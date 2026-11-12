package pipocaagil.pipocaagil2026_ciclo3.auth.domain;

import java.util.UUID;

public record UserId (UUID id){
    public UserId () {this(UUID.randomUUID());}
}
