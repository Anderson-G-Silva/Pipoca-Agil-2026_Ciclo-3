package pipocaagil.pipocaagil2026_ciclo3.auth.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pipocaagil.pipocaagil2026_ciclo3.auth.domain.User;
import pipocaagil.pipocaagil2026_ciclo3.auth.domain.UserRepository;

@Service
public class FindByEmailUseCase {
    private final UserRepository userRepository;

    public FindByEmailUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    public User execute (String email) {
        return userRepository.findByEmail(email);
    }

}
