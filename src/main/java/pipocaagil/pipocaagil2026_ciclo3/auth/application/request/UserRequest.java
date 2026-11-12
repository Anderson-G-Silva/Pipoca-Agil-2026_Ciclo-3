package pipocaagil.pipocaagil2026_ciclo3.auth.application.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.br.CPF;
import pipocaagil.pipocaagil2026_ciclo3.auth.domain.UserType;

import java.time.LocalDate;
import java.util.UUID;

public record UserRequest (
        String userName,
        String email,
        LocalDate birthDate,
        String accompaniedPatientCPF,
        UserType userType,
        String password,
        String confirmPassword,
        UUID patientId

){
}
