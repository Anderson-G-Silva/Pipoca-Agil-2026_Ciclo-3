package pipocaagil.pipocaagil2026_ciclo3.scheduling.application.request;

import lombok.Getter;

import java.time.DayOfWeek;

import static java.time.DayOfWeek.*;

@Getter
public enum DaysOfWeek {
    SEG (MONDAY),
    TER (TUESDAY),
    QUA (WEDNESDAY),
    QUI (THURSDAY),
    SEX (FRIDAY),
    SAB (SATURDAY),
    DOM (SUNDAY);

    private final DayOfWeek dayOfWeek;
    DaysOfWeek(DayOfWeek dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }
}
