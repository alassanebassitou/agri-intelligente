package bj.agri.backend.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table
@NoArgsConstructor
@Getter
@Setter
public class Parcelle extends Auditable {
    private String name;
    private Double superficie;
    private Double latitude;
    private Double longitude;
    private String commune;
    private String typeSol;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    private Users owner;
}
