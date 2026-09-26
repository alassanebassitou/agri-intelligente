package bj.agri.backend.controller;

import bj.agri.backend.dto.request.ClotureCampagneRequest;
import bj.agri.backend.dto.request.CreateCampagneRequest;
import bj.agri.backend.dto.response.CampagneResponse;
import bj.agri.backend.security.AuthenticatedUser;
import bj.agri.backend.services.CampagneService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/campagnes")
public class CampagneController {

    private final CampagneService campagneService;

    @PostMapping
    public ResponseEntity<CampagneResponse> creer(@Valid @RequestBody CreateCampagneRequest req,
                                                  @AuthenticationPrincipal AuthenticatedUser user) {
        CampagneResponse res = campagneService.creer(req, user);
        return ResponseEntity.created(URI.create("/api/campagnes/" + res.id())).body(res);
    }

    @GetMapping
    public List<CampagneResponse> lister(@RequestParam Long parcelleId,
                                         @AuthenticationPrincipal AuthenticatedUser user) {
        return campagneService.listerPourParcelle(parcelleId, user);
    }

    @PatchMapping("/{id}/statut")
    public CampagneResponse cloturer(@PathVariable Long id, @RequestBody ClotureCampagneRequest req,
                                     @AuthenticationPrincipal AuthenticatedUser user) {
        return campagneService.cloturer(id, req, user);
    }
}
