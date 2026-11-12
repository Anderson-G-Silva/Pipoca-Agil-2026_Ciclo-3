package pipocaagil.pipocaagil2026_ciclo3.scheduling.domain;

import java.util.UUID;

public record DoctorId(UUID id)  {
    public DoctorId () {this(UUID.randomUUID());}
}
