package co.edu.uniandes.dse.parcialprueba.services;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

import co.edu.uniandes.dse.parcialprueba.entities.MedicoEntity;
import co.edu.uniandes.dse.parcialprueba.exceptions.IllegalOperationException;
import jakarta.transaction.Transactional;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

@DataJpaTest
@Transactional
@Import(MedicoService.class)
class MedicoServiceTest {

    @Autowired
    private MedicoService medicoService;

    @Autowired
    private TestEntityManager entityManager;

    private PodamFactory factory = new PodamFactoryImpl();

    @BeforeEach
    void setUp() {
        clearData();
    }

    private void clearData() {
        entityManager.getEntityManager().createQuery("DELETE FROM MedicoEntity").executeUpdate();
    }

    @Test
    void testCreateMedico() throws IllegalOperationException {
        MedicoEntity newEntity = factory.manufacturePojo(MedicoEntity.class);
        newEntity.setRegistroMedico("RM1234");
        MedicoEntity result = medicoService.createMedico(newEntity);
        assertNotNull(result);
        MedicoEntity entity = entityManager.find(MedicoEntity.class, result.getId());
        assertEquals(newEntity.getNombre(), entity.getNombre());
        assertEquals(newEntity.getApellido(), entity.getApellido());
        assertEquals("RM1234", entity.getRegistroMedico());
    }

    @Test
    void testCreateMedicoWithInvalidRegistro() {
        assertThrows(IllegalOperationException.class, () -> {
            MedicoEntity newEntity = factory.manufacturePojo(MedicoEntity.class);
            newEntity.setRegistroMedico("1234");
            medicoService.createMedico(newEntity);
        });
    }
}
