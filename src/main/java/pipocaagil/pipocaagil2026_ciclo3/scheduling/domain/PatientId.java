package pipocaagil.pipocaagil2026_ciclo3.scheduling.domain;

import java.util.UUID;

public record PatientId (UUID id) {
    public PatientId () {this(UUID.randomUUID());}
}
