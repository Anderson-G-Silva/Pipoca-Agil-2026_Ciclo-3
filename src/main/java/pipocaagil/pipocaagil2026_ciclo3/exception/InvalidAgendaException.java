package pipocaagil.pipocaagil2026_ciclo3.exception;

public class InvalidAgendaException extends BusinessConflictException{
    public InvalidAgendaException(String message) {
        super(message);
    }
}
