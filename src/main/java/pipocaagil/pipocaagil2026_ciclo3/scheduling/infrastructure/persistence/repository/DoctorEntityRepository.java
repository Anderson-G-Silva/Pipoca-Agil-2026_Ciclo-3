package pipocaagil.pipocaagil2026_ciclo3.scheduling.infrastructure.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pipocaagil.pipocaagil2026_ciclo3.scheduling.domain.MedicalSpecialty;
import pipocaagil.pipocaagil2026_ciclo3.scheduling.domain.UfCrm;
import pipocaagil.pipocaagil2026_ciclo3.scheduling.infrastructure.persistence.entity.DoctorEntity;

import java.util.List;
import java.util.UUID;

public interface DoctorEntityRepository extends JpaRepository<DoctorEntity, UUID> {
    DoctorEntity findByName(String name);
    DoctorEntity findByCRMAndUF(String CRM, UfCrm UF);
    List<DoctorEntity> findBySpecialty(MedicalSpecialty specialty);

}
