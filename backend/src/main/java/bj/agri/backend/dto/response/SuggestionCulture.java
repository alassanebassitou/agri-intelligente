package bj.agri.backend.dto.response;

import java.util.List;

public record SuggestionCulture(String nomCulture,
                                int score,
                                List<String> raisons) {
}
