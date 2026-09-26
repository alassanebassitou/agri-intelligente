package bj.agri.backend;

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
public class Alerte extends Auditable {
    private String type;
    private String niveau;
    private String message;
    private Boolean isRead;
    private LocalDate dateCreated;
}
