package pipocaagil.pipocaagil2026_ciclo3.scheduling.infrastructure.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import pipocaagil.pipocaagil2026_ciclo3.scheduling.domain.CareModality;
import pipocaagil.pipocaagil2026_ciclo3.scheduling.domain.StatusAgenda;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Entity
@Table(name = "agenda", uniqueConstraints = @UniqueConstraint(name = "uk_doctor_date_time", columnNames = {"doctorId", "slotDate","slotTime"}))
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AgendaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;


    @NotNull
    @Column(nullable = false)
    LocalDate slotDate;

    @NotNull
    @Column(nullable = false)
    LocalTime slotTime;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    CareModality modality;

    @NotNull
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(nullable = false)
    UUID doctorId;


    @JdbcTypeCode(SqlTypes.CHAR)
    UUID patientId;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    StatusAgenda status;

    Instant heldUntil;
}
