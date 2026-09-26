package bj.agri.backend.models;

import bj.agri.backend.enums.StatusPayment;
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
public class Paiement extends Auditable {
    private BigDecimal amount;
    private String prestataire;
    private String reference;

    @Enumerated(EnumType.STRING)
    private StatusPayment status;
    private BigDecimal montantRedevance;


    @OneToOne
    @JoinColumn(name = "commande_id")
    private Commande commande;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "redevance_id")
    private Redevance redevance;
}
