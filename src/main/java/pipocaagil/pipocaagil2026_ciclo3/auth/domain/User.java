package pipocaagil.pipocaagil2026_ciclo3.auth.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    UserId id;
    String userName;
    String email;
    LocalDate birthDate;
    String password;
    UserType userType;
    UUID patientId;

public User(String userName, String email,LocalDate birthDate, String password, UserType userType) {
    this.id = new UserId();
    this.userName = userName;
    this.email = email;
    this.birthDate = birthDate;
    this.password = password;
    this.userType = userType;
}

public User (String userName, String email, LocalDate birthDate, String password, UserType userType, UUID patientId) {
    this.id = new UserId();
    this.userName = userName;
    this.email = email;
    this.birthDate = birthDate;
    this.password = password;
    this.userType = userType;
    this.patientId = patientId;
}

}
