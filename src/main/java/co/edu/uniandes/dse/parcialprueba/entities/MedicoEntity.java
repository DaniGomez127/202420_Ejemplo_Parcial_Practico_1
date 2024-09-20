package co.edu.uniandes.dse.parcialprueba.entities;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Column;
import lombok.Data;
import uk.co.jemos.podam.common.PodamExclude;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase que representa un Médico en la persistencia.
 */
@Data
@Entity
public class MedicoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String apellido;

    @Column(unique = true, nullable = false)
    private String registroMedico;

    @PodamExclude
    @ManyToMany(mappedBy = "medicos", cascade = CascadeType.ALL)
    private List<EspecialidadEntity> especialidades = new ArrayList<>();
}

