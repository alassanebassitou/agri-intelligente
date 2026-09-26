package bj.agri.backend.dto.response;

import bj.agri.backend.enums.StatusCommande;
import bj.agri.backend.models.Commande;

import java.time.LocalDate;

public record CommandeResponse(
        Long id, Long offreId, String cultureNom, Double quantite,
        Double montantTotal, StatusCommande statut, LocalDate dateCommande
) {
    public static CommandeResponse from(Commande c) {
        return new CommandeResponse(
                c.getId(), c.getOffre().getId(), c.getOffre().getCampagne().getCulture().getName(),
                c.getQuantity(), c.getQuantity() * c.getOffre().getPrix().doubleValue(),
                c.getStatus(), c.getDateCommande()
        );
    }
}
