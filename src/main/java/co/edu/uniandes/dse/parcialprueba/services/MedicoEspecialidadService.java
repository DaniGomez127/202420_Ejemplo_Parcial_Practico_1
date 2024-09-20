package co.edu.uniandes.dse.parcialprueba.services;


import co.edu.uniandes.dse.parcialprueba.entities.EspecialidadEntity;
import co.edu.uniandes.dse.parcialprueba.entities.MedicoEntity;
import co.edu.uniandes.dse.parcialprueba.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.parcialprueba.repositories.EspecialidadRepository;
import co.edu.uniandes.dse.parcialprueba.repositories.MedicoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class MedicoEspecialidadService {

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private EspecialidadRepository especialidadRepository;

    @Transactional
    public MedicoEntity addEspecialidad(Long medicoId, Long especialidadId) throws EntityNotFoundException {
        Optional<MedicoEntity> medicoOpt = medicoRepository.findById(medicoId);
        if (medicoOpt.isEmpty()) {
            throw new EntityNotFoundException("El médico con ID " + medicoId + " no fue encontrado.");
        }

        Optional<EspecialidadEntity> especialidadOpt = especialidadRepository.findById(especialidadId);
        if (especialidadOpt.isEmpty()) {
            throw new EntityNotFoundException("La especialidad con ID " + especialidadId + " no fue encontrada.");
        }

        MedicoEntity medico = medicoOpt.get();
        EspecialidadEntity especialidad = especialidadOpt.get();

        medico.getEspecialidades().add(especialidad);
        return medicoRepository.save(medico);
    }
}