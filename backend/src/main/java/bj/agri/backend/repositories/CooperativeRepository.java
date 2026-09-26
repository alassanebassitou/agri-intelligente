package bj.agri.backend.repositories;

import bj.agri.backend.models.Cooperative;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CooperativeRepository extends JpaRepository<Cooperative,Long> {
    Optional<Cooperative> findByCodeInvitation(String codeInvitation);
}
