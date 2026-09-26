package bj.agri.backend.repositories;

import bj.agri.backend.models.Culture;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CultureRepository extends JpaRepository<Culture, Long> {
    Optional<Culture> findByName(@NotNull String s);
}
