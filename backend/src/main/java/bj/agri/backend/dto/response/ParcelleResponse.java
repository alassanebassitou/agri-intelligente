package bj.agri.backend.dto.response;

import bj.agri.backend.models.Parcelle;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ParcelleResponse {
    private Long id;
    private String name;
    private Double superficie;
    private Double latitude;
    private Double longitude;
    private String commune;
    private String typeSol;
    private LocalDateTime createdAt;
    private String userFullname;
    private LocalDateTime updatedAt;

    public static ParcelleResponse from(Parcelle parcelle) {
        return ParcelleResponse.builder()
                .id(parcelle.getId())
                .name(parcelle.getName())
                .superficie(parcelle.getSuperficie())
                .latitude(parcelle.getLatitude())
                .longitude(parcelle.getLongitude())
                .commune(parcelle.getCommune())
                .typeSol(parcelle.getTypeSol())
                .userFullname(parcelle.getOwner().getLastname()+" "+parcelle.getOwner().getFirstname())
                .createdAt(parcelle.getDateCreation())
                .updatedAt(parcelle.getDateModification())
                .build();
    }
}
