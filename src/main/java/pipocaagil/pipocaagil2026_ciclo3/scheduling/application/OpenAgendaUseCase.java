package pipocaagil.pipocaagil2026_ciclo3.scheduling.application;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import pipocaagil.pipocaagil2026_ciclo3.exception.DoctorNotFoundException;
import pipocaagil.pipocaagil2026_ciclo3.exception.InvalidAgendaException;
import pipocaagil.pipocaagil2026_ciclo3.scheduling.application.request.DaysOfWeek;
import pipocaagil.pipocaagil2026_ciclo3.scheduling.application.request.OpenAgenda;
import pipocaagil.pipocaagil2026_ciclo3.scheduling.application.request.OpenAgendaResult;
import pipocaagil.pipocaagil2026_ciclo3.scheduling.application.request.ServiceWindow;
import pipocaagil.pipocaagil2026_ciclo3.scheduling.domain.Agenda;
import pipocaagil.pipocaagil2026_ciclo3.scheduling.domain.AgendaRepository;
import pipocaagil.pipocaagil2026_ciclo3.scheduling.domain.DoctorRepository;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class OpenAgendaUseCase {
    private static final int HORIZONTE_MAXIMO_DIAS = 90;

    private final AgendaRepository agendaRepository;
    private final DoctorRepository doctorRepository;


    public OpenAgendaUseCase(AgendaRepository agendaRepository, DoctorRepository doctorRepository) {
        this.agendaRepository = agendaRepository;
        this.doctorRepository = doctorRepository;
    }

    @Transactional
    public OpenAgendaResult execute(OpenAgenda request) {
        validade(request);

        UUID doctorId = doctorRepository.findByCRMAndUF(request.CRM(), request.UF())
                .orElseThrow(() -> new DoctorNotFoundException(request.CRM(), request.UF()))
                .getId().id();

        Set<DayOfWeek> days = request.daysOfWeek()
                .stream()
                .map(DaysOfWeek::getDayOfWeek)
                .collect(Collectors.toSet());

        List<Agenda> slots = generateSlots (doctorId, request, days);
        int created = agendaRepository.saveIgnoringExisting(slots);

        return new OpenAgendaResult(slots.size(), created, slots.size() - created);
    }

    private List<Agenda> generateSlots(UUID doctorId, OpenAgenda request, Set<DayOfWeek> days) {
        List<Agenda> slots = new ArrayList<>();
        Duration step = Duration.ofMinutes(request.consultationDuration());
        for (LocalDate d = request.startDate(); !d.isAfter(request.endDate()); d = d.plusDays(1)) {
            if(!days.contains(d.getDayOfWeek())) continue;
            for(ServiceWindow w: request.windows()){
                long count = Duration.between(w.start(),w.end()).dividedBy(step);
                for (long i = 0; i < count; i++) {
                    LocalTime t = w.start().plus(step.multipliedBy(i));
                    slots.add(Agenda.open(d,t,request.modality(),doctorId));
                }
            }
        };
        return slots;
    }

    private void validade(OpenAgenda request) {
        if (request.endDate().isBefore(request.startDate())) {
            throw new InvalidAgendaException("Data final anterior a inicial");
        }
        if (request.startDate().isBefore(LocalDate.now())) {
            throw new InvalidAgendaException("Data inicial no passado");
        }
        if (ChronoUnit.DAYS.between(request.startDate(), request.endDate()) > HORIZONTE_MAXIMO_DIAS) {
            throw new InvalidAgendaException("Período máximo de "+HORIZONTE_MAXIMO_DIAS+" dias");
        }
        List<ServiceWindow> sorted = request.windows()
                .stream()
                .sorted(Comparator.comparing(ServiceWindow::start))
                .toList();

        for (int i = 0; i < sorted.size(); i++) {
            ServiceWindow window = sorted.get(i);
            if(!window.start().isBefore(window.end())) {
                throw new InvalidAgendaException("Janela com início maior ou igual a data final");
            }
            if(i>0 && window.start().isBefore(sorted.get(i-1).end())) {
                throw new InvalidAgendaException("Janelas sobrepostas");
            }
        }

    }

}
