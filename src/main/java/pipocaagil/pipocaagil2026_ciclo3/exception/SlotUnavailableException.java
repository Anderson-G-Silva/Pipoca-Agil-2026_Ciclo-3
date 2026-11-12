package pipocaagil.pipocaagil2026_ciclo3.exception;

public class SlotUnavailableException extends BusinessConflictException {
    public SlotUnavailableException() {
        super("Este horário não está mais disponível. Selecione outro horário.");

    }
}
