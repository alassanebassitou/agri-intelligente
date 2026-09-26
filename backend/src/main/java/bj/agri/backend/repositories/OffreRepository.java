package bj.agri.backend.repositories;

import bj.agri.backend.models.Offre;
import jakarta.persistence.LockModeType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface OffreRepository extends JpaRepository<Offre,Long> {

    Page<Offre> findByStatusAndMarche(String statut, String marche, Pageable pageable);

    Page<Offre> findByStatus(String statut, Pageable pageable);

    @Query("""
        SELECT o FROM Offre o
        WHERE o.id = :id AND o.users.id = :userId
        """)
    Optional<Offre> findByIdAndOwner(Long id, Long userId);

    @Query("""
        SELECT o FROM Offre o WHERE o.campagne.id = :campagneId
        """)
    List<Offre> findByCampagneId(Long campagneId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT o FROM Offre o WHERE o.id = :id")
    Optional<Offre> findByIdForUpdate(Long id);
}
