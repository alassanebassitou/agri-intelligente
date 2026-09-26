package bj.agri.backend.services;

import bj.agri.backend.dto.request.ClotureCampagneRequest;
import bj.agri.backend.dto.request.CreateCampagneRequest;
import bj.agri.backend.dto.response.CampagneResponse;
import bj.agri.backend.exceptions.ResourceNotFoundException;
import bj.agri.backend.models.Campagne;
import bj.agri.backend.models.Culture;
import bj.agri.backend.models.Parcelle;
import bj.agri.backend.repositories.CampagneRepository;
import bj.agri.backend.repositories.CultureRepository;
import bj.agri.backend.repositories.ParcelleRepository;
import bj.agri.backend.security.AuthenticatedUser;
import bj.agri.backend.services.monitoring.MoteurAlertes;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CampagneService {

    private final CampagneRepository campagneRepository;
    private final ParcelleRepository parcelleRepository;
    private final CultureRepository cultureRepository;
    private final MoteurAlertes moteurAlertes;

    public CampagneResponse creer(CreateCampagneRequest req, AuthenticatedUser authUser) {

        Parcelle parcelle = parcelleRepository.findByIdAndOwnerId(req.parcelleId(), authUser.userId())
                .orElseThrow(() -> new ResourceNotFoundException("Parcelle introuvable"));

        Culture culture = cultureRepository.findByName(req.cultureName())
                .orElseThrow(() -> new ResourceNotFoundException("Culture introuvable"));

        Campagne campagne = new Campagne();
        campagne.setParcelle(parcelle);
        campagne.setCulture(culture);
        campagne.setDateSemis(req.dateSemis());
        campagne.setDateRecolteEstimee(req.dateSemis().plusDays(culture.getDureeCycleJours()));
        campagne.setStatus("EN_COURS");
        campagne.setDateCreation(LocalDateTime.now());

        Campagne saved = campagneRepository.save(campagne);

        // Première évaluation immédiate (ex. alerte semis tardif/précoce)
        //moteurAlertes.evaluer(saved);

        return CampagneResponse.from(saved);
    }

    public List<CampagneResponse> listerPourParcelle(Long parcelleId, AuthenticatedUser authUser) {
        parcelleRepository.findByIdAndOwnerId(parcelleId, authUser.userId())
                .orElseThrow(() -> new ResourceNotFoundException("Parcelle introuvable"));

        return campagneRepository.findByParcelleIdOrderByDateSemisDesc(parcelleId)
                .stream().map(CampagneResponse::from).toList();
    }

    public CampagneResponse cloturer(Long campagneId, ClotureCampagneRequest req, AuthenticatedUser authUser) {
        Campagne campagne = campagneRepository.findByIdAndOwner(campagneId, authUser.userId())
                .orElseThrow(() -> new ResourceNotFoundException("Campagne introuvable"));

        if (!List.of("RECOLTEE", "ABANDONNEE").contains(req.status())) {
            throw new IllegalArgumentException("Statut invalide");
        }

        campagne.setStatus(req.status());
        if ("RECOLTEE".equals(req.status())) {
            campagne.setQuantityRecoltee(req.quantiteRecoltee());
        }

        return CampagneResponse.from(campagneRepository.save(campagne));
    }
}
