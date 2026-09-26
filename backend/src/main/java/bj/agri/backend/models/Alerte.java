package bj.agri.backend.models;

import bj.agri.backend.enums.NiveauAlerte;
import bj.agri.backend.enums.TypeAlerte;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table
@NoArgsConstructor
@Getter
@Setter
public class Alerte extends Auditable {

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TypeAlerte type;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private NiveauAlerte niveau;

    @Column(nullable = false)
    private String message;
    private Boolean isRead;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parcelle_id")
    private Parcelle parcelle;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "maladie_id")
    private Maladie maladie;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "campagne_id")
    private Campagne campagne;
}
