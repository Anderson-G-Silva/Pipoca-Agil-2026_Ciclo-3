package pipocaagil.pipocaagil2026_ciclo3.auth.infrastructure.security;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import pipocaagil.pipocaagil2026_ciclo3.auth.domain.User;
import pipocaagil.pipocaagil2026_ciclo3.auth.domain.UserRepository;
import pipocaagil.pipocaagil2026_ciclo3.auth.infrastructure.persistence.repository.UserEntityRepository;

import java.time.LocalDate;
import java.util.UUID;

import static pipocaagil.pipocaagil2026_ciclo3.auth.domain.UserType.ADMIN;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
public class SecurityConfig {
    // Configurações do ponto de entrada
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http, RestUsernamePasswordAuthenticationFilter restUsernamePasswordAuthenticationFilter) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)

                .securityContext(context -> context.requireExplicitSave(false))

                .authorizeHttpRequests(auth -> auth
                        //.requestMatchers("/user/api/auth/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "/user", "/user/api/auth/login").permitAll()
                        .requestMatchers("/scheduling/patient/save**").permitAll()
                        .requestMatchers("/user/companion/save**").permitAll()
                        .requestMatchers("/user/employee/save**").hasRole("ADMIN")
                        .requestMatchers(
                                "/v3/api-docs/**",
                                "/swagger-ui/**",
                                "/swagger-ui.html"
                        ).permitAll()
                        .anyRequest().authenticated())

                .addFilterAt(restUsernamePasswordAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
        ;

        return http.build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuditorAware<UUID> auditorProvider(AuthenticatedUserProvider userProvider) {
        return new AuditorAwareImpl(userProvider);
    }

    @Bean
    CommandLineRunner initDatabase(UserRepository repository, UserEntityRepository entityRepository) {
        return args -> {

            if (entityRepository.count() == 0) {

                var password = (passwordEncoder().encode("Admin"));
                var Admin = new User("Admin","Admin@gmail.com", LocalDate.of(1970,1,1), password, ADMIN);
                repository.save(Admin);

            }
        };
    }

}
