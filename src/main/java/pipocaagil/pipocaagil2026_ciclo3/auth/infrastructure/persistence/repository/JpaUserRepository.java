package pipocaagil.pipocaagil2026_ciclo3.auth.infrastructure.persistence.repository;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;
import pipocaagil.pipocaagil2026_ciclo3.auth.domain.User;
import pipocaagil.pipocaagil2026_ciclo3.auth.domain.UserRepository;
import pipocaagil.pipocaagil2026_ciclo3.auth.infrastructure.persistence.entity.UserEntity;
import pipocaagil.pipocaagil2026_ciclo3.util.mapper.MapperUser;

import java.util.UUID;

@Repository
public class JpaUserRepository implements UserRepository, UserDetailsService {
    private final UserEntityRepository userEntityRepository;
    private final MapperUser mapperUser;

    public JpaUserRepository(UserEntityRepository userEntityRepository, MapperUser mapperUser) {
        this.userEntityRepository = userEntityRepository;
        this.mapperUser = mapperUser;
    }

    @Override
    public User save(User user) {
        UserEntity entity = userEntityRepository.save(mapperUser.domainToEntity(user));
        return mapperUser.entityToDomain(entity);
    }

    @Override
    public User findByUserName(String userName) {
        return mapperUser.entityToDomain(userEntityRepository.findByUserName(userName));
    }

    @Override
    public User findByEmail(String email) {
        return mapperUser.entityToDomain(userEntityRepository.findByEmail(email));
    }

    @Override
    public void deleteById(UUID id) {
        userEntityRepository.deleteById(id);

    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userEntityRepository.findUserByEmail(email)
                .orElseThrow(()-> new UsernameNotFoundException(email));
    }
}
