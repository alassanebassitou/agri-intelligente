package bj.agri.backend.dto.response;

import bj.agri.backend.models.Redevance;

public record RedevanceResponse(Long id, String type, Double taux, String marche) {
    public static RedevanceResponse from(Redevance r) {
        return new RedevanceResponse(r.getId(), r.getType(), r.getTaux(), r.getMarche());
    }
}

