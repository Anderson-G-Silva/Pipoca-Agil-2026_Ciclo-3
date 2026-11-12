package pipocaagil.pipocaagil2026_ciclo3.util.mapper;

import org.mapstruct.Mapper;
import pipocaagil.pipocaagil2026_ciclo3.scheduling.application.request.OpenAgenda;
import pipocaagil.pipocaagil2026_ciclo3.scheduling.domain.Agenda;
import pipocaagil.pipocaagil2026_ciclo3.scheduling.infrastructure.http.request.OpenAgendaRequest;
import pipocaagil.pipocaagil2026_ciclo3.scheduling.infrastructure.persistence.entity.AgendaEntity;

@Mapper(componentModel = "spring")
public interface MapperAgenda {

    // Entity -> Domain
    Agenda entityToDomain (AgendaEntity entity);

    OpenAgenda entityToDomain (OpenAgendaRequest request);

    // Domain -> Entity
    AgendaEntity domainToEntity(Agenda domain);


    OpenAgendaRequest domainToEntity(OpenAgenda request);
}
