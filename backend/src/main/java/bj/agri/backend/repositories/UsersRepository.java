package bj.agri.backend.repositories;

import bj.agri.backend.enums.Authorities;
import bj.agri.backend.models.Users;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<Users,Long> {
    Optional<Users> findByNpi(String username);

    Boolean existsByNpi( String npi);

    Boolean existsByRole(Authorities authorities);
}
