package pipocaagil.pipocaagil2026_ciclo3.auth.application;

import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pipocaagil.pipocaagil2026_ciclo3.auth.application.request.UserRequest;
import pipocaagil.pipocaagil2026_ciclo3.auth.domain.User;
import pipocaagil.pipocaagil2026_ciclo3.auth.domain.UserRepository;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Service
public class SaveUserUserCase {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public SaveUserUserCase(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void execute(UserRequest request) {
        var age = ChronoUnit.YEARS.between(request.birthDate(), LocalDate.now());
        if (age <= request.userType().getMinimunAge()) {
            throw new IllegalArgumentException("Idade mínima permitida é de " + request.userType().getMinimunAge() + " anos. Idade fornecida: " + age);
        }
                User user = new User(
                request.userName(),
                request.email(),
                request.birthDate(),
                passwordEncoder.encode(request.password()),
                request.userType(),
                request.patientId());
        userRepository.save(user);
    }
}
