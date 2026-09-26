package bj.agri.backend.repositories;

import bj.agri.backend.models.Paiement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface PaiementRepository extends JpaRepository<Paiement,Long> {
    //Optional<Object> findByCommandeId(Long id);
    Optional<Paiement> findByCommandeId(Long commandeId);

    @Query("""
        SELECT p FROM Paiement p
        WHERE p.id = :id AND (p.commande.acheteur.id = :userId OR p.commande.offre.users.id = :userId)
        """)
    Optional<Paiement> findByIdAndConcerne(Long id, Long userId);

    @Query("""
        SELECT COALESCE(SUM(p.montantRedevance), 0) FROM Paiement p
        WHERE p.status = 'SUCCESSFULLY'
        AND FUNCTION('to_char', p.dateCreation, 'YYYY-MM') = :periode
        """)
    Double totalRecettesPourPeriode(String periode);

    Optional<Paiement> findByReference(String reference);
}
