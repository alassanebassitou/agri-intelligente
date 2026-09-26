package bj.agri.backend.dto.request;

import jakarta.validation.constraints.NotNull;

public record InitierPaimentRequest (
        @NotNull Long commandeId,
        @NotNull String prestataire  // KKIAPAY ou FEDAPAY
) {}
