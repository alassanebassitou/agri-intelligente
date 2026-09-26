package bj.agri.backend.models;

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
public class Campagne extends Auditable {
    private LocalDate dateSemis;
    private LocalDate dateRecolteEstimee;
    private String status;
    private Double quantityRecoltee;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parcelle_id")
    private Parcelle parcelle;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "culture_id")
    private Culture culture;
}
