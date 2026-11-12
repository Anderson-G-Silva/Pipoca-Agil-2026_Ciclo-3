package pipocaagil.pipocaagil2026_ciclo3.auth.infrastructure.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.springframework.stereotype.Repository;
import pipocaagil.pipocaagil2026_ciclo3.auth.domain.UserId;
import pipocaagil.pipocaagil2026_ciclo3.auth.domain.UserType;

import java.util.UUID;

@Entity (name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {
    @Id
    @JdbcTypeCode(SqlTypes.CHAR) // mapear o objeto UUID do Java como uma coluna de caractere de tamanho fixo (CHAR)
    UUID id;

    @Column(nullable = false, length = 150)
    String userName;

    @NotBlank(message = "O e-mail é obrigatório.")
    @Pattern(
            regexp = "^[A-Za-z0-9_%+-]+(?:\\.[A-Za-z0-9_%+-]+)*@(?:[A-Za-z0-9](?:[A-Za-z0-9-]*[A-Za-z0-9])?\\.)+[A-Za-z]{2,}$",
            message = "Por favor, insira um e-mail em um formato válido."
    )
    @Column(nullable = false, unique = true)
    String email;

    @Column(nullable = false, length = 50)
    String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    UserType userType;

    UUID patientId;
}
