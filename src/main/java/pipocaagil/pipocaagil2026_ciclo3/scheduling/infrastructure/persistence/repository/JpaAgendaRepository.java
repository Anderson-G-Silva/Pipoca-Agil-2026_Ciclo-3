package pipocaagil.pipocaagil2026_ciclo3.scheduling.infrastructure.persistence.repository;

import org.springframework.data.domain.AuditorAware;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import pipocaagil.pipocaagil2026_ciclo3.scheduling.domain.Agenda;
import pipocaagil.pipocaagil2026_ciclo3.scheduling.domain.AgendaRepository;
import pipocaagil.pipocaagil2026_ciclo3.scheduling.domain.AvailableSlot;
import pipocaagil.pipocaagil2026_ciclo3.scheduling.domain.CareModality;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class JpaAgendaRepository implements AgendaRepository {
    private final AgendaEntityRepository agendaEntityRepository;
    private final JdbcTemplate jdbc;
    private final AuditorAware<UUID> auditor;

    public JpaAgendaRepository(AgendaEntityRepository agendaEntityRepository,  JdbcTemplate jdbc, AuditorAware<UUID> auditor) {
        this.agendaEntityRepository = agendaEntityRepository;
        this.jdbc = jdbc;
        this.auditor = auditor;
    }

    private static final int BATCH_SIZE = 500;

    private static final String ZONE = "America/Sao_Paulo";

    private UUID actor() {
        return auditor.getCurrentAuditor().orElse(null);
    }

    private static String str(UUID id) {
        return id == null ? null : id.toString();
    }

    private static final String INSERT_SQL = """
    INSERT INTO agenda (slot_date, slot_time, modality, doctor_id, status,
                        created_at, created_by, updated_at, updated_by)
    VALUES (?, ?, ?, ?, ?, now(), ?, now(), ?)
    ON CONFLICT (doctor_id, slot_date, slot_time) DO NOTHING
    """;

    private static final String HOLD_SQL = """
    UPDATE agenda
       SET status = 'EM_RESERVA',
           patient_id = ?,
           held_until = now() + (? * interval '1 second'),
           updated_at = now(),
           updated_by = ?
     WHERE id = ?
       AND ((slot_date + slot_time) AT TIME ZONE '%s') > now()
       AND ( status = 'DISPONIVEL'
          OR (status = 'EM_RESERVA' AND (held_until <= now() OR patient_id = ?)) )
    RETURNING held_until
    """.formatted(ZONE);

    private static final String CONFIRM_SQL = """
    UPDATE agenda
       SET status = 'AGENDADO', 
           held_until = NULL,
           updated_at = now(),
           updated_by = ?
     WHERE id = ? AND status = 'EM_RESERVA'
       AND patient_id = ? AND held_until > now()
    """;

    private static final String RELEASE_SQL = """
    UPDATE agenda
       SET status = 'DISPONIVEL', patient_id = NULL, 
           held_until = NULL, 
           updated_at = now(),
           updated_by = ?
     WHERE id = ? AND status = 'EM_RESERVA' AND patient_id = ?
    """;

    private static final String AVAILABLE_SQL = """
    SELECT a.id, a.doctor_id, d.name AS doctor_name,
           a.slot_date, a.slot_time, a.modality
      FROM agenda a
      JOIN doctor d ON d.id = a.doctor_id
     WHERE d.specialty = ?
       AND a.slot_date BETWEEN ? AND ?
       AND ((a.slot_date + a.slot_time) AT TIME ZONE '%s') > now()
       AND ( a.status = 'DISPONIVEL'
          OR (a.status = 'EM_RESERVA' AND a.held_until <= now()) )
     ORDER BY a.slot_date, a.slot_time, d.name
    """.formatted(ZONE);



    @Override
    public int saveIgnoringExisting(List<Agenda> slots) {
        if (slots.isEmpty()) return 0;
        String actor = str(actor());

        int[][] results = jdbc.batchUpdate(INSERT_SQL, slots, BATCH_SIZE, (ps, slot) -> {
            ps.setObject(1, slot.getSlotDate());
            ps.setObject(2, slot.getSlotTime());
            ps.setString(3, slot.getModality().name());
            ps.setString(4, slot.getDoctorId().toString());
            ps.setString(5, slot.getStatus().name());
            ps.setString(6, actor);           // created_by
            ps.setString(7, actor);           // updated_by
        });

        int created = 0;
        for (int[] batch : results) {
            for (int rows : batch) {
                if (rows > 0) created++;
            }
        }
        return created;

    }

    @Override
    public Optional<Instant> hold(Long slotId, UUID patientId, Duration holdTime) {
        List<Instant> result = jdbc.query(HOLD_SQL,
                (rs, i) -> rs.getObject("held_until", OffsetDateTime.class).toInstant(),
                patientId.toString(), holdTime.toSeconds(), str(actor()), slotId, patientId.toString());
        return result.stream().findFirst();
    }

    @Override
    public boolean confirm(Long slotId, UUID patientId) {
        return jdbc.update(CONFIRM_SQL, str(actor()), slotId, patientId.toString()) == 1;
    }

    @Override
    public boolean release(Long slotId, UUID patientId) {
        return jdbc.update(RELEASE_SQL, str(actor()), slotId, patientId.toString()) == 1;
    }

    @Override
    public List<AvailableSlot> findAvailable(String specialty, LocalDate from, LocalDate to) {
        return jdbc.query(AVAILABLE_SQL, (rs, i) -> new AvailableSlot(
                        rs.getLong("id"),
                        UUID.fromString(rs.getString("doctor_id").trim()),
                        rs.getString("doctor_name"),
                        rs.getObject("slot_date", LocalDate.class),
                        rs.getObject("slot_time", LocalTime.class),
                        CareModality.valueOf(rs.getString("modality"))),
                specialty, from, to);
    }
}
