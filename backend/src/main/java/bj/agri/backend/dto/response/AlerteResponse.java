package bj.agri.backend.dto.response;

import bj.agri.backend.enums.NiveauAlerte;
import bj.agri.backend.enums.TypeAlerte;
import bj.agri.backend.models.Alerte;

import java.time.LocalDateTime;

public record AlerteResponse(
        Long id, Long parcelleId, Long campagneId, TypeAlerte type, NiveauAlerte niveau,
        String message, boolean lue, LocalDateTime dateCreation
) {
    public static AlerteResponse from(Alerte a) {
        return new AlerteResponse(a.getId(), a.getParcelle().getId(),
                a.getCampagne() != null ? a.getCampagne().getId() : null,
                a.getType(), a.getNiveau(), a.getMessage(), a.getIsRead(), a.getDateCreation());
    }
}
