package bj.agri.backend.services.monitoring.regles;

import bj.agri.backend.enums.NiveauAlerte;
import bj.agri.backend.enums.TypeAlerte;
import bj.agri.backend.models.Alerte;
import bj.agri.backend.models.Campagne;
import bj.agri.backend.models.ObservationClimat;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class RegleFortePluie implements RegleAlerte {

    private static final double SEUIL_MM_3_JOURS = 80.0;

    @Override
    public Optional<Alerte> evaluer(Campagne campagne, List<ObservationClimat> historique) {
        double cumul = historique.stream()
                .limit(3)
                .mapToDouble(ObservationClimat::getPluieMm)
                .sum();

        if (cumul >= SEUIL_MM_3_JOURS) {
            Alerte a = new Alerte();
            a.setParcelle(campagne.getParcelle());
            a.setCampagne(campagne);
            a.setType(TypeAlerte.CLIMAT);
            a.setNiveau(cumul >= 120 ? NiveauAlerte.RED : NiveauAlerte.ORANGE);
            a.setMessage("Fortes pluies cumulées (%.0f mm sur 3 jours) : évitez l'épandage d'engrais et vérifiez le drainage."
                    .formatted(cumul));
            return Optional.of(a);
        }
        return Optional.empty();
    }
}
