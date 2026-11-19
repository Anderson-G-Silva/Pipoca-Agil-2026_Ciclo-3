package pipocaagil.pipocaagil2026_ciclo3.auth.infrastructure.security;




import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import pipocaagil.pipocaagil2026_ciclo3.auth.infrastructure.persistence.entity.UserEntity;

import java.util.UUID;

@Component
public class AuthenticatedUserProvider {

    public UUID getCurrentUserId() {
        return currentUser().getId();
    }

    public UUID getCurrentPatientId() {
        UUID patientId = currentUser().getPatientId();
        if (patientId == null) {
            throw new AccessDeniedException("O usuário não está vinculado a um paciente.");
        }
        return patientId;
    }

    private UserEntity currentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null
                || !authentication.isAuthenticated()
                || !(authentication.getPrincipal() instanceof UserEntity user)) {
            throw new IllegalStateException("Nenhum usuário autenticado no contexto.");
        }
        return user;
    }
}