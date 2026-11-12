package pipocaagil.pipocaagil2026_ciclo3.scheduling.application.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalTime;

public record ServiceWindow(
        @Schema(type = "string", example = "08:00", pattern = "^([01]\\d|2[0-3]):[0-5]\\d$",
                description = "Início da janela de atendimento (HH:mm)")
        @JsonFormat(pattern = "HH:mm")
        LocalTime start,

        @Schema(type = "string", example = "12:00", pattern = "^([01]\\d|2[0-3]):[0-5]\\d$",
                description = "Fim da janela de atendimento (HH:mm)")
        @JsonFormat(pattern = "HH:mm")
        LocalTime end) {
}
