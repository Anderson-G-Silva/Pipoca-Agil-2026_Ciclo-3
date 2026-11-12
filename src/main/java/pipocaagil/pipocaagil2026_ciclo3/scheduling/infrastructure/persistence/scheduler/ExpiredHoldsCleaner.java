package pipocaagil.pipocaagil2026_ciclo3.scheduling.infrastructure.persistence.scheduler;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ExpiredHoldsCleaner {

    private final JdbcTemplate jdbc;

    public ExpiredHoldsCleaner(JdbcTemplate jdbc) { this.jdbc = jdbc; }

    @Scheduled(fixedDelay = 60_000)
    public void release() {
        jdbc.update("""
            UPDATE agenda
               SET status = 'DISPONIVEL', patient_id = NULL, held_until = NULL
             WHERE status = 'EM_RESERVA' AND held_until <= now()
            """);
    }
}
