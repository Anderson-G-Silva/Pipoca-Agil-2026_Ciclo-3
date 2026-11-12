package pipocaagil.pipocaagil2026_ciclo3.scheduling.domain;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AgendaRepository {
    int saveIgnoringExisting(List<Agenda> slots);

    Optional<Instant> hold (Long slotId, UUID patientId, Duration holdTime);

    boolean confirm (Long slotId, UUID patientId);

    boolean release (Long slotId, UUID patientId);

    List<AvailableSlot> findAvailable(String specialty, LocalDate from, LocalDate to);

}
