package co.edu.uniandes.dse.parcialprueba.services;

import co.edu.uniandes.dse.parcialprueba.entities.EspecialidadEntity;
import co.edu.uniandes.dse.parcialprueba.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.parcialprueba.repositories.EspecialidadRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EspecialidadService {

    @Autowired
    private EspecialidadRepository especialidadRepository;

    @Transactional
    public EspecialidadEntity createEspecialidad(EspecialidadEntity especialidadEntity) throws IllegalOperationException {
        validateEspecialidad(especialidadEntity);
        return especialidadRepository.save(especialidadEntity);
    }

    private void validateEspecialidad(EspecialidadEntity especialidadEntity) throws IllegalOperationException {
        if (especialidadEntity.getDescripcion() == null || especialidadEntity.getDescripcion().length() < 10) {
            throw new IllegalOperationException("La descripción debe tener al menos 10 caracteres.");
        }
    }
}