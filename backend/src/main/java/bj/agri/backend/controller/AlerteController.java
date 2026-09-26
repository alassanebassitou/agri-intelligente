package bj.agri.backend.controller;

import bj.agri.backend.dto.response.AlerteResponse;
import bj.agri.backend.security.AuthenticatedUser;
import bj.agri.backend.services.monitoring.AlerteService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/alertes")
public class AlerteController {

    private final AlerteService alerteService;

    @GetMapping
    public Page<AlerteResponse> lister(@RequestParam(required = false) Boolean lue,
                                       Pageable pageable,
                                       @AuthenticationPrincipal AuthenticatedUser user) {
        return alerteService.lister(lue, pageable, user);
    }

    @PatchMapping("/{id}/lue")
    public AlerteResponse marquerLue(@PathVariable Long id, @AuthenticationPrincipal AuthenticatedUser user) {
        return alerteService.marquerLue(id, user);
    }
}
