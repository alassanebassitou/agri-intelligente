package bj.agri.backend.dto.response;

import java.util.List;

public record SuggestionCultureResponse (
        Long cultureId,
        String nomCulture,
        int score,
        List<String> raisons
) {
}
