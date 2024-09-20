package co.edu.uniandes.dse.parcialprueba.services;


import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

import co.edu.uniandes.dse.parcialprueba.entities.EspecialidadEntity;
import co.edu.uniandes.dse.parcialprueba.exceptions.IllegalOperationException;
import jakarta.transaction.Transactional;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

@DataJpaTest
@Transactional
@Import(EspecialidadService.class)
class EspecialidadServiceTest {

    @Autowired
    private EspecialidadService especialidadService;

    @Autowired
    private TestEntityManager entityManager;

    private PodamFactory factory = new PodamFactoryImpl();

    @BeforeEach
    void setUp() {
        clearData();
    }

    private void clearData() {
        entityManager.getEntityManager().createQuery("DELETE FROM EspecialidadEntity").executeUpdate();
    }

    @Test
    void testCreateEspecialidad() throws IllegalOperationException {
        EspecialidadEntity newEntity = factory.manufacturePojo(EspecialidadEntity.class);
        newEntity.setDescripcion("Descripción válida con más de diez caracteres");
        EspecialidadEntity result = especialidadService.createEspecialidad(newEntity);
        assertNotNull(result);
        EspecialidadEntity entity = entityManager.find(EspecialidadEntity.class, result.getId());
        assertEquals(newEntity.getNombre(), entity.getNombre());
        assertEquals(newEntity.getDescripcion(), entity.getDescripcion());
    }

    @Test
    void testCreateEspecialidadWithInvalidDescripcion() {
        assertThrows(IllegalOperationException.class, () -> {
            EspecialidadEntity newEntity = factory.manufacturePojo(EspecialidadEntity.class);
            newEntity.setDescripcion("Corta");
            especialidadService.createEspecialidad(newEntity);
        });
    }
}
