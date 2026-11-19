package pipocaagil.pipocaagil2026_ciclo3.auth.infrastructure.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import pipocaagil.pipocaagil2026_ciclo3.auth.domain.UserType;

import java.time.Instant;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@EntityListeners(AuditingEntityListener.class)
@Entity (name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity implements UserDetails {

    private static final String ROLE_PREFIX = "ROLE_";

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

    @Column(nullable = false, length = 255)
    String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    UserType userType;

    UUID patientId;

    @CreatedBy
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(updatable = false)
    private UUID createdBy;

    @CreatedDate
    @Column(updatable = false)
    private Instant createdAt;

    @LastModifiedBy
    @JdbcTypeCode(SqlTypes.CHAR)
    private UUID updatedBy;

    @LastModifiedDate
    private Instant updatedAt;

    // auto-cadastro: o autor é o próprio usuário
    @PrePersist
    void defineCreatedByWhenSelfRegistered() {
        if (createdBy == null) {
            createdBy = id;
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(ROLE_PREFIX + userType.name()));
    }

    @Override
    public String getUsername() {
        return this.email;
    }
}
