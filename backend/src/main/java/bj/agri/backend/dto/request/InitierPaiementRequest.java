package bj.agri.backend.dto.request;

public record InitierPaiementRequest(Long commandeId,
                                     String prestataire) {
}
