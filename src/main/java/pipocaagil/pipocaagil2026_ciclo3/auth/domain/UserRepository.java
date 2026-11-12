package pipocaagil.pipocaagil2026_ciclo3.auth.domain;

import java.util.UUID;

public interface UserRepository {
    User save(User user);
    User findByUserName(String userName);
    User findByEmail(String email);
    void deleteById(UUID id);
}
