package pipocaagil.pipocaagil2026_ciclo3.scheduling.infrastructure.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.hibernate.validator.constraints.br.CPF;
import pipocaagil.pipocaagil2026_ciclo3.scheduling.domain.PersonWithDesability;

import java.time.LocalDate;
import java.util.UUID;

@Entity (name = "patient")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatientEntity {
    @Id
    @JdbcTypeCode(SqlTypes.CHAR) // mapear o objeto UUID do Java como uma coluna de caractere de tamanho fixo (CHAR)
    UUID id;

    @NotBlank
    @Size(min = 2, max = 150)
    @Column(nullable = false, length = 150)
    String name;

    @NotBlank
    @CPF
    @Column(nullable = false, unique = true, length = 11)
    String CPF;

    @NotNull
    @Past
    @Column(nullable = false, updatable = false)
    LocalDate birthDate;


    @NotBlank
    @Pattern(
            regexp = "^[A-Za-z0-9_%+-]+(?:\\.[A-Za-z0-9_%+-]+)*@(?:[A-Za-z0-9](?:[A-Za-z0-9-]*[A-Za-z0-9])?\\.)+[A-Za-z]{2,}$"
    )
    @Column(nullable = false, unique = true)
    String email;



    @Pattern(
            regexp = "^(?:\\+?55)?[1-9][1-9]9\\d{8}$"
    )
    @Column(nullable = false)
    String cellPhone;

    @Pattern(
            regexp = "^(?:\\+?55)?[1-9][1-9][2-5]\\d{7}$"
    )
    String landPhone;

    @Enumerated(EnumType.STRING)
    PersonWithDesability pwd;
}
