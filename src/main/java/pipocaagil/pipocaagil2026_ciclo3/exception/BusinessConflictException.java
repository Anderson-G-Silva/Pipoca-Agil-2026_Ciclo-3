package pipocaagil.pipocaagil2026_ciclo3.exception;

public abstract class BusinessConflictException extends RuntimeException {
    protected BusinessConflictException(String message) { super(message); }
}
