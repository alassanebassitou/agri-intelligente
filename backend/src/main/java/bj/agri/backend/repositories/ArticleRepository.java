package bj.agri.backend.repositories;

import bj.agri.backend.models.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article,Long> {

    Page<Article> findByCategorieAndLangue(String categorie, String langue, Pageable pageable);

    Page<Article> findByLangue(String langue, Pageable pageable);

    Page<Article> findByCultureId(Long cultureId, Pageable pageable);
}
