package bj.agri.backend.services.components;

import bj.agri.backend.enums.NiveauAlerte;
import bj.agri.backend.enums.TypeAlerte;
import bj.agri.backend.models.Alerte;
import bj.agri.backend.models.Campagne;
import bj.agri.backend.models.ObservationClimat;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Component
public class RegleRecolteProche implements RegleAlerte{

    @Override
    public Optional<Alerte> evaluer(Campagne campagne, List<ObservationClimat> historique) {
        if (campagne.getDateRecolteEstimee() == null) return Optional.empty();

        long joursRestants = ChronoUnit.DAYS.between(LocalDate.now(), campagne.getDateRecolteEstimee());

        if (joursRestants == 14 || joursRestants == 3) {
            Alerte a = new Alerte();
            a.setParcelle(campagne.getParcelle());
            a.setCampagne(campagne);
            a.setType(TypeAlerte.RECOLTE);
            a.setNiveau(NiveauAlerte.GREEN);
            a.setMessage("Votre " + campagne.getCulture().getName() + " sera prêt dans " + joursRestants + " jours.");
            return Optional.of(a);
        }
        return Optional.empty();
    }
}
