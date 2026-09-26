package bj.agri.backend.services;

import bj.agri.backend.dto.response.SuggestionCulture;
import bj.agri.backend.models.Campagne;
import bj.agri.backend.models.Culture;
import bj.agri.backend.models.ObservationClimat;
import bj.agri.backend.models.Parcelle;
import bj.agri.backend.repositories.CampagneRepository;
import bj.agri.backend.repositories.CultureRepository;
import bj.agri.backend.repositories.ObservationClimatRepository;
import bj.agri.backend.repositories.ParcelleRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SuggestionCultureService {

    private final CultureRepository cultureRepository;
    private final ObservationClimatRepository observationRepository;
    private final CampagneRepository campagneRepository;
    private final ParcelleRepository parcelleRepository;

    public List<SuggestionCulture> suggererPour(Long parcelleId) {

        Parcelle parcelle = parcelleRepository.findById(parcelleId)
                .orElseThrow(() -> new EntityNotFoundException("Parcelle introuvable"));

        var observations = observationRepository
                .findTop30ByParcelleIdOrderByJourDesc(parcelleId);

        double tempMoyenne = observations.stream()
                .mapToDouble(ObservationClimat::getTemperature).average().orElse(27.0);
        double pluieCumulee = observations.stream()
                .mapToDouble(ObservationClimat::getPluieMm).sum();

        var derniereCampagne = campagneRepository
                .findTopByParcelleIdOrderByDateSemisDesc(parcelleId);

        int moisActuel = LocalDate.now().getMonthValue();

        return cultureRepository.findAll().stream()
                .map(culture -> evaluer(culture, parcelle, tempMoyenne, pluieCumulee,
                        moisActuel, derniereCampagne))
                .sorted(Comparator.comparingInt(SuggestionCulture::score).reversed())
                .limit(5)
                .toList();
    }

    private SuggestionCulture evaluer(Culture culture, Parcelle parcelle,
                                      double tempMoyenne, double pluieCumulee,
                                      int moisActuel, Optional<Campagne> derniere) {
        int score = 0;
        List<String> raisons = new ArrayList<>();

        // Température (30 pts)
        if (tempMoyenne >= culture.getTemperatureMin() && tempMoyenne <= culture.getTemperatureMax()) {
            score += 30;
            raisons.add("Température de la parcelle adaptée");
        } else {
            raisons.add("Température hors de la plage idéale pour cette culture");
        }

        // Pluviométrie (30 pts)
        if (pluieCumulee >= culture.getPluviometrieMinMm()) {
            score += pluieCumulee <= culture.getPluviometrieMaxMm() ? 30 : 20;
            raisons.add("Pluviométrie suffisante récemment");
        } else {
            raisons.add("Pluviométrie récente insuffisante pour cette culture");
        }

        // Sol (20 pts) — ignoré si inconnu
        if ("TOUS".equals(culture.getTypeSolPrefere())
                || culture.getTypeSolPrefere().equals(parcelle.getTypeSol())) {
            score += 20;
        } else if (!"INCONNU".equals(parcelle.getTypeSol())) {
            raisons.add("Type de sol non idéal pour cette culture");
        }

        // Saison de semis (20 pts)
        if (dansLaFenetre(moisActuel, culture.getSaisonSemisDebut(), culture.getSaisonSemisFin())) {
            score += 20;
            raisons.add("C'est la bonne période pour semer cette culture");
        } else {
            raisons.add("Hors de la période de semis recommandée");
        }

        // Bonus rotation
        if (derniere.isPresent() && derniere.get().getCulture().getId().equals(culture.getId())) {
            score -= 10;
            raisons.add("Même culture que la dernière campagne : la rotation est conseillée");
        }

        return new SuggestionCulture(culture.getName(), Math.max(score, 0), raisons);
    }

    private boolean dansLaFenetre(int mois, int debut, int fin) {
        return debut <= fin ? (mois >= debut && mois <= fin)
                : (mois >= debut || mois <= fin); // fenêtre à cheval sur l'année
    }
}
