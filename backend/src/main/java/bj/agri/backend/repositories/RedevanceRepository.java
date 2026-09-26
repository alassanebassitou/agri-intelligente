package bj.agri.backend.repositories;

import bj.agri.backend.models.Redevance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RedevanceRepository extends JpaRepository<Redevance,Long> {
    Optional<Redevance> findByMarche(String marche);
}
