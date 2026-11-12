package pipocaagil.pipocaagil2026_ciclo3.scheduling.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@ToString
public class Patient {
    PatientId id;
    String name;
    String CPF;
    LocalDate birthDate;
    String email;
    String cellPhone;
    String landPhone;
    PersonWithDesability pwd;

    public Patient (String name, String CPF, LocalDate birthDate, String email, String cellPhone, String landPhone, PersonWithDesability pwd) {
        this.id = new PatientId();
        this.name = name;
        this.CPF = CPF;
        this.birthDate = birthDate;
        this.email = email;
        this.cellPhone = cellPhone;
        this.landPhone = landPhone;
        this.pwd = pwd;
    }

    @Builder(builderMethodName = "restore")
    public Patient (PatientId id, String name, String CPF, LocalDate birthDate, String email, String cellPhone, String landPhone, PersonWithDesability pwd) {
        this.id = id;
        this.name = name;
        this.CPF = CPF;
        this.birthDate = birthDate;
        this.email = email;
        this.cellPhone = cellPhone;
        this.landPhone = landPhone;
        this.pwd = pwd;
    }


}
