package pipocaagil.pipocaagil2026_ciclo3.scheduling.application;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import pipocaagil.pipocaagil2026_ciclo3.auth.application.SaveUserUserCase;
import pipocaagil.pipocaagil2026_ciclo3.auth.application.request.UserRequest;
import pipocaagil.pipocaagil2026_ciclo3.auth.domain.User;
import pipocaagil.pipocaagil2026_ciclo3.scheduling.domain.Patient;
import pipocaagil.pipocaagil2026_ciclo3.scheduling.domain.PatientRepository;
import pipocaagil.pipocaagil2026_ciclo3.scheduling.infrastructure.http.request.PatientRequest;

import static pipocaagil.pipocaagil2026_ciclo3.auth.domain.UserType.PATIENT;

@Service
public class SavePatienteUseCase {
    private final PatientRepository patientRepository;
    private final SaveUserUserCase  saveUserUserCase;

    public SavePatienteUseCase(PatientRepository patientRepository, SaveUserUserCase saveUserUserCase) {
        this.patientRepository = patientRepository;
        this.saveUserUserCase = saveUserUserCase;
    }

    @Transactional
    public void execute(PatientRequest request) {
        Patient patient = new Patient(
                request.name(),
                request.CPF(),
                request.birthDate(),
                request.email(),
                request.cellPhone(),
                request.landPhone(),
                request.pwd()
        );


        patientRepository.save(patient);
        System.out.println(patient);
        UserRequest  userRequest = new UserRequest(
                patient.getName(),
                patient.getEmail(),
                request.birthDate(),
                null,
                PATIENT,
                request.password(),
                request.confirmPassword(),
                patient.getId().id()
        );
        saveUserUserCase.execute(userRequest);

    }

}
