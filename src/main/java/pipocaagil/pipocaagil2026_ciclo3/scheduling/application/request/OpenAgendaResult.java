package pipocaagil.pipocaagil2026_ciclo3.scheduling.application.request;

public record OpenAgendaResult(
        int totalGenerated,   // slots calculados a partir do pedido
        int created,          // slots realmente inseridos
        int skipped           // slots que já existiam (ON CONFLICT DO NOTHING)
) {
}
