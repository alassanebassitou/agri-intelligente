package bj.agri.backend.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
public class Redevance extends Auditable {
    private String type;
    private Double taux;
    private String marche;
}
