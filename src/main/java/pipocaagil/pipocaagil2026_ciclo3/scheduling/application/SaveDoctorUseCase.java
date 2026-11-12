package pipocaagil.pipocaagil2026_ciclo3.scheduling.application;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import pipocaagil.pipocaagil2026_ciclo3.scheduling.domain.Doctor;
import pipocaagil.pipocaagil2026_ciclo3.scheduling.domain.DoctorRepository;
import pipocaagil.pipocaagil2026_ciclo3.scheduling.infrastructure.http.request.DoctorRequest;

@Service
public class SaveDoctorUseCase {

    private final DoctorRepository doctorRepository;


    public SaveDoctorUseCase(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    @Transactional
    public Doctor execute(DoctorRequest request) {
        Doctor doctor = new Doctor(
                request.name(),
                request.CRM(),
                request.UF(),
                request.specialty()
        );

        return doctorRepository.save(doctor);
    }
}
