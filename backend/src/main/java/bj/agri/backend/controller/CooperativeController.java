package bj.agri.backend.controller;

import bj.agri.backend.dto.request.CreateCooperativeRequest;
import bj.agri.backend.dto.response.CooperativeResponse;
import bj.agri.backend.security.AuthenticatedUser;
import bj.agri.backend.services.CooperativeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cooperatives")
public class CooperativeController {

    private final CooperativeService cooperativeService;

    @PostMapping("/cree")
    //@PreAuthorize("")
    public ResponseEntity<CooperativeResponse> create(@RequestBody CreateCooperativeRequest request,
                                                      @AuthenticationPrincipal AuthenticatedUser authUser) {
        CooperativeResponse response = cooperativeService.creer(request, authUser);
        return ResponseEntity.status(201).body(response);
    }

    @PostMapping("/rejoindre")
    //@PreAuthorize("")
    public ResponseEntity<Void> rejoindre(@Param("codeInvitation") String codeInvitation,
                                                         @AuthenticationPrincipal AuthenticatedUser authUser) {
        cooperativeService.rejoindre(codeInvitation, authUser);
        return ResponseEntity.noContent().build();
    }
}
