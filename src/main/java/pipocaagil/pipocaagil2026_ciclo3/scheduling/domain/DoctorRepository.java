package pipocaagil.pipocaagil2026_ciclo3.scheduling.domain;

import java.util.List;

public interface DoctorRepository {
    Doctor save(Doctor doctor);
    Doctor findByName(String name);
    Doctor findByCRMAndUF (String CRM, UfCrm UF);
    List<Doctor> findBySpecialty(MedicalSpecialty specialty);


}
