package pipocaagil.pipocaagil2026_ciclo3.scheduling.domain;

import java.util.List;
import java.util.Optional;

public interface DoctorRepository {
    Doctor save(Doctor doctor);
    Doctor findByName(String name);
    Optional<Doctor> findByCRMAndUF (String CRM, UfCrm UF);
    List<Doctor> findBySpecialty(MedicalSpecialty specialty);


}
