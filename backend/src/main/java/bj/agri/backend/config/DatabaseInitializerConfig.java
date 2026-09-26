package bj.agri.backend.config;

import bj.agri.backend.enums.Authorities;
import bj.agri.backend.enums.TypeUser;
import bj.agri.backend.models.Users;
import bj.agri.backend.repositories.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;

@Configuration
@RequiredArgsConstructor
public class DatabaseInitializerConfig implements ApplicationRunner {

    private final UsersRepository usersRepository;
    private final PasswordEncoder encoder;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        initAdmin();
    }

    private void initAdmin() {

        if (usersRepository.existsByRole(Authorities.ROLE_ADMIN)) {
             return;
         }

        Users users = new Users();
        users.setNpi("1234567890");
        users.setLastname("admin");
        users.setFirstname("admin");
        users.setPassword(encoder.encode("admin@123"));
        users.setPhone("+2290195456548");
        users.setLangue("fr");
        users.setTypeUser(TypeUser.ADMIN);
        users.setRole(Authorities.ROLE_ADMIN);
        users.setDateCreation(LocalDateTime.now());

        usersRepository.save(users);

    }
}
