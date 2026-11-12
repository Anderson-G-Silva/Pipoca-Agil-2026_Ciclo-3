package pipocaagil.pipocaagil2026_ciclo3.scheduling.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

import static pipocaagil.pipocaagil2026_ciclo3.scheduling.domain.StatusAgenda.DISPONIVEL;

@Getter
@NoArgsConstructor
public class Agenda {
    Long id;
    LocalDate slotDate;
    LocalTime slotTime;
    CareModality modality;
    UUID doctorId;
    UUID patientId;
    StatusAgenda status;
    Instant heldUntil;

    public Agenda(LocalDate slotDate, LocalTime slotTime, CareModality modality, UUID doctorId, UUID patientId, StatusAgenda status,  Instant heldUntil) {
        this.slotDate = slotDate;
        this.slotTime = slotTime;
        this.modality = modality;
        this.doctorId = doctorId;
        this.patientId = patientId;
        this.status = status;
        this.heldUntil = heldUntil;
    }

    //um slot recém-aberto nasce disponível, sem paciente e sem reserva” fica no domínio, e o caso de uso só chama Agenda.open
    public static Agenda open(LocalDate date, LocalTime time, CareModality modality, UUID doctorId) {
        return new Agenda(date, time, modality, doctorId, null, DISPONIVEL, null);
    }

    public static final Duration HOLD_DURATION = Duration.ofSeconds(60);
}
