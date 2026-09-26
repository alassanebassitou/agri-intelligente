package bj.agri.backend.dto.response;

import bj.agri.backend.models.ObservationClimat;

import java.time.LocalDate;

public record ObservationClimatResponse(
        LocalDate jour, Double temperature, Double humidite, Double pluieMm, String source
) {
    public static ObservationClimatResponse from(ObservationClimat o) {
        return new ObservationClimatResponse(o.getJour(), o.getTemperature(),
                o.getHumidity(), o.getPluieMm(), o.getSource());
    }
}
