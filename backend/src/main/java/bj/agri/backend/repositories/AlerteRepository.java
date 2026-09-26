package bj.agri.backend.repositories;

import bj.agri.backend.enums.TypeAlerte;
import bj.agri.backend.models.Alerte;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.Optional;

public interface AlerteRepository extends JpaRepository<Alerte,Long> {

    @Query("""
        SELECT a FROM Alerte a
        WHERE a.parcelle.owner.id = :userId
        AND (:isRead IS NULL OR a.isRead = :lue)
        ORDER BY a.dateCreation DESC
        """)
    Page<Alerte> findForUser(Long userId, Boolean lue, Pageable pageable);

    @Query("""
        SELECT a FROM Alerte a
        WHERE a.id = :id AND a.parcelle.owner.id = :userId
        """)
    Optional<Alerte> findByIdAndOwner(Long id, Long userId);

    boolean existsByCampagneIdAndTypeAndDateCreationAfter(Long campagneId, TypeAlerte type, LocalDateTime apres);
}
