package bj.agri.backend.repositories;

import bj.agri.backend.models.Commande;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CommandeRepository extends JpaRepository<Commande,Long> {

    Page<Commande> findByAcheteurId(Long acheteurId, Pageable pageable);

    @Query("""
        SELECT c FROM Commande c
        WHERE c.id = :id AND (c.acheteur.id = :userId OR c.offre.users.id = :userId)
        """)
    Optional<Commande> findByIdAndConcerne(Long id, Long userId);

    @Query("SELECT COALESCE(SUM(c.quantity), 0) FROM Commande c WHERE c.offre.id = :offreId AND c.status != 'CANCELLED'")
    Double sommeQuantitesCommandees(Long offreId);
}
