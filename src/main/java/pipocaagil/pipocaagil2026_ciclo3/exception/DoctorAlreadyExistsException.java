package pipocaagil.pipocaagil2026_ciclo3.exception;

public class DoctorAlreadyExistsException extends BusinessConflictException {
    public DoctorAlreadyExistsException(String crm, String uf) {
        super("Já existe um médico cadastrado com CRM %s/%s.".formatted(crm, uf));
    }
}
