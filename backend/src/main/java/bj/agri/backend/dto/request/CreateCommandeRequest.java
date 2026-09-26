package bj.agri.backend.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CreateCommandeRequest (
        @NotNull Long offreId,
        @NotNull @Positive Double quantite
) {}

