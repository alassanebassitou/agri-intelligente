package bj.agri.backend;

import bj.agri.backend.commun.audite.Auditable;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table
@NoArgsConstructor
@Getter
@Setter
public class Culture extends Auditable {
    private String name;
    private Integer dureCycleJour;
}
