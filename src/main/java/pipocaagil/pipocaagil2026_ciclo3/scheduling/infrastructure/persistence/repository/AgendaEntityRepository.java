package pipocaagil.pipocaagil2026_ciclo3.scheduling.infrastructure.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pipocaagil.pipocaagil2026_ciclo3.scheduling.infrastructure.http.request.OpenAgendaRequest;
import pipocaagil.pipocaagil2026_ciclo3.scheduling.infrastructure.persistence.entity.AgendaEntity;

public interface AgendaEntityRepository extends JpaRepository<AgendaEntity, Long> {

}
