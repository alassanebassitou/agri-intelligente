package bj.agri.backend.dto.response;

import bj.agri.backend.models.Campagne;

import java.time.LocalDate;

public record CampagneResponse(
        Long id, Long parcelleId, String cultureNom,
        LocalDate dateSemis, LocalDate dateRecolteEstimee,
        String statut, Double quantiteRecoltee
) {
    public static CampagneResponse from(Campagne c) {
        return new CampagneResponse(c.getId(), c.getParcelle().getId(), c.getCulture().getName(),
                c.getDateSemis(), c.getDateRecolteEstimee(), c.getStatus(), c.getQuantityRecoltee());
    }
}
