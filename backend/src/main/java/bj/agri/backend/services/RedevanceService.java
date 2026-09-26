package bj.agri.backend.services;

import bj.agri.backend.dto.response.RecetteResponse;
import bj.agri.backend.dto.response.RedevanceResponse;
import bj.agri.backend.dto.response.ResultatRedevance;
import bj.agri.backend.models.Redevance;
import bj.agri.backend.repositories.PaiementRepository;
import bj.agri.backend.repositories.RedevanceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RedevanceService {

    private final RedevanceRepository redevanceRepository;
    private final PaiementRepository paiementRepository;

    public List<RedevanceResponse> lister() {
        return redevanceRepository.findAll().stream()
                .map(RedevanceResponse::from)
                .toList();
    }

    // Appelé par PaiementService au moment où un paiement passe à REUSSI
    public ResultatRedevance calculerRedevance(String marche, double montant) {
        Redevance r = redevanceRepository.findByMarche(marche)
                .orElseThrow(() -> new IllegalStateException("Aucun taux de redevance défini pour ce marché"));
        return new ResultatRedevance(montant * r.getTaux() / 100, r);
    }

    public RecetteResponse recettesPourPeriode(String periode) {
        double total = paiementRepository.totalRecettesPourPeriode(periode);
        return new RecetteResponse(periode, total);
    }
}
