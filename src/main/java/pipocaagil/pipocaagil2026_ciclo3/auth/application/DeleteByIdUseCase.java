package pipocaagil.pipocaagil2026_ciclo3.auth.application;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import pipocaagil.pipocaagil2026_ciclo3.auth.domain.UserRepository;

import java.util.UUID;

@Service
public class DeleteByIdUseCase {
    private final UserRepository userRepository;

    public DeleteByIdUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public void execute(UUID id) {
        userRepository.deleteById(id);
    }
}
