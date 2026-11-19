package pipocaagil.pipocaagil2026_ciclo3.scheduling.infrastructure.http;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import pipocaagil.pipocaagil2026_ciclo3.auth.domain.User;
import pipocaagil.pipocaagil2026_ciclo3.auth.infrastructure.security.AuthenticatedUserProvider;
import pipocaagil.pipocaagil2026_ciclo3.scheduling.application.ConfirmUseCase;
import pipocaagil.pipocaagil2026_ciclo3.scheduling.application.FindAvailableSlotsUseCase;
import pipocaagil.pipocaagil2026_ciclo3.scheduling.application.HoldSlotUseCase;
import pipocaagil.pipocaagil2026_ciclo3.scheduling.application.OpenAgendaUseCase;
import pipocaagil.pipocaagil2026_ciclo3.scheduling.application.SaveDoctorUseCase;
import pipocaagil.pipocaagil2026_ciclo3.scheduling.application.SavePatienteUseCase;
import pipocaagil.pipocaagil2026_ciclo3.scheduling.application.request.OpenAgenda;
import pipocaagil.pipocaagil2026_ciclo3.scheduling.domain.Agenda;
import pipocaagil.pipocaagil2026_ciclo3.scheduling.domain.AvailableSlot;
import pipocaagil.pipocaagil2026_ciclo3.scheduling.domain.Doctor;
import pipocaagil.pipocaagil2026_ciclo3.scheduling.infrastructure.http.request.DoctorRequest;
import pipocaagil.pipocaagil2026_ciclo3.scheduling.infrastructure.http.request.OpenAgendaRequest;
import pipocaagil.pipocaagil2026_ciclo3.scheduling.infrastructure.http.request.PatientRequest;
import pipocaagil.pipocaagil2026_ciclo3.scheduling.infrastructure.http.response.HoldResponse;
import pipocaagil.pipocaagil2026_ciclo3.util.mapper.MapperAgenda;

import java.nio.file.attribute.UserPrincipal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/scheduling")
public class SchedulingController {
    private final SavePatienteUseCase savePatienteUseCase;
    private final SaveDoctorUseCase saveDoctorUseCase;
    private final OpenAgendaUseCase openAgendaUseCase;
    private final FindAvailableSlotsUseCase findAvailableSlotsUseCase;
    private final HoldSlotUseCase holdSlotUseCase;
    private final ConfirmUseCase confirmUseCase;
    private final MapperAgenda mapperAgenda;
    private final AuthenticatedUserProvider authenticatedUser;



    public SchedulingController(SavePatienteUseCase savePatienteUseCase,
                                SaveDoctorUseCase saveDoctorUseCase,
                                OpenAgendaUseCase openAgendaUseCase,
                                FindAvailableSlotsUseCase findAvailableSlotsUseCase,
                                HoldSlotUseCase holdSlotUseCase,
                                ConfirmUseCase confirmUseCase,
                                MapperAgenda mapperAgenda,
                                AuthenticatedUserProvider authenticatedUser) {
        this.savePatienteUseCase = savePatienteUseCase;
        this.saveDoctorUseCase = saveDoctorUseCase;
        this.openAgendaUseCase = openAgendaUseCase;
        this.findAvailableSlotsUseCase = findAvailableSlotsUseCase;
        this.holdSlotUseCase = holdSlotUseCase;
        this.confirmUseCase = confirmUseCase;
        this.mapperAgenda = mapperAgenda;
        this.authenticatedUser = authenticatedUser;
    }

    @Operation(
            summary = "Cria e salva cadastro de paciente",
            description = "Cria o cadastro de um novo paciente e salva no banco de dados."
    )
    @PostMapping("/patient/save")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> savePatiente(@Valid @RequestBody PatientRequest request) {
        if (!request.password().equals(request.confirmPassword())){
            return ResponseEntity.badRequest().body("As senhas não coincidem");
        }
        savePatienteUseCase.execute(request);
        return ResponseEntity.ok().build();
    }

    @Operation(
            summary = "Cria e salva cadastro de médico",
            description = "Cria o cadastro de um novo médico e salva no banco de dados."
    )
    @PostMapping("/doctor/save")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public Doctor saveDoctor(@Valid @RequestBody DoctorRequest request) {
        return saveDoctorUseCase.execute(request);
    }

    @Operation(
            summary = "Abre nova agenda",
            description = "Cria as agendas de datas e horários por médico - período máximo de 90 dias"
    )
    @PostMapping("/agenda/open")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public void openAgenda(@Valid @RequestBody OpenAgendaRequest request) {
        openAgendaUseCase.execute(mapperAgenda.entityToDomain(request));
    }

    @Operation(
            summary = "Busca agenda disponível por especialidade",
            description = "Consulta datas e horários disponíveis de todos os médicos por especialidade"
    )
    @GetMapping("/agenda/slots")
    @ResponseStatus(HttpStatus.OK)
    public List<AvailableSlot> available(@RequestParam String specialty,
                                         @RequestParam LocalDate from,
                                         @RequestParam LocalDate to) {
        return findAvailableSlotsUseCase.execute(specialty, from, to);
    }

    @Operation(
            summary = "Reserva a agenda",
            description = "Realiza a reserva da agenda por 60 segundos ou até o paciente confirmar a agenda"
    )
    @PostMapping ("/agenda/slots/{id}/hold")
    @PreAuthorize("hasAnyRole('PATIENT', 'COMPANION')")
    @ResponseStatus(HttpStatus.OK)
    public HoldResponse hold(@PathVariable Long id) {
        UUID patienteId = authenticatedUser.getCurrentPatientId();
        Instant expiresAt = holdSlotUseCase.execute(id,patienteId);
        return new HoldResponse(id, expiresAt, Agenda.HOLD_DURATION.toSeconds());
    }

    @Operation(
            summary = "Confirma a agenda reservada",
            description = "Realiza a confirmação da agenda que foi reservada"
    )
    @PostMapping ("/agenda/slots/{id}/confirmation")
    @PreAuthorize("hasAnyRole('PATIENT', 'COMPANION')")
    @ResponseStatus(HttpStatus.CREATED)
    public void confirm (@PathVariable Long id){
        UUID patienteId = authenticatedUser.getCurrentPatientId();
        confirmUseCase.execute(id,patienteId);
    }

}
