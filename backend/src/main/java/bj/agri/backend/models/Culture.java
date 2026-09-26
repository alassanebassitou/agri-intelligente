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
public class Culture extends Auditable {
    private String name;
    private Integer dureeCycleJours;
    private Integer temperatureMin;
    private Integer temperatureMax;
    private Integer pluviometrieMinMm;
    private Integer pluviometrieMaxMm;
    private String typeSolPrefere;
    private Integer saisonSemisDebut;
    private Integer saisonSemisFin;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "type_culture_code")
    private TypeCulture typeCulture;

    @ManyToMany
    @JoinTable(name = "culture_maladie", joinColumns = @JoinColumn(name = "culture_id"),
            inverseJoinColumns = @JoinColumn(name = "maladie_id"))
    private Set<Maladie> maladies = new HashSet<>();
}
