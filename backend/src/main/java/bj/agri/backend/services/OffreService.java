package bj.agri.backend.services;

import bj.agri.backend.dto.request.CreateOffreRequest;
import bj.agri.backend.dto.response.OffreResponse;
import bj.agri.backend.enums.StatusOffer;
import bj.agri.backend.exceptions.ResourceNotFoundException;
import bj.agri.backend.models.Campagne;
import bj.agri.backend.models.Offre;
import bj.agri.backend.models.Users;
import bj.agri.backend.repositories.CampagneRepository;
import bj.agri.backend.repositories.OffreRepository;
import bj.agri.backend.repositories.UsersRepository;
import bj.agri.backend.security.AuthenticatedUser;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class OffreService {

    private final OffreRepository offreRepository;
    private final CampagneRepository campagneRepository;
    private final UsersRepository usersRepository;

    public OffreResponse faireUneOffre(CreateOffreRequest req, AuthenticatedUser authUser) {

        Campagne campagne = campagneRepository.findByIdAndOwner(req.campagneId(), authUser.userId())
                .orElseThrow(() -> new ResourceNotFoundException("Campagne introuvable"));

        if (!"RECOLTEE".equals(campagne.getStatus())) {
            throw new IllegalStateException("Seule une campagne récoltée peut être mise en vente");
        }

        if (campagne.getQuantityRecoltee() == null || req.quantite() > campagne.getQuantityRecoltee()) {
            throw new IllegalArgumentException("Quantité en vente supérieure à la quantité récoltée");
        }

        Users vendeur = usersRepository.findById(authUser.userId())
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur introuvable"));

        Offre offre = new Offre();
        offre.setCampagne(campagne);
        offre.setUsers(vendeur);
        offre.setQuantity(req.quantite());
        offre.setPrix(req.prix());
        offre.setMarche(req.marche());
        offre.setDateCreation(LocalDateTime.now());
        offre.setStatus(StatusOffer.OPEN);

        return OffreResponse.from(offreRepository.save(offre));
    }

    public Page<OffreResponse> parcourir(String marche, Pageable pageable) {
        Page<Offre> offres = (marche != null)
                ? offreRepository.findByStatusAndMarche("OUVERTE", marche, pageable)
                : offreRepository.findByStatus("OUVERTE", pageable);
        return offres.map(OffreResponse::from);
    }

    public OffreResponse annuler(Long offreId, AuthenticatedUser authUser) {
        Offre offre = offreRepository.findByIdAndOwner(offreId, authUser.userId())
                .orElseThrow(() -> new ResourceNotFoundException("Offre introuvable"));

        if ("VENDUE".equals(offre.getStatus())) {
            throw new IllegalStateException("Une offre déjà vendue ne peut pas être annulée");
        }

        offre.setStatus(StatusOffer.CANCELLED);
        return OffreResponse.from(offreRepository.save(offre));
    }
}
