package pipocaagil.pipocaagil2026_ciclo3.scheduling.domain;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

public record AvailableSlot(Long id, UUID doctorId, String doctorName,
                            LocalDate date, LocalTime time, CareModality modality) {
}
