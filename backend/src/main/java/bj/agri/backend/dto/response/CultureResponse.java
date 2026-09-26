package bj.agri.backend.dto.response;

import bj.agri.backend.models.Culture;

public record CultureResponse(Long cultureId,
                              String cultureName) {

    public static CultureResponse from(Culture culture) {
        return new CultureResponse(culture.getId(), culture.getName());
    }
}
