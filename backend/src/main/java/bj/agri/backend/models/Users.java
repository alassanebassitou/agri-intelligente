package bj.agri.backend.models;

import bj.agri.backend.enums.Authorities;
import bj.agri.backend.enums.RoleCooperative;
import bj.agri.backend.enums.TypeUser;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Users extends Auditable {

     private String lastname;
     private String firstname;

     @Enumerated(EnumType.STRING)
     @Column(name = "role_cooperative")
     private RoleCooperative roleCooperative;

     @Column(nullable = false, unique = true)
     private String npi;

     @Column(nullable = false)
     private String password;

     @Enumerated(EnumType.STRING)
     private Authorities role;

     private String phone;
     private String langue;

     @ManyToOne(fetch = FetchType.LAZY)
     @JoinColumn(name = "cooperative_id")
     private Cooperative cooperative;

     @Enumerated(EnumType.STRING)
     @Column(name = "type_user")
     private TypeUser typeUser;
}
