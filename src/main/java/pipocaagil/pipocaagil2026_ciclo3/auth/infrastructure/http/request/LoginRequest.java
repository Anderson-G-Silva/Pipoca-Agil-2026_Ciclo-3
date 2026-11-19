package pipocaagil.pipocaagil2026_ciclo3.auth.infrastructure.http.request;

import io.swagger.v3.oas.annotations.media.Schema;

public record LoginRequest(
        @Schema(example = "string", requiredMode = Schema.RequiredMode.REQUIRED)
        String email,
        @Schema(example = "string", format = "password", requiredMode = Schema.RequiredMode.REQUIRED)
        String password
) {}
