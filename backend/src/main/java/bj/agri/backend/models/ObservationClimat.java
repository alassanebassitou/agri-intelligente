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
public class ObservationClimat extends Auditable {
    private LocalDate jour;
    private Double temperature;
    private Double humidity;
    private Double pluieMm;
    private String source;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parcelle_id")
    private Parcelle parcelle;
}


