package pipocaagil.pipocaagil2026_ciclo3.scheduling.domain;

import java.util.List;
import java.util.Optional;

public interface PatientRepository {
    Patient save(Patient patient);

    Patient findByCPF(String CPF);

    Patient findByName(String name);

    List<Patient> findAll();

    Patient deleteByCPF(String CPF);
}
