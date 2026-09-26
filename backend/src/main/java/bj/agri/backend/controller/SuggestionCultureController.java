package bj.agri.backend.controller;

import bj.agri.backend.dto.response.SuggestionCulture;
import bj.agri.backend.services.SuggestionCultureService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/parcelles/{parcelleId}/suggestions-culture")
@RequiredArgsConstructor
public class SuggestionCultureController {

    private final SuggestionCultureService service;

    @GetMapping
    public ResponseEntity<List<SuggestionCulture>> suggerer(@PathVariable Long parcelleId) {
        return ResponseEntity.ok(service.suggererPour(parcelleId));
    }
}
