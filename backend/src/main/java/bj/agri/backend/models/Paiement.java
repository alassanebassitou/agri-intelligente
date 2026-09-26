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
public class Payment extends Auditable {
    private BigDecimal amount;
    private String prestataire;
    private String reference;

    @Enumerated(EnumType.STRING)
    private StatusPayment status;

    @OneToOne
    @JoinColumn(name = "commande_id")
    private Commande commande;
}
