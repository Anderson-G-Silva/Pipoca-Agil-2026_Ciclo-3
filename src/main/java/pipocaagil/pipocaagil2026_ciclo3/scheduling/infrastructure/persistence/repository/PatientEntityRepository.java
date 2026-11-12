package pipocaagil.pipocaagil2026_ciclo3.scheduling.infrastructure.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pipocaagil.pipocaagil2026_ciclo3.scheduling.domain.Patient;
import pipocaagil.pipocaagil2026_ciclo3.scheduling.infrastructure.persistence.entity.PatientEntity;

import java.util.List;
import java.util.UUID;

public interface PatientEntityRepository extends JpaRepository<PatientEntity, UUID> {
    PatientEntity findByCPF(String CPF);

    PatientEntity findByName(String name);

    List<PatientEntity> findAll();

    PatientEntity deleteByCPF(String CPF);
}
