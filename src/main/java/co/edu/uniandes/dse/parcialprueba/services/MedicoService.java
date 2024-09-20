package co.edu.uniandes.dse.parcialprueba.services;

import co.edu.uniandes.dse.parcialprueba.entities.EspecialidadEntity;

import co.edu.uniandes.dse.parcialprueba.entities.MedicoEntity;
import co.edu.uniandes.dse.parcialprueba.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.parcialprueba.repositories.MedicoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MedicoService {

    @Autowired
    private MedicoRepository medicoRepository;

    @Transactional
    public MedicoEntity createMedico(MedicoEntity medicoEntity) throws IllegalOperationException {
        validateMedico(medicoEntity);
        return medicoRepository.save(medicoEntity);
    }

    private void validateMedico(MedicoEntity medicoEntity) throws IllegalOperationException {
        if (medicoEntity.getRegistroMedico() == null || !medicoEntity.getRegistroMedico().startsWith("RM")) {
            throw new IllegalOperationException("El registro médico debe iniciar con 'RM'.");
        }
    }
}