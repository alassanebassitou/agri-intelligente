package bj.agri.backend.offre;

import bj.agri.backend.commun.audite.Auditable;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
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
    private Long quantity;
    private BigDecimal prix;
    private String status;
}
