package bj.agri.backend.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table
@NoArgsConstructor
@Getter
@Setter
public class Maladie extends Auditable {
    private String name;
    private String symptome;
    private String treatment;

    @ManyToMany(mappedBy = "maladies")
    private Set<Culture> cultures = new HashSet<>();
}
