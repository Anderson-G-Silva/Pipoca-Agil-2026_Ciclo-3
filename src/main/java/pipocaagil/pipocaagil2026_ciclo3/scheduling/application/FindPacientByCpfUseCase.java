package pipocaagil.pipocaagil2026_ciclo3.scheduling.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pipocaagil.pipocaagil2026_ciclo3.scheduling.domain.Patient;
import pipocaagil.pipocaagil2026_ciclo3.scheduling.domain.PatientRepository;

import java.util.Optional;

@Service
public class FindPacientByCpfUseCase {
    private final PatientRepository patientRepository;

    public FindPacientByCpfUseCase(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    @Transactional(readOnly = true)
    public Optional<Patient> execute(String cpf) {
        return Optional.ofNullable(patientRepository.findByCPF(cpf));
    }
}
