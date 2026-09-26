package bj.agri.backend.controller;

import bj.agri.backend.dto.response.CultureResponse;
import bj.agri.backend.services.CultureService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cultures")
public class CultureController {

    private final CultureService cultureService;

    @GetMapping
    public ResponseEntity<List<CultureResponse>> getAllCulture() {
        return ResponseEntity.ok(cultureService.getAllCulture());
    }
}
