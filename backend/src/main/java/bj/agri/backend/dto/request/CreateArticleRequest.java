package bj.agri.backend.dto.request;

import bj.agri.backend.enums.ArticleCategorie;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateArticleRequest(
        @NotBlank String titre,
        @NotBlank String contenu,
        @NotNull ArticleCategorie categorie,
        Long cultureId,
        @NotBlank String langue,
        String urlAudio
) {}
