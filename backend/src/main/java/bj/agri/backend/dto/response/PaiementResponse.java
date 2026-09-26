package bj.agri.backend.dto.response;

import bj.agri.backend.enums.StatusPayment;
import bj.agri.backend.models.Paiement;

import java.math.BigDecimal;

public record PaiementResponse(
        Long id, Long commandeId, BigDecimal montant,
        String prestataire, String reference,
        StatusPayment statut
) {
    public static PaiementResponse from(Paiement p) {
        return new PaiementResponse(p.getId(), p.getCommande().getId(), p.getAmount(),
                p.getPrestataire(), p.getReference(), p.getStatus());
    }
}
