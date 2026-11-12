package pipocaagil.pipocaagil2026_ciclo3.util.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pipocaagil.pipocaagil2026_ciclo3.auth.application.request.UserRequest;
import pipocaagil.pipocaagil2026_ciclo3.auth.domain.User;
import pipocaagil.pipocaagil2026_ciclo3.auth.domain.UserId;
import pipocaagil.pipocaagil2026_ciclo3.auth.infrastructure.http.request.CompanionRequest;
import pipocaagil.pipocaagil2026_ciclo3.auth.infrastructure.http.request.EmployeeRequest;
import pipocaagil.pipocaagil2026_ciclo3.auth.infrastructure.persistence.entity.UserEntity;
import pipocaagil.pipocaagil2026_ciclo3.scheduling.domain.PatientId;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface MapperUser {

    // Entity -> Domain
    User entityToDomain (UserEntity entity);

    // Domain -> Entity
    UserEntity domainToEntity(User domain);

    // Entity -> Domain (DTO)

    @Mapping(target = "userType", expression = "java(CompanionRequest.USER_TYPE)") // Parametro necessário para busar a constante do tipo de usuário
    @Mapping(target = "patientId", source = "patientId")
    UserRequest userRequestCompanionToDomain(CompanionRequest request, UUID patientId);

    UserRequest userRequestEmployeeToDomain(EmployeeRequest request);


    default UUID mapT(UserId id) { return id == null ? null : id.id();}

    default UserId mapT(UUID id) { return id == null ? null : new UserId(id);}


    default UUID mapT1(PatientId id) { return id == null ? null : id.id();}

    default PatientId mapT1(UUID id) { return id == null ? null : new PatientId(id);}
}
