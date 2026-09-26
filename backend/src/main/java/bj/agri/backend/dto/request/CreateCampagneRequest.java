package bj.agri.backend.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

import java.time.LocalDate;

public record CreateCampagneRequest(
        @NotNull Long parcelleId,
        @NotNull String cultureName,
        @NotNull @PastOrPresent LocalDate dateSemis
) {}
