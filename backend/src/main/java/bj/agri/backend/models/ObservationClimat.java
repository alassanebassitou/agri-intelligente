package bj.agri.backend.observation;

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
public class Observation {
    private LocalDate jour;;
    private String temperature;
    private String humidity;
    private String pluieMn;
    private String source;
}


