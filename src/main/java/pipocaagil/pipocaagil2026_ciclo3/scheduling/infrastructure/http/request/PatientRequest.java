package pipocaagil.pipocaagil2026_ciclo3.scheduling.infrastructure.http.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.br.CPF;
import pipocaagil.pipocaagil2026_ciclo3.scheduling.domain.PersonWithDesability;

import java.time.LocalDate;

public record PatientRequest(
        @NotBlank(message = "O nome é obrigatório e não pode conter apenas espaços.")
        @Size(min = 2, max = 150, message = "O nome deve ter entre 2 e 150 caracteres")
        @Schema(description = "Nome completo")
        String name,

        @NotBlank(message = "CPF é obrigatório")
        @CPF(message = "CPF inválido")
        @Schema(description = "CPF")
        String CPF,

        @NotNull(message = "Data de nascimento é obrigatória")
        @Past(message = "A data de nascimento deve ser uma data no passado.")
        @Schema(description = "Data de nascimento")
        LocalDate birthDate,

        @NotBlank(message = "O e-mail é obrigatório.")
        @Pattern(
                regexp = "^[A-Za-z0-9_%+-]+(?:\\.[A-Za-z0-9_%+-]+)*@(?:[A-Za-z0-9](?:[A-Za-z0-9-]*[A-Za-z0-9])?\\.)+[A-Za-z]{2,}$",
                message = "Por favor, insira um e-mail em um formato válido."
        )
        @Schema(description = "E-mail", example = "nome@exemplo.com")
        String email,

        @NotBlank(message = "Número de celular é obrigatório")
        @Pattern(
                regexp = "^(?:\\+?55)?[1-9][1-9]9\\d{8}$",
                message = "Celular inválido. Use DDD + 9 dígitos, ex: 11987654321"
        )
        @Schema(description = "Telefone Celular")
        String cellPhone,

        @Pattern(
                regexp = "^(?:\\+?55)?[1-9][1-9][2-5]\\d{7}$",
                message = "Telefone fixo inválido. Use DDD + 8 dígitos, ex: 1133334444"
        )
        @Schema(description = "Telefone fixo")
        String landPhone,

        @Schema(description = "Tipo de deficiência", example = "VISUAL")
        PersonWithDesability pwd,

        @NotBlank(message = "A senha é obrigatória")
        @Pattern(
                regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&._-])[A-Za-z\\d@$!%*?&._-]{8,72}$",
                message = "A senha deve ter no mínimo 8 dígitos e máximo 72 dígítos e conter pelo menos uma letra maiúscula, uma letra minúscula, um número e um caractere especial."
        )
        @Schema(description = "Senha")
        String password,

        @NotBlank(message = "A confirmação de senha é obrigatória")
        @Pattern(
                regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&._-])[A-Za-z\\d@$!%*?&._-]{8,72}$",
                message = "A senha deve ter no mínimo 8 dígitos e máximo 72 dígítos e conter pelo menos uma letra maiúscula, uma letra minúscula, um número e um caractere especial."
        )
        @Schema(description = "Confirmar senha")
        String confirmPassword


) {
}
