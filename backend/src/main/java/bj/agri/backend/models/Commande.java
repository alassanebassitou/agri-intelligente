package bj.agri.backend.models;

import bj.agri.backend.enums.StatusCommande;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table
@NoArgsConstructor
@Getter
@Setter
public class Commande extends Auditable {
    private Double quantity;

    @Enumerated(EnumType.STRING)
    private StatusCommande status;
    private LocalDate dateCommande;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "acheteur_id")
    private Users acheteur;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "offre_id")
    private Offre offre;
}
