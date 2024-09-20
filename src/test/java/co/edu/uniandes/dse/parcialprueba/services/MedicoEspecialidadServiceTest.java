package co.edu.uniandes.dse.parcialprueba.services;



import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

import co.edu.uniandes.dse.parcialprueba.entities.EspecialidadEntity;
import co.edu.uniandes.dse.parcialprueba.entities.MedicoEntity;
import co.edu.uniandes.dse.parcialprueba.exceptions.EntityNotFoundException;
import jakarta.transaction.Transactional;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

@DataJpaTest
@Transactional
@Import(MedicoEspecialidadService.class)
class MedicoEspecialidadServiceTest {

    @Autowired
    private MedicoEspecialidadService medicoEspecialidadService;

    @Autowired
    private TestEntityManager entityManager;

    private PodamFactory factory = new PodamFactoryImpl();

    private MedicoEntity medicoEntity;
    private EspecialidadEntity especialidadEntity;

    @BeforeEach
    void setUp() {
        clearData();
        insertData();
    }

    private void clearData() {
        entityManager.getEntityManager().createQuery("DELETE FROM MedicoEntity").executeUpdate();
        entityManager.getEntityManager().createQuery("DELETE FROM EspecialidadEntity").executeUpdate();
    }

    private void insertData() {
        medicoEntity = factory.manufacturePojo(MedicoEntity.class);
        especialidadEntity = factory.manufacturePojo(EspecialidadEntity.class);
        
        // Aquí utilizamos merge en lugar de persist para evitar el error.
        medicoEntity = entityManager.merge(medicoEntity);
        especialidadEntity = entityManager.merge(especialidadEntity);
    }

    @Test
    void testAddEspecialidadSuccess() throws EntityNotFoundException {
        MedicoEntity result = medicoEspecialidadService.addEspecialidad(medicoEntity.getId(), especialidadEntity.getId());
        assertNotNull(result);
        assertTrue(result.getEspecialidades().contains(especialidadEntity));
    }

    @Test
    void testAddEspecialidadMedicoNotFound() {
        assertThrows(EntityNotFoundException.class, () -> {
            medicoEspecialidadService.addEspecialidad(0L, especialidadEntity.getId());
        });
    }

    @Test
    void testAddEspecialidadEspecialidadNotFound() {
        assertThrows(EntityNotFoundException.class, () -> {
            medicoEspecialidadService.addEspecialidad(medicoEntity.getId(), 0L);
        });
    }
}

