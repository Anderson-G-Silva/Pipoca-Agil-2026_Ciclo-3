package pipocaagil.pipocaagil2026_ciclo3.scheduling.application;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import pipocaagil.pipocaagil2026_ciclo3.exception.SlotUnavailableException;
import pipocaagil.pipocaagil2026_ciclo3.scheduling.domain.Agenda;
import pipocaagil.pipocaagil2026_ciclo3.scheduling.domain.AgendaRepository;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

@Service
public class HoldSlotUseCase {
    private final AgendaRepository agendaRepository;

    public HoldSlotUseCase(AgendaRepository agendaRepository) {
        this.agendaRepository = agendaRepository;
    }

    @Transactional
    public Instant execute(Long slotId, UUID patientId) {
        return agendaRepository.hold(slotId, patientId, Agenda.HOLD_DURATION)
                .orElseThrow(SlotUnavailableException::new);
    }
}
