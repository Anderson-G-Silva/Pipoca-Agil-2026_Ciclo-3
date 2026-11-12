package pipocaagil.pipocaagil2026_ciclo3.auth.infrastructure.http.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.br.CPF;
import pipocaagil.pipocaagil2026_ciclo3.auth.domain.UserType;

import java.time.LocalDate;

import static pipocaagil.pipocaagil2026_ciclo3.auth.domain.UserType.COMPANION;

public record CompanionRequest (
        @NotBlank(message = "nome é obrigatório")
        @Size(min = 2, max = 150, message = "O nome deve ter entre 2 e 150 caracteres")
        @Schema(description = "Nome completo")
        String userName,

        @NotBlank(message = "O e-mail é obrigatório.")
        @Pattern(
                regexp = "^[A-Za-z0-9_%+-]+(?:\\.[A-Za-z0-9_%+-]+)*@(?:[A-Za-z0-9](?:[A-Za-z0-9-]*[A-Za-z0-9])?\\.)+[A-Za-z]{2,}$",
                message = "Por favor, insira um e-mail em um formato válido."
        )
        @Schema(description = "E-mail", example = "nome@exemplo.com")
        String email,

        @NotNull(message = "Data de nascimento é obrigatória")
        LocalDate birthDate,

        @NotBlank(message = "CPF é obrigatório")
        @CPF(message = "CPF inválido")
        String accompaniedPatientCPF,

        @NotBlank(message = "A senha é obrigatória")
        @Pattern(
                regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&._-])[A-Za-z\\d@$!%*?&._-]{8,72}$",
                message = "A senha deve ter no mínimo 8 dígitos e máximo 72 dígítos e conter pelo menos uma letra maiúscula, uma letra minúscula, um número e um caractere especial."
        )

        String password,

        @NotBlank(message = "A confirmação de senha é obrigatória")
        @Pattern(
                regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&._-])[A-Za-z\\d@$!%*?&._-]{8,72}$",
                message = "A senha deve ter no mínimo 8 dígitos e máximo 72 dígítos e conter pelo menos uma letra maiúscula, uma letra minúscula, um número e um caractere especial."
        )
        String confirmPassword

) {
        // Constante que define o tipo de usuário, neste caso é fixo.
        public static final UserType USER_TYPE = COMPANION;
}
