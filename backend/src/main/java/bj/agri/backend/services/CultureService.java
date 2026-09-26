package bj.agri.backend.services;


import bj.agri.backend.dto.response.CultureResponse;
import bj.agri.backend.repositories.CultureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CultureService {

    private final CultureRepository cultureRepository;

    @Transactional(readOnly = true)
    public List<CultureResponse> getAllCulture() {
        return cultureRepository.findAll().stream()
                .map(CultureResponse::from)
                .toList();
    }
}
