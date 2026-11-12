package pipocaagil.pipocaagil2026_ciclo3.exception;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessConflictException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    @ApiResponse(
            responseCode = "409",
            description = "Conflito com o estado atual do recurso",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(type = "object"),
                    examples = @ExampleObject(
                            name = "Conflito",
                            value = """
                                    { "message": "Descrição do conflito" }
                                    """
                    )
            )
    )
    public ResponseEntity<Map<String, String>> handleConflict(BusinessConflictException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(Map.of("message", ex.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ApiResponse(
            responseCode = "400",
            description = "Erro de validação: cada campo rejeitado vem com o motivo",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(type = "object"),
                    examples = @ExampleObject(
                            name = "Campos inválidos",
                            value = """
                                    { "campo": "motivo da rejeição" }
                                    """
                    )
            )
    )
    public ResponseEntity<Map<String, String>> handleValidationException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new LinkedHashMap<>();
        ex.getBindingResult().getFieldErrors()
                .forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
        return ResponseEntity.badRequest().body(errors);
    }


    /*@ExceptionHandler(DoctorAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    @ApiResponse(
            responseCode = "409",
            description = "Conflito: registro duplicado (chave única violada)",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(type = "object"),
                    examples = @ExampleObject(
                            name = "Médico duplicado",
                            value = """
                {
                  "message": "Já existe um médico cadastrado com CRM 123456/SP."
                }
                """
                    )
            )
    )
    public ResponseEntity<Map<String, String>> handleDoctorAlreadyExists(DoctorAlreadyExistsException ex) {
        Map<String, String> error = new LinkedHashMap<>();
        error.put("message", ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    @ExceptionHandler(HoldExpiredException.class)
    @ResponseStatus(HttpStatus.CONFLICT)   // 409
    public Map<String, String> handleHoldExpired(HoldExpiredException ex) {
        return Map.of("erro", ex.getMessage());
    }

    @ExceptionHandler(SlotUnavailableException.class)
    @ResponseStatus(HttpStatus.CONFLICT)   // 409
    public Map<String, String> handleSlotUnavailable(SlotUnavailableException ex) {
        return Map.of("erro", ex.getMessage());
    }*/
}
