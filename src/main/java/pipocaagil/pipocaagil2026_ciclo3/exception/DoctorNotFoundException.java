package pipocaagil.pipocaagil2026_ciclo3.exception;

import pipocaagil.pipocaagil2026_ciclo3.scheduling.domain.UfCrm;

public class DoctorNotFoundException extends RuntimeException {
    public DoctorNotFoundException(String CRM, UfCrm UF) {
        super("Médico com CRM " + CRM + "/" + UF + " não encontrado.");
    }
}
