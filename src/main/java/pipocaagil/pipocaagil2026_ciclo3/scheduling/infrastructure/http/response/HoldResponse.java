package pipocaagil.pipocaagil2026_ciclo3.scheduling.infrastructure.http.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.Instant;

public record HoldResponse(
        @Schema(description = "Id do horário reservado", example = "42")
        Long slotId,

        @Schema(description = "Instante em que a reserva expira (UTC)", example = "2026-11-12T19:32:30Z")
        Instant expiresAt,

        @Schema(description = "Duração total da reserva, em segundos", example = "60")
        long holdSeconds
) {
}
