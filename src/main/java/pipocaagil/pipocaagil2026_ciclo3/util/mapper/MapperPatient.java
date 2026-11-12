package pipocaagil.pipocaagil2026_ciclo3.util.mapper;

import org.mapstruct.Mapper;
import pipocaagil.pipocaagil2026_ciclo3.scheduling.domain.Patient;
import pipocaagil.pipocaagil2026_ciclo3.scheduling.domain.PatientId;
import pipocaagil.pipocaagil2026_ciclo3.scheduling.infrastructure.persistence.entity.PatientEntity;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface MapperPatient {

    // Entity -> Domain
    Patient entityToDomain (PatientEntity entity);

    // Domain -> Entity
    PatientEntity domainToEntity(Patient domain);

    default UUID mapT(PatientId id) { return id == null ? null : id.id();}

    default PatientId mapT(UUID id) { return id == null ? null : new PatientId(id);}
}
