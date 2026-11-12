package pipocaagil.pipocaagil2026_ciclo3.scheduling.infrastructure.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import pipocaagil.pipocaagil2026_ciclo3.scheduling.domain.MedicalSpecialty;
import pipocaagil.pipocaagil2026_ciclo3.scheduling.domain.UfCrm;

import java.util.UUID;

@Entity
@Table (name = "doctor", uniqueConstraints = @UniqueConstraint(name = "uk_doctor_crm_uf", columnNames = {"CRM","UF"}))
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DoctorEntity {
    @Id
    @JdbcTypeCode(SqlTypes.CHAR) // mapear o objeto UUID do Java como uma coluna de caractere de tamanho fixo (CHAR)
    UUID id;

    @NotBlank
    @Size(min = 2, max = 150)
    @Column(nullable = false, length = 150)
    String name;

    @NotBlank
    @Pattern(regexp = "^[1-9]\\d{3,6}$")
    @Column(nullable = false, length = 7)
    String CRM;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    UfCrm UF;

    @NotNull
    @Enumerated(EnumType.STRING)
    MedicalSpecialty specialty;

}
