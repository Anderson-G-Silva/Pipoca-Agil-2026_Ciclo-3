package pipocaagil.pipocaagil2026_ciclo3.scheduling.infrastructure.http.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import pipocaagil.pipocaagil2026_ciclo3.scheduling.application.request.DaysOfWeek;
import pipocaagil.pipocaagil2026_ciclo3.scheduling.application.request.ServiceWindow;
import pipocaagil.pipocaagil2026_ciclo3.scheduling.domain.CareModality;
import pipocaagil.pipocaagil2026_ciclo3.scheduling.domain.UfCrm;

import java.time.Duration;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public record OpenAgendaRequest (
        @NotBlank(message = "CRM é obrigatório")
        @Pattern(regexp = "^[1-9]\\d{3,6}$",
                message = "Número do CRM inválido (4 a 7 dígitos)")
        @Schema(description = "Número do CRM")
        String CRM,

        @NotNull(message = "UF é obrigatório")
        @Schema(description = "UF do CRM")
        UfCrm UF,

        @NotNull(message = "Modalidade de consulta é obrigatória")
        @Schema(description = "Modalidade de atendimento")
        CareModality modality,

        @NotNull(message = "Data inicial é obrigatória")
        @Schema(description = "Data inicial da agenda")
        LocalDate startDate,

        @NotNull(message = "Data final é obrigatória")
        @Schema(description = "Data final da agenda")
        LocalDate endDate,

        @NotNull(message = "A lista de dias não pode ser nula.")
        @NotEmpty(message = "Você deve selecionar ao menos um dia da semana.")
        @Size(min = 1, max = 7)
        @Schema(description = "Dias da semana")
        Set<DaysOfWeek> daysOfWeek,

        @NotNull(message = "A lista de janelas de horários não pode ser nula.")
        @NotEmpty(message = "Você deve indicar ao menos uma janela de horário.")
        @Schema(description = "Janela de atendimento")
        List<ServiceWindow> windows,

        @NotNull(message = "A duração deve ser informada.")
        @Positive(message = "A duração deve ser um valor maior que zero.")
        @Schema(description = "Duração da consulta em minutos", example = "30")
        Integer consultationDuration

        ) {
}
