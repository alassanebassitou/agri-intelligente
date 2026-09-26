package bj.agri.backend.dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateParcelleRequest {
    private String name;
    private Double superficie;
    private Double latitude;
    private Double longitude;
    private String commune;
    private String typeSol;
}
