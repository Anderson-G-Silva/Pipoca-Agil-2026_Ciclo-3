package pipocaagil.pipocaagil2026_ciclo3.scheduling.infrastructure.http.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import pipocaagil.pipocaagil2026_ciclo3.scheduling.domain.MedicalSpecialty;
import pipocaagil.pipocaagil2026_ciclo3.scheduling.domain.UfCrm;

public record DoctorRequest(
        @NotBlank(message = "O nome é obrigatório e não pode conter apenas espaços.")
        @Size(min = 2, max = 150, message = "O nome deve ter entre 2 e 150 caracteres")
        @Column(nullable = false, length = 150)
        @Schema(description = "Nome completo")
        String name,

        @NotBlank(message = "CRM é obrigatório")
        @Pattern(regexp = "^[1-9]\\d{3,6}$",
                message = "Número do CRM inválido (4 a 7 dígitos)")
        @Schema(description = "Número do CRM")
        String CRM,

        @NotNull(message = "UF é obrigatório")
        @Enumerated(EnumType.STRING)
        @Column(nullable = false)
        @Schema(description = "UF do CRM")
        UfCrm UF,

        @NotNull(message = "Especialidade é obrigatória")
        @Enumerated(EnumType.STRING)
        @Schema(description = "Especialidade médica")
        MedicalSpecialty specialty

) {
}
