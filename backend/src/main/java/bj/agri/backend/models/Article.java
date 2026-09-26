package bj.agri.backend.models;

import bj.agri.backend.enums.ArticleCategorie;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
public class Article extends Auditable {

    private String titre;

    @Column(columnDefinition = "TEXT")
    private String contenu;

    @Enumerated(EnumType.STRING)
    private ArticleCategorie categorie;

    @ManyToOne
    @JoinColumn(name = "culture_id")
    private Culture culture;

    private String langue;
    private String urlAudio;

    @ManyToOne
    @JoinColumn(name = "auteur_id")
    private Users auteur;
}
