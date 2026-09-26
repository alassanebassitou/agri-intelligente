package bj.agri.backend.campagne;

import bj.agri.backend.commun.audite.Auditable;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
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
    private LocalDate dateSemi;
    private LocalDate dateRecolteEstime;
    private String status;
    private Long quantityRecolte;
}
