-- Référentiels
CREATE TABLE type_culture (
    code VARCHAR(80) NOT NULL UNIQUE PRIMARY KEY,
    name VARCHAR(80) NOT NULL
);

CREATE TABLE culture (
    id                BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name               VARCHAR(80) NOT NULL UNIQUE,
    duree_cycle_jours INT NOT NULL CHECK (duree_cycle_jours > 0),
    type_culture_code   VARCHAR NOT NULL REFERENCES type_culture(code)
);

CREATE TABLE maladie (
    id         BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name        VARCHAR(120) NOT NULL UNIQUE,
    symptomes  TEXT,
    traitement TEXT
);

CREATE TABLE culture_maladie (
    culture_id BIGINT NOT NULL REFERENCES culture(id) ON DELETE CASCADE,
    maladie_id BIGINT NOT NULL REFERENCES maladie(id) ON DELETE CASCADE,
    PRIMARY KEY (culture_id, maladie_id)
);

-- Utilisateurs et terrain
CREATE TABLE utilisateur (
    id            BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    nom           VARCHAR(120) NOT NULL,
    telephone     VARCHAR(30) NOT NULL UNIQUE,
    mot_de_passe  VARCHAR(255) NOT NULL,
    role          VARCHAR(20) NOT NULL
                  CHECK (role IN ('ROLE_AGRICULTEUR','ROLE_ACHETEUR','ROLE_AGENT_ETAT','ROLE_ADMIN')),
    langue        VARCHAR(10) NOT NULL DEFAULT 'fr'
);

CREATE TABLE parcelle (
    id          BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    nom         VARCHAR(120) NOT NULL,
    superficie  NUMERIC(10,2) CHECK (superficie > 0),
    commune     VARCHAR(120) NOT NULL,
    latitude    DOUBLE PRECISION NOT NULL CHECK (latitude BETWEEN -90 AND 90),
    longitude   DOUBLE PRECISION NOT NULL CHECK (longitude BETWEEN -180 AND 180),
    user_id     BIGINT NOT NULL REFERENCES utilisateur(id) ON DELETE CASCADE
);

CREATE TABLE campagne (
    id                    BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    parcelle_id           BIGINT NOT NULL REFERENCES parcelle(id) ON DELETE CASCADE,
    culture_id            BIGINT NOT NULL REFERENCES culture(id),
    date_semis            DATE NOT NULL,
    date_recolte_estimee  DATE,
    status                VARCHAR(20) NOT NULL DEFAULT 'EN_COURS'
                          CHECK (status IN ('PLANIFIEE','EN_COURS','RECOLTEE','ABANDONNEE')),
    quantite_recoltee     NUMERIC(12,2)
);

-- Monitoring
CREATE TABLE observation_climat (
    id           BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    parcelle_id  BIGINT NOT NULL REFERENCES parcelle(id) ON DELETE CASCADE,
    jour         DATE NOT NULL,
    temperature  NUMERIC(5,2),
    humidite     NUMERIC(5,2) CHECK (humidite BETWEEN 0 AND 100),
    pluie_mm     NUMERIC(6,2) CHECK (pluie_mm >= 0),
    source       VARCHAR(20) NOT NULL DEFAULT 'OPEN_METEO'
                 CHECK (source IN ('OPEN_METEO','SIMULE','SAISIE')),
    UNIQUE (parcelle_id, jour)
);

CREATE TABLE alerte (
    id             BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    parcelle_id    BIGINT NOT NULL REFERENCES parcelle(id) ON DELETE CASCADE,
    campagne_id    BIGINT REFERENCES campagne(id) ON DELETE CASCADE,
    maladie_id     BIGINT REFERENCES maladie(id),
    type           VARCHAR(20) NOT NULL
                   CHECK (type IN ('CLIMAT','PHYTO','SEMIS','RECOLTE')),
    niveau         VARCHAR(10) NOT NULL
                   CHECK (niveau IN ('VERT','ORANGE','ROUGE')),
    message        TEXT NOT NULL,
    lue            BOOLEAN NOT NULL DEFAULT FALSE,
);

-- Marché
CREATE TABLE offre (
    id           BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    campagne_id  BIGINT NOT NULL REFERENCES campagne(id) ON DELETE CASCADE,
    user_id      BIGINT NOT NULL REFERENCES utilisateur(id),
    quantite     NUMERIC(12,2) NOT NULL CHECK (quantite > 0),
    prix         NUMERIC(12,2) NOT NULL CHECK (prix >= 0),
    marche       VARCHAR(15) NOT NULL DEFAULT 'LOCAL'
                 CHECK (marche IN ('LOCAL','INTERNATIONAL')),
    status       VARCHAR(15) NOT NULL DEFAULT 'OUVERTE'
                 CHECK (status IN ('OUVERTE','VENDUE','ANNULEE'))
);

CREATE TABLE commande (
    id             BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    offre_id       BIGINT NOT NULL REFERENCES offre(id),
    acheteur_id    BIGINT NOT NULL REFERENCES utilisateur(id),
    quantite       NUMERIC(12,2) NOT NULL CHECK (quantite > 0),
    status         VARCHAR(15) NOT NULL DEFAULT 'EN_ATTENTE'
                   CHECK (status IN ('EN_ATTENTE','PAYEE','ANNULEE')),
    date_commande  DATE NOT NULL DEFAULT CURRENT_DATE
);

CREATE TABLE paiement (
    id           BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    commande_id  BIGINT NOT NULL UNIQUE REFERENCES commande(id),  -- payée une seule fois
    montant      NUMERIC(12,2) NOT NULL CHECK (montant > 0),
    prestataire  VARCHAR(30) NOT NULL,
    reference    VARCHAR(100),
    status       VARCHAR(15) NOT NULL DEFAULT 'EN_ATTENTE'
                 CHECK (status IN ('EN_ATTENTE','REUSSI','ECHEC')),
    montant_redevance NUMERIC(12,2)
);

CREATE TABLE article (
    id         BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    titre      VARCHAR(200) NOT NULL,
    contenu    TEXT NOT NULL,
    categorie  VARCHAR(30) NOT NULL
               CHECK (categorie IN ('REGLEMENTATION','CONSEIL','MALADIE')),
    culture_id BIGINT REFERENCES culture(id),
    langue     VARCHAR(10) NOT NULL DEFAULT 'fr',
    url_audio  VARCHAR(255),
    auteur_id  BIGINT REFERENCES utilisateur(id),
    date_creation TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE TABLE redevance (
    id     BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    type   VARCHAR(30) NOT NULL,
    taux   NUMERIC(5,2) NOT NULL CHECK (taux >= 0 AND taux <= 100),
    marche VARCHAR(15) NOT NULL CHECK (marche IN ('LOCAL','INTERNATIONAL')),
    UNIQUE (marche)
);


-- Index utiles aux requêtes courantes
CREATE INDEX idx_parcelle_user       ON parcelle(user_id);
CREATE INDEX idx_campagne_parcelle   ON campagne(parcelle_id);
CREATE INDEX idx_obs_parcelle_jour   ON observation_climat(parcelle_id, jour DESC);
CREATE INDEX idx_alerte_parcelle     ON alerte(parcelle_id, lue, date_creation DESC);
CREATE INDEX idx_offre_statut        ON offre(statut);
CREATE INDEX idx_commande_acheteur   ON commande(acheteur_id);
CREATE INDEX idx_article_categorie ON article(categorie);