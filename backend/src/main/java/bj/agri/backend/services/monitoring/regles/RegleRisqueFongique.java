package bj.agri.backend.services.components;

import bj.agri.backend.enums.NiveauAlerte;
import bj.agri.backend.enums.TypeAlerte;
import bj.agri.backend.models.Alerte;
import bj.agri.backend.models.Campagne;
import bj.agri.backend.models.Maladie;
import bj.agri.backend.models.ObservationClimat;
import bj.agri.backend.repositories.MaladieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class RegleRisqueFongique implements RegleAlerte{

    private final MaladieRepository maladieRepository;

    @Override
    public Optional<Alerte> evaluer(Campagne campagne, List<ObservationClimat> historique) {
        boolean conditionsFavorables = historique.stream()
                .limit(5)
                .allMatch(o -> o.getHumidity() >= 80 && o.getTemperature() >= 26);

        if (!conditionsFavorables || historique.size() < 5) {
            return Optional.empty();
        }

        List<Maladie> maladies = maladieRepository.findByCultureId(campagne.getCulture().getId());
        if (maladies.isEmpty()) {
            return Optional.empty();
        }

        Maladie maladie = maladies.get(0); // simplification : la première associée
        Alerte a = new Alerte();
        a.setParcelle(campagne.getParcelle());
        a.setCampagne(campagne);
        a.setMaladie(maladie);
        a.setType(TypeAlerte.PHYTO);
        a.setNiveau(NiveauAlerte.ORANGE);
        a.setMessage("Conditions favorables à " + maladie.getName() + " sur votre " + campagne.getCulture().getName()
                + ". Inspectez les feuilles cette semaine.");
        return Optional.of(a);
    }
}
