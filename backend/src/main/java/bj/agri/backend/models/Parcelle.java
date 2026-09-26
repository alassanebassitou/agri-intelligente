package bj.agri.backend.parcelles;

import bj.agri.backend.commun.audite.Auditable;
import bj.agri.backend.user.Authorities;
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
public class Parcelle extends Auditable {
    private String name;
    private String superficie;
    private String password;
    private Authorities role;
    private String phone;
}
