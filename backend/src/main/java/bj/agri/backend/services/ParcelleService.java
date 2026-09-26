package bj.agri.backend.services;

import bj.agri.backend.dto.request.CreateParcelleRequest;
import bj.agri.backend.dto.response.ObservationClimatResponse;
import bj.agri.backend.dto.response.ParcelleResponse;
import bj.agri.backend.dto.response.SuggestionCulture;
import bj.agri.backend.exceptions.ResourceNotFoundException;
import bj.agri.backend.models.Parcelle;
import bj.agri.backend.models.Users;
import bj.agri.backend.repositories.ObservationClimatRepository;
import bj.agri.backend.repositories.ParcelleRepository;
import bj.agri.backend.repositories.UsersRepository;
import bj.agri.backend.security.AuthenticatedUser;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ParcelleService {

    private final ParcelleRepository parcelleRepository;
    private final UsersRepository usersRepository;
    private final ObservationClimatRepository observationClimatRepository;
    private final SuggestionCultureService suggestionCultureService;

    public ParcelleResponse createdParcelle(CreateParcelleRequest request, AuthenticatedUser authUser) {
        Users user = usersRepository.findById(authUser.userId())
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur introuvable"));

        Parcelle parcelle = new Parcelle();
        parcelle.setName(request.getName());
        parcelle.setSuperficie(request.getSuperficie());
        parcelle.setCommune(request.getCommune());
        parcelle.setLatitude(request.getLatitude());
        parcelle.setTypeSol(request.getTypeSol());
        parcelle.setLongitude(request.getLongitude());
        parcelle.setDateCreation(LocalDateTime.now());
        parcelle.setOwner(user);

        return ParcelleResponse.from(parcelleRepository.save(parcelle));
    }

    public ParcelleResponse getParcelleByUserId(Long parId, AuthenticatedUser authUser) {
        return parcelleRepository.findByIdAndOwnerId(parId, authUser.userId())
                .map(ParcelleResponse::from)
                .orElseThrow(() -> new ResourceNotFoundException("Parcelle introuvable"));
    }

    public ParcelleResponse modifiedParcelle(Long parId, CreateParcelleRequest request, AuthenticatedUser authUser) {
        Parcelle parcelle = parcelleRepository.findByIdAndOwnerId(parId, authUser.userId())
                .orElseThrow(() -> new ResourceNotFoundException("Parcelle introuvable"));

        if (request.getName() != null && !request.getName().isBlank()) {
            parcelle.setName(request.getName());
        }
        if (request.getSuperficie() != null && request.getSuperficie() > 0) {
            parcelle.setSuperficie(request.getSuperficie());
        }
        if (request.getCommune() != null && !request.getCommune().isBlank()) {
            parcelle.setCommune(request.getCommune());
        }
        if (request.getLatitude() != 0) {
            parcelle.setLatitude(request.getLatitude());
        }
        if (request.getLongitude() != 0) {
            parcelle.setLongitude(request.getLongitude());
        }
        if (request.getTypeSol() != null) {
            parcelle.setTypeSol(request.getTypeSol());
        }

        return ParcelleResponse.from(parcelleRepository.save(parcelle));
    }

    public void delete(Long parId, AuthenticatedUser authUser) {
        Parcelle parcelle = parcelleRepository.findByIdAndOwnerId(parId, authUser.userId())
                .orElseThrow(() -> new ResourceNotFoundException("Parcelle introuvable"));
        parcelleRepository.delete(parcelle);
    }

    public List<ObservationClimatResponse> getClimat(Long parId, AuthenticatedUser authUser, int jours) {
        Parcelle parcelle = parcelleRepository.findByIdAndOwnerId(parId, authUser.userId())
                .orElseThrow(() -> new ResourceNotFoundException("Parcelle introuvable"));

        return observationClimatRepository
                .findByParcelleIdOrderByJourDesc(parcelle.getId(), PageRequest.of(0, jours))
                .stream()
                .map(ObservationClimatResponse::from)
                .toList();
    }

    public List<SuggestionCulture> getSug(Long parId, AuthenticatedUser authUser) {
        Parcelle parcelle = parcelleRepository.findByIdAndOwnerId(parId, authUser.userId())
                .orElseThrow(() -> new ResourceNotFoundException("Parcelle introuvable"));

        return suggestionCultureService.suggererPour(parcelle.getId());
    }

    public Page<ParcelleResponse> getAll(Pageable pageable) {
        return parcelleRepository.findAll(pageable)
                .map(ParcelleResponse::from);
    }

    public Page<ParcelleResponse> getAllForUser(Pageable pageable, AuthenticatedUser authUser) {
        return parcelleRepository.findAllByOwnerId(pageable, authUser.userId())
                .map(ParcelleResponse::from);
    }
}
