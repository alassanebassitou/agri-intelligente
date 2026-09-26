package bj.agri.backend.services;

import bj.agri.backend.dto.request.CreateCommandeRequest;
import bj.agri.backend.dto.response.CommandeResponse;
import bj.agri.backend.enums.StatusCommande;
import bj.agri.backend.enums.StatusOffer;
import bj.agri.backend.exceptions.ResourceNotFoundException;
import bj.agri.backend.models.Commande;
import bj.agri.backend.models.Offre;
import bj.agri.backend.models.Users;
import bj.agri.backend.repositories.CommandeRepository;
import bj.agri.backend.repositories.OffreRepository;
import bj.agri.backend.repositories.UsersRepository;
import bj.agri.backend.security.AuthenticatedUser;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CommandeService {

    private final CommandeRepository commandeRepository;
    private final OffreRepository offreRepository;
    private final UsersRepository usersRepository;

    @Transactional
    public CommandeResponse creer(CreateCommandeRequest req, AuthenticatedUser authUser) {
        // Verrou pessimiste pour éviter que deux acheteurs commandent en même temps
        // au-delà de la quantité disponible (voir repository ci-dessous)
        Offre offre = offreRepository.findByIdForUpdate(req.offreId())
                .orElseThrow(() -> new ResourceNotFoundException("Offre introuvable"));

        if (!"OUVERTE".equals(offre.getStatus())) {
            throw new IllegalStateException("Cette offre n'est plus disponible");
        }

        double dejaCommande = commandeRepository.sommeQuantitesCommandees(offre.getId());
        double disponible = offre.getQuantity() - dejaCommande;

        if (req.quantite() > disponible) {
            throw new IllegalArgumentException(
                    "Quantité indisponible : il reste %.1f unités".formatted(disponible));
        }

        Users acheteur = usersRepository.findById(authUser.userId())
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur introuvable"));

        Commande commande = new Commande();
        commande.setOffre(offre);
        commande.setAcheteur(acheteur);
        commande.setQuantity(req.quantite());
        commande.setStatus(StatusCommande.PENDING);
        commande.setDateCommande(LocalDate.now());
        commande.setDateCreation(LocalDateTime.now());

        Commande saved = commandeRepository.save(commande);

        // Si toute la quantité disponible est atteinte, l'offre passe à VENDUE
        if (req.quantite() >= disponible) {
            offre.setStatus(StatusOffer.SELL);
            offreRepository.save(offre);
        }

        return CommandeResponse.from(saved);
    }

    public Page<CommandeResponse> mesCommandes(Pageable pageable, AuthenticatedUser authUser) {
        return commandeRepository.findByAcheteurId(authUser.userId(), pageable)
                .map(CommandeResponse::from);
    }

    public CommandeResponse detail(Long id, AuthenticatedUser authUser) {
        return commandeRepository.findByIdAndConcerne(id, authUser.userId())
                .map(CommandeResponse::from)
                .orElseThrow(() -> new ResourceNotFoundException("Commande introuvable"));
    }
}
