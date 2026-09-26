package bj.agri.backend.controller;

import bj.agri.backend.exceptions.ResourceNotFoundException;
import bj.agri.backend.models.ObservationClimat;
import bj.agri.backend.models.Parcelle;
import bj.agri.backend.repositories.CampagneRepository;
import bj.agri.backend.repositories.ObservationClimatRepository;
import bj.agri.backend.repositories.ParcelleRepository;
import bj.agri.backend.services.monitoring.MoteurAlertes;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminSimulationController {

    private final ObservationClimatRepository observationRepository;
    private final ParcelleRepository parcelleRepository;
    private final MoteurAlertes moteurAlertes;
    private final CampagneRepository campagneRepository;

    @PostMapping("/simuler-climat/{parcelleId}")
    public ResponseEntity<Void> simuler(@PathVariable Long parcelleId,
                                        @RequestParam(defaultValue = "FORTE_PLUIE") String scenario) {
        Parcelle parcelle = parcelleRepository.findById(parcelleId)
                .orElseThrow(() -> new ResourceNotFoundException("Parcelle introuvable"));

        for (int i = 0; i < 5; i++) {
            ObservationClimat o = new ObservationClimat();
            o.setParcelle(parcelle);
            o.setJour(LocalDate.now().minusDays(i));
            if ("FORTE_PLUIE".equals(scenario)) {
                o.setPluieMm(45.0);
                o.setTemperature(27.0);
                o.setHumidity(75.0);
            } else {
                o.setPluieMm(5.0);
                o.setTemperature(29.0);
                o.setHumidity(85.0);
            }
            o.setSource("SIMULE");
            o.setDateCreation(LocalDateTime.now());
            observationRepository.save(o);
        }

        campagneRepository.findByParcelleIdOrderByDateSemisDesc(parcelleId)
                .stream().findFirst()
                .ifPresent(moteurAlertes::evaluer);

        return ResponseEntity.ok().build();
    }
}
