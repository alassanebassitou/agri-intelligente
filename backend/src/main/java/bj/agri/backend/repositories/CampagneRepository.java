package bj.agri.backend.repositories;

import bj.agri.backend.models.Campagne;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CampagneRepository extends JpaRepository<Campagne,Long> {
    Optional<Campagne> findTopByParcelleIdOrderByDateSemisDesc(Long parcelleId);

    List<Campagne> findByParcelleIdOrderByDateSemisDesc(Long parcelleId);

    @Query("""
        SELECT c FROM Campagne c
        WHERE c.id = :id AND c.parcelle.owner.id = :userId
        """)
    Optional<Campagne> findByIdAndOwner(Long id, Long userId);

    List<Campagne> findByStatus(String enCours);
}
