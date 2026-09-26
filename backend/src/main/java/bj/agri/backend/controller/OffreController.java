package bj.agri.backend.controller;

import bj.agri.backend.dto.request.CreateOffreRequest;
import bj.agri.backend.dto.response.OffreResponse;
import bj.agri.backend.security.AuthenticatedUser;
import bj.agri.backend.services.OffreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/offres")
public class OffreController {

    private final OffreService offreService;

    @PostMapping("/faire-une-offre")
    @PreAuthorize("hasRole('AGRICULTEUR')")
    public ResponseEntity<OffreResponse> faireUneOffre(@RequestBody CreateOffreRequest request, AuthenticatedUser authenticatedUser) {
        return ResponseEntity.status(201)
                .body(offreService.faireUneOffre(request, authenticatedUser));
    }
}
