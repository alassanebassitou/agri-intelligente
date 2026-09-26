package bj.agri.backend.controller;

import bj.agri.backend.dto.request.CreateParcelleRequest;
import bj.agri.backend.dto.response.ObservationClimatResponse;
import bj.agri.backend.dto.response.ParcelleResponse;
import bj.agri.backend.dto.response.SuggestionCulture;
import bj.agri.backend.security.AuthenticatedUser;
import bj.agri.backend.services.ParcelleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/parcelles")
public class ParcelleController {

    private final ParcelleService parcelleService;

    @PostMapping("/create")
    @PreAuthorize("hasRole('AGRICULTEUR')")
    public ResponseEntity<ParcelleResponse> created(@RequestBody CreateParcelleRequest request,
                                                    @AuthenticationPrincipal AuthenticatedUser authUser) {
        ParcelleResponse response = parcelleService.createdParcelle(request, authUser);
        return ResponseEntity.status(201).body(response);
    }

    @GetMapping("/{parId}")
    public ResponseEntity<ParcelleResponse> detail(@PathVariable("parId") Long parId,
                                                    @AuthenticationPrincipal AuthenticatedUser authUser) {
        ParcelleResponse response = parcelleService.getParcelleByUserId(parId, authUser);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<Page<ParcelleResponse>> getAll(@Param("page") int page, @Param("size") int size) {
        Pageable pageable = PageRequest.of(page,size);
        Page<ParcelleResponse> responses = parcelleService.getAll(pageable);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/by-user")
    //@PreAuthorize("hasRole('AGRICULTEUR')")
    public ResponseEntity<Page<ParcelleResponse>> mesParcelles(@Param("page") int page, @Param("size") int size,
                                                         @AuthenticationPrincipal AuthenticatedUser authUser) {
        Pageable pageable = PageRequest.of(page,size);
        Page<ParcelleResponse> responses = parcelleService.getAllForUser(pageable, authUser);
        return ResponseEntity.ok(responses);
    }

    @PutMapping("/{parId}")
    @PreAuthorize("hasRole('AGRICULTEUR')")
    public ResponseEntity<ParcelleResponse> modified(@PathVariable("parId") Long parId,
                                                     @RequestBody CreateParcelleRequest request,
                                                     @AuthenticationPrincipal AuthenticatedUser authenticatedUser) {
        ParcelleResponse response = parcelleService.modifiedParcelle(parId,request,authenticatedUser);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}/climat")
    @PreAuthorize("hasRole('AGRICULTEUR')")
    public List<ObservationClimatResponse> climat(@PathVariable Long id,
                                                  @RequestParam(defaultValue = "7") int jours,
                                                  @AuthenticationPrincipal AuthenticatedUser user) {
        return parcelleService.getClimat(id, user, jours);
    }

    @GetMapping("/{id}/suggestions-culture")
    @PreAuthorize("hasRole('AGRICULTEUR')")
    public List<SuggestionCulture> suggestions(@PathVariable Long id,
                                               @AuthenticationPrincipal AuthenticatedUser user) {
        return parcelleService.getSug(id, user);
    }
}
