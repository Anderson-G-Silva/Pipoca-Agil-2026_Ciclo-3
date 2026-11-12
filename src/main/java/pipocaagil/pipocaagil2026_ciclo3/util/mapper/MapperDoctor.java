package pipocaagil.pipocaagil2026_ciclo3.util.mapper;

import org.mapstruct.Mapper;
import pipocaagil.pipocaagil2026_ciclo3.scheduling.domain.Doctor;
import pipocaagil.pipocaagil2026_ciclo3.scheduling.domain.DoctorId;
import pipocaagil.pipocaagil2026_ciclo3.scheduling.infrastructure.persistence.entity.DoctorEntity;


import java.util.UUID;

@Mapper(componentModel = "spring")
public interface MapperDoctor {

    // Entity -> Domain
    Doctor entityToDomain (DoctorEntity entity);

    // Domain -> Entity
    DoctorEntity domainToEntity(Doctor domain);

    default UUID mapT(DoctorId id) { return id == null ? null : id.id();}

    default DoctorId mapT(UUID id) { return id == null ? null : new DoctorId(id);}
}
