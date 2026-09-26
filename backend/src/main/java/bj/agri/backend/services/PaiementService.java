package bj.agri.backend.services;

import bj.agri.backend.dto.response.PaiementResponse;
import bj.agri.backend.enums.StatusCommande;
import bj.agri.backend.enums.StatusPayment;
import bj.agri.backend.exceptions.ResourceNotFoundException;
import bj.agri.backend.models.Commande;
import bj.agri.backend.models.Paiement;
import bj.agri.backend.repositories.CommandeRepository;
import bj.agri.backend.dto.request.InitierPaiementRequest;
import bj.agri.backend.repositories.PaiementRepository;
import bj.agri.backend.security.AuthenticatedUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaiementService {

    private final PaiementRepository paiementRepository;
    private final CommandeRepository commandeRepository;
    private final RedevanceService redevanceService;

    public PaiementResponse initier(InitierPaiementRequest req, AuthenticatedUser authUser) {
        Commande commande = commandeRepository.findByIdAndConcerne(req.commandeId(), authUser.userId())
                .orElseThrow(() -> new ResourceNotFoundException("Commande introuvable"));

        if (!commande.getAcheteur().getId().equals(authUser.userId())) {
            throw new AccessDeniedException("Seul l'acheteur peut initier le paiement");
        }

        if (paiementRepository.findByCommandeId(commande.getId()).isPresent()) {
            throw new IllegalStateException("Cette commande a déjà un paiement");
        }

        double montant = commande.getQuantity() * commande.getOffre().getPrix().doubleValue();

        Paiement paiement = new Paiement();
        paiement.setCommande(commande);
        paiement.setAmount(BigDecimal.valueOf(montant));
        paiement.setPrestataire(req.prestataire());
        paiement.setStatus(StatusPayment.PENDING);
        paiement.setDateCreation(LocalDateTime.now());
        paiement.setReference(genererReference());

        return PaiementResponse.from(paiementRepository.save(paiement));
    }

    @Transactional
    public void confirmerParWebhook(String reference, String statutPrestataire) {
        Paiement paiement = paiementRepository.findByReference(reference)
                .orElseThrow(() -> new ResourceNotFoundException("Paiement introuvable"));

        paiement.setStatus("SUCCESSFULLY".equals(statutPrestataire) ? StatusPayment.SUCCESSFULLY : StatusPayment.FAILED);
        paiementRepository.save(paiement);

        if ("SUCCESSFULLY".equals(paiement.getStatus())) {

            String marche = paiement.getCommande().getOffre().getMarche();

            var resultat = redevanceService.calculerRedevance(marche, paiement.getAmount().doubleValue());
            paiement.setMontantRedevance(BigDecimal.valueOf(resultat.montant()));
            paiement.setRedevance(resultat.redevance());

            Commande commande = paiement.getCommande();
            commande.setStatus(StatusCommande.PAYED);
            commandeRepository.save(commande);
        }
    }

    private String genererReference() {
        return "PAY-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
}
