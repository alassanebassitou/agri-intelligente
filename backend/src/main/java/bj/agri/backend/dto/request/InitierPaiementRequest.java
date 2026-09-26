package bj.agri.backend.repositories;

public record InitierPaiementRequest(Long commandeId,
                                     String prestataire) {
}
