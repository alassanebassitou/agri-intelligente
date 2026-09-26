package bj.agri.backend.user;

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
public class Users extends Auditable {
     private String name;
     private String npi;
     private String password;
     private Authorities role;
     private String phone;
}
