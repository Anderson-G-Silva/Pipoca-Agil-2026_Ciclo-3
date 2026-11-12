package pipocaagil.pipocaagil2026_ciclo3.scheduling.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class Doctor {
    DoctorId id;
    String name;
    String CRM;
    UfCrm UF;
    MedicalSpecialty specialty;

    public Doctor(String name, String CRM, UfCrm UF, MedicalSpecialty specialty) {
        this.id = new DoctorId();
        this.name = name;
        this.CRM = CRM;
        this.UF = UF;
        this.specialty = specialty;
    }

    @Builder(builderMethodName = "restore")
    public Doctor(DoctorId id, String name, String CRM, UfCrm UF, MedicalSpecialty specialty) {
        this.id = id;
        this.name = name;
        this.CRM = CRM;
        this.UF = UF;
        this.specialty = specialty;
    }


}
