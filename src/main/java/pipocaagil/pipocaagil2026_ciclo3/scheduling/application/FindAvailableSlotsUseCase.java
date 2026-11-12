package pipocaagil.pipocaagil2026_ciclo3.scheduling.application;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import pipocaagil.pipocaagil2026_ciclo3.scheduling.domain.AgendaRepository;
import pipocaagil.pipocaagil2026_ciclo3.scheduling.domain.AvailableSlot;

import java.time.LocalDate;
import java.util.List;

@Service
public class FindAvailableSlotsUseCase {
    private final AgendaRepository agendaRepository;

    public FindAvailableSlotsUseCase(AgendaRepository agendaRepository) {
        this.agendaRepository = agendaRepository;
    }

    @Transactional(readOnly = true)
    public List<AvailableSlot> execute(String specialty, LocalDate from, LocalDate to) {
        return agendaRepository.findAvailable(specialty, from, to);
    }
}
