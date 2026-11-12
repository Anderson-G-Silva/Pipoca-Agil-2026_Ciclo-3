package pipocaagil.pipocaagil2026_ciclo3.scheduling.application;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import pipocaagil.pipocaagil2026_ciclo3.exception.HoldExpiredException;
import pipocaagil.pipocaagil2026_ciclo3.scheduling.domain.AgendaRepository;

import java.util.UUID;

@Service
public class ConfirmUseCase {
    private final AgendaRepository agendaRepository;

    public ConfirmUseCase(AgendaRepository agendaRepository) {
        this.agendaRepository = agendaRepository;
    }

    @Transactional
    public void execute(Long slotId, UUID patientId) {
        if (!agendaRepository.confirm(slotId, patientId)) {
            throw new HoldExpiredException("O tempo de reserva deste horário terminou. Selecione o horário novamente.");
        }
    }
}
