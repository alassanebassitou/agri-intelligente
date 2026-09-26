package bj.agri.backend.services.components;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TacheAlertesQuotidienne {

    private final MoteurAlertes moteurAlertes;

    @Scheduled(cron = "0 0 6 * * *")  // tous les jours à 6h
    public void executer() {
        moteurAlertes.evaluerToutesLesCampagnesActives();
    }
}
