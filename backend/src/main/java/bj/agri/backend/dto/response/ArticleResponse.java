package bj.agri.backend.dto.response;

import bj.agri.backend.enums.ArticleCategorie;
import bj.agri.backend.models.Article;

import java.time.LocalDateTime;

public record ArticleResponse(
        Long id, String titre, String contenu, ArticleCategorie categorie,
        String cultureNom, String langue, String urlAudio, LocalDateTime dateCreation
) {
    public static ArticleResponse from(Article a) {
        return new ArticleResponse(
                a.getId(), a.getTitre(), a.getContenu(), a.getCategorie(),
                a.getCulture() != null ? a.getCulture().getName() : null,
                a.getLangue(), a.getUrlAudio(), a.getDateCreation()
        );
    }
}
