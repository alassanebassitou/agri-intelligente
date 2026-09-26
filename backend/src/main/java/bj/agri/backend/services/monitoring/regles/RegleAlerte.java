package bj.agri.backend.services.monitoring.regles;

import bj.agri.backend.models.Alerte;
import bj.agri.backend.models.Campagne;
import bj.agri.backend.models.ObservationClimat;

import java.util.List;
import java.util.Optional;

public interface RegleAlerte {
    Optional<Alerte> evaluer(Campagne campagne, List<ObservationClimat> historique);
}
