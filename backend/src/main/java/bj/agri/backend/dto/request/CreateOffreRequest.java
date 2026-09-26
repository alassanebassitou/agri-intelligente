package bj.agri.backend.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

public record CreateOffreRequest(
        @NotNull Long campagneId,
        @NotNull @Positive Double quantite,
        @NotNull @PositiveOrZero BigDecimal prix,
        @NotNull String marche  // LOCAL ou INTERNATIONAL
) {}
