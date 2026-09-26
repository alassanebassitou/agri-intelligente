package bj.agri.backend.dto.response;

import bj.agri.backend.enums.StatusOffer;
import bj.agri.backend.models.Offre;

import java.math.BigDecimal;

public record OffreResponse(
        Long id, Long campagneId, String cultureNom, String communeVendeur,
        Double quantite, BigDecimal prix, String marche, StatusOffer statut, String vendeurNom
) {
    public static OffreResponse from(Offre o) {
        return new OffreResponse(
                o.getId(), o.getCampagne().getId(), o.getCampagne().getCulture().getName(),
                o.getCampagne().getParcelle().getCommune(),
                o.getQuantity(), o.getPrix(), o.getMarche(), o.getStatus(),
                o.getUsers().getLastname() +" "+ o.getUsers().getFirstname()
        );
    }
}
