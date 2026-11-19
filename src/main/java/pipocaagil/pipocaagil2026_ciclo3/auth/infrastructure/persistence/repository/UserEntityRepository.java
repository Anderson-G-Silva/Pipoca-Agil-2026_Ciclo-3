package pipocaagil.pipocaagil2026_ciclo3.auth.infrastructure.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pipocaagil.pipocaagil2026_ciclo3.auth.infrastructure.persistence.entity.UserEntity;

import java.util.Optional;
import java.util.UUID;

public interface UserEntityRepository extends JpaRepository<UserEntity, UUID> {
    UserEntity findByUserName(String userName);
    UserEntity findByEmail(String email);
    void deleteById(UUID id);
    Optional<UserEntity> findUserByEmail(String email);
}

