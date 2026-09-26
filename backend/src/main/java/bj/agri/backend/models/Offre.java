package bj.agri.backend.models;

import bj.agri.backend.enums.StatusOffer;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table
@NoArgsConstructor
@Getter
@Setter
public class Offre extends Auditable {
    private String marche;
    private Double quantity;
    private BigDecimal prix;

    @Enumerated(EnumType.STRING)
    private StatusOffer status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Users users;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "campagne_id")
    private Campagne campagne;

}
