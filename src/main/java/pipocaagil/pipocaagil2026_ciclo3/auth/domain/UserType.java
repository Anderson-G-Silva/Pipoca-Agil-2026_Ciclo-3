package pipocaagil.pipocaagil2026_ciclo3.auth.domain;


import lombok.Getter;

@Getter
public enum UserType {
    PATIENT(0),
    COMPANION(18),
    DOCTOR(18),
    ATTENDANT(16);

    private final int minimunAge;
    UserType(int minimunAge) {
        this.minimunAge = minimunAge;
    }
}
