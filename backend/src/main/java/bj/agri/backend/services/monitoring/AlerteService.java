package bj.agri.backend.services;

import bj.agri.backend.dto.response.AlerteResponse;
import bj.agri.backend.exceptions.ResourceNotFoundException;
import bj.agri.backend.models.Alerte;
import bj.agri.backend.repositories.AlerteRepository;
import bj.agri.backend.security.AuthenticatedUser;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AlerteService {

    private final AlerteRepository alerteRepository;

    public Page<AlerteResponse> lister(Boolean lue, Pageable pageable, AuthenticatedUser authUser) {
        return alerteRepository.findForUser(authUser.userId(), lue, pageable)
                .map(AlerteResponse::from);
    }

    public AlerteResponse marquerLue(Long alerteId, AuthenticatedUser authUser) {
        Alerte alerte = alerteRepository.findByIdAndOwner(alerteId, authUser.userId())
                .orElseThrow(() -> new ResourceNotFoundException("Alerte introuvable"));
        alerte.setIsRead(true);
        return AlerteResponse.from(alerteRepository.save(alerte));
    }
}
