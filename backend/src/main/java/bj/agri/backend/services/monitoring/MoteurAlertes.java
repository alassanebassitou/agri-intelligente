package bj.agri.backend.services.components;

import bj.agri.backend.models.Campagne;
import bj.agri.backend.models.ObservationClimat;
import bj.agri.backend.repositories.AlerteRepository;
import bj.agri.backend.repositories.CampagneRepository;
import bj.agri.backend.repositories.ObservationClimatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class MoteurAlertes {

    private final List<RegleAlerte> regles;              // Spring injecte toutes les @Component RegleAlerte
    private final ObservationClimatRepository observationRepository;
    private final AlerteRepository alerteRepository;
    private final CampagneRepository campagneRepository;

    public void evaluer(Campagne campagne) {
        List<ObservationClimat> historique = observationRepository
                .findByParcelleIdOrderByJourDesc(campagne.getParcelle().getId(), PageRequest.of(0, 10));

        for (RegleAlerte regle : regles) {
            regle.evaluer(campagne, historique).ifPresent(alerte -> {
                boolean dejaCreeeAujourdhui = alerteRepository.existsByCampagneIdAndTypeAndDateCreationAfter(
                        campagne.getId(), alerte.getType(), LocalDateTime.now().toLocalDate().atStartOfDay());
                if (!dejaCreeeAujourdhui) {
                    alerteRepository.save(alerte);
                }
            });
        }
    }

    // Appelée par la tâche planifiée, pour toutes les campagnes actives
    public void evaluerToutesLesCampagnesActives() {
        campagneRepository.findByStatus("EN_COURS").forEach(this::evaluer);
    }
}
