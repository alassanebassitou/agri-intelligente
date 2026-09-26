package bj.agri.backend.dto.response;

import bj.agri.backend.models.Cooperative;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record CooperativeResponse(Long id,
                                  String name,
                                  String commune,
                                  String codeInvitation,
                                  LocalDateTime createdAt,
                                  LocalDateTime updatedAt) {

    public static CooperativeResponse from(Cooperative cooperative) {
        return CooperativeResponse.builder()
                .id(cooperative.getId())
                .name(cooperative.getName())
                .commune(cooperative.getCommune())
                .codeInvitation(cooperative.getCodeInvitation())
                .createdAt(cooperative.getDateCreation())
                .updatedAt(cooperative.getDateModification())
                .build();
    }
}
