package bj.agri.backend.services;

import bj.agri.backend.dto.request.CreateArticleRequest;
import bj.agri.backend.dto.response.ArticleResponse;
import bj.agri.backend.exceptions.ResourceNotFoundException;
import bj.agri.backend.models.Article;
import bj.agri.backend.models.Culture;
import bj.agri.backend.models.Users;
import bj.agri.backend.repositories.ArticleRepository;
import bj.agri.backend.repositories.CultureRepository;
import bj.agri.backend.repositories.UsersRepository;
import bj.agri.backend.security.AuthenticatedUser;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final CultureRepository cultureRepository;
    private final UsersRepository usersRepository;

    public ArticleResponse creer(CreateArticleRequest req, AuthenticatedUser authUser) {
        Users auteur = usersRepository.findById(authUser.userId())
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur introuvable"));

        Article article = new Article();
        article.setTitre(req.titre());
        article.setContenu(req.contenu());
        article.setCategorie(req.categorie());
        article.setLangue(req.langue());
        article.setUrlAudio(req.urlAudio());
        article.setAuteur(auteur);
        article.setDateCreation(LocalDateTime.now());

        if (req.cultureId() != null) {
            Culture culture = cultureRepository.findById(req.cultureId())
                    .orElseThrow(() -> new ResourceNotFoundException("Culture introuvable"));
            article.setCulture(culture);
        }

        return ArticleResponse.from(articleRepository.save(article));
    }

    public Page<ArticleResponse> lister(String categorie, String langue, Pageable pageable) {
        Page<Article> articles;
        if (categorie != null) {
            articles = articleRepository.findByCategorieAndLangue(categorie, langue, pageable);
        } else {
            articles = articleRepository.findByLangue(langue, pageable);
        }
        return articles.map(ArticleResponse::from);
    }

    public ArticleResponse detail(Long id) {
        return articleRepository.findById(id)
                .map(ArticleResponse::from)
                .orElseThrow(() -> new ResourceNotFoundException("Article introuvable"));
    }
}
