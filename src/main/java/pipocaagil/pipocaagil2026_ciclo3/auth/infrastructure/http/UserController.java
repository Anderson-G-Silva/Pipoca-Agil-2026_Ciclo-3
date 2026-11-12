package pipocaagil.pipocaagil2026_ciclo3.auth.infrastructure.http;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import pipocaagil.pipocaagil2026_ciclo3.auth.application.FindByEmailUseCase;
import pipocaagil.pipocaagil2026_ciclo3.auth.application.SaveUserUserCase;
import pipocaagil.pipocaagil2026_ciclo3.auth.infrastructure.http.request.CompanionRequest;
import pipocaagil.pipocaagil2026_ciclo3.auth.infrastructure.http.request.EmployeeRequest;
import pipocaagil.pipocaagil2026_ciclo3.scheduling.application.FindPacientByCpfUseCase;
import pipocaagil.pipocaagil2026_ciclo3.scheduling.domain.Patient;
import pipocaagil.pipocaagil2026_ciclo3.util.mapper.MapperUser;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.UUID;

import static pipocaagil.pipocaagil2026_ciclo3.auth.domain.UserType.COMPANION;
import static pipocaagil.pipocaagil2026_ciclo3.auth.domain.UserType.PATIENT;

@RestController
@RequestMapping("/user")
public class UserController {
    private final SaveUserUserCase saveUserUserCase;
    private final FindPacientByCpfUseCase findPacientByCpfUseCase;
    private final MapperUser mapperUser;

    public UserController(SaveUserUserCase saveUserUserCase, MapperUser mapperUser, FindPacientByCpfUseCase findPacientByCpfUseCase) {
        this.saveUserUserCase = saveUserUserCase;
        this.mapperUser = mapperUser;
        this.findPacientByCpfUseCase = findPacientByCpfUseCase;
    }

    @Operation(
            summary = "Cria e salva usuário acompanhante",
            description = "Cria o usuário como acompanhante de paciente e salva no banco de dados."
    )
    @PostMapping("/companion/save")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> saveUserCompanion(@Valid @RequestBody CompanionRequest request) {
        Optional<Patient> patient = findPacientByCpfUseCase.execute(request.accompaniedPatientCPF());
        UUID patientId;
        if (!patient.isPresent()) {
            return ResponseEntity.badRequest().body("Paciente com CPF "+request.accompaniedPatientCPF()+" não foi enconrtrado");
        }
        patientId = patient.get().getId().id();
        var age = ChronoUnit.YEARS.between(request.birthDate(), LocalDate.now());
        if (age <= CompanionRequest.USER_TYPE.getMinimunAge()) {
            return ResponseEntity.badRequest().body("Idade mínima permitida é de " + CompanionRequest.USER_TYPE.getMinimunAge() + " anos. Idade fornecida: " + age);
        }
        saveUserUserCase.execute(mapperUser.userRequestCompanionToDomain(request, patientId));
        return ResponseEntity.ok().build();
    }

    @Operation(
            summary = "Cria e salva usuário colaborador da clínica ou laboratório",
            description = "Cria o usuário como colaborador da clínica ou laboratório e salva no banco de dados."
    )
    @PostMapping("/employee/save")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> saveUserEmpolyee(@Valid @RequestBody EmployeeRequest request) {
        if (request.userType().equals(PATIENT) || request.userType().equals(COMPANION)){
            return ResponseEntity.badRequest().body("Tipo de colaborador inválido");
        };
        var age = ChronoUnit.YEARS.between(request.birthDate(), LocalDate.now());
        if (age <= request.userType().getMinimunAge()) {
            return ResponseEntity.badRequest().body("Idade mínima permitida é de " + request.userType().getMinimunAge() + " anos. Idade fornecida: " + age);
        }
        saveUserUserCase.execute(mapperUser.userRequestEmployeeToDomain(request));
        return ResponseEntity.ok().build();
    }
}
