package bj.agri.backend.maladue;

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
public class Maladie {
    private String name;
    private String symptome;
    private String treatment;
}
