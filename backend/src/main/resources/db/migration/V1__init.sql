-- 1. Table TypeCulture (Pas de dépendance)
CREATE TABLE type_culture (
    code VARCHAR(255) PRIMARY KEY,
    name VARCHAR(255)
);

-- 2. Table Cooperative (Sans la clé étrangère vers Users pour l'instant pour éviter les conflits circulaires)
CREATE TABLE cooperative (
    id BIGSERIAL PRIMARY KEY,
    date_creation TIMESTAMP NOT NULL,
    date_modification TIMESTAMP,
    cree_par BIGINT,
    modifie_par BIGINT,
    name VARCHAR(255),
    commune VARCHAR(255),
    code_invitation VARCHAR(255),
    responsable_id BIGINT
);

-- 3. Table Users (Avec lien vers Cooperative)
CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    date_creation TIMESTAMP NOT NULL,
    date_modification TIMESTAMP,
    cree_par BIGINT,
    modifie_par BIGINT,
    lastname VARCHAR(255),
    firstname VARCHAR(255),
    role_cooperative VARCHAR(50),
    npi VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(50),
    phone VARCHAR(50),
    langue VARCHAR(50),
    cooperative_id BIGINT,
    type_user VARCHAR(50),
    CONSTRAINT fk_users_cooperative FOREIGN KEY (cooperative_id) REFERENCES cooperative(id)
);

-- Ajout de la contrainte FK pour responsable_id dans cooperative vers users
ALTER TABLE cooperative
    ADD CONSTRAINT fk_cooperative_responsable FOREIGN KEY (responsable_id) REFERENCES users(id);

-- 4. Table Culture (Dépend de TypeCulture)[cite: 7]
CREATE TABLE culture (
    id BIGSERIAL PRIMARY KEY,
    date_creation TIMESTAMP NOT NULL,
    date_modification TIMESTAMP,
    cree_par BIGINT,
    modifie_par BIGINT,
    name VARCHAR(255),
    duree_cycle_jours INT,
    temperature_min INT,
    temperature_max INT,
    pluviometrie_min_mm INT,
    pluviometrie_max_mm INT,
    type_sol_prefere VARCHAR(255),
    saison_semis_debut INT,
    saison_semis_fin INT,
    type_culture_code VARCHAR(255),
    CONSTRAINT fk_culture_type_culture FOREIGN KEY (type_culture_code) REFERENCES type_culture(code)
);

-- 5. Table Maladie[cite: 8]
CREATE TABLE maladie (
    id BIGSERIAL PRIMARY KEY,
    date_creation TIMESTAMP NOT NULL,
    date_modification TIMESTAMP,
    cree_par BIGINT,
    modifie_par BIGINT,
    name VARCHAR(255),
    symptome TEXT,
    treatment TEXT
);

-- 6. Table de jointure ManyToMany culture_maladie[cite: 7, 8]
CREATE TABLE culture_maladie (
    culture_id BIGINT NOT NULL,
    maladie_id BIGINT NOT NULL,
    PRIMARY KEY (culture_id, maladie_id),
    CONSTRAINT fk_culture_maladie_culture FOREIGN KEY (culture_id) REFERENCES culture(id),
    CONSTRAINT fk_culture_maladie_maladie FOREIGN KEY (maladie_id) REFERENCES maladie(id)
);

-- 7. Table Parcelle (Dépend de Users)
CREATE TABLE parcelle (
    id BIGSERIAL PRIMARY KEY,
    date_creation TIMESTAMP NOT NULL,
    date_modification TIMESTAMP,
    cree_par BIGINT,
    modifie_par BIGINT,
    name VARCHAR(255),
    superficie DOUBLE PRECISION,
    latitude DOUBLE PRECISION,
    longitude DOUBLE PRECISION,
    commune VARCHAR(255),
    type_sol VARCHAR(255),
    owner_id BIGINT,
    CONSTRAINT fk_parcelle_user FOREIGN KEY (owner_id) REFERENCES users(id)
);

-- 8. Table Campagne (Dépend de Parcelle et Culture)[cite: 4]
CREATE TABLE campagne (
    id BIGSERIAL PRIMARY KEY,
    date_creation TIMESTAMP NOT NULL,
    date_modification TIMESTAMP,
    cree_par BIGINT,
    modifie_par BIGINT,
    date_semis DATE,
    date_recolte_estimee DATE,
    status VARCHAR(50),
    quantity_recoltee DOUBLE PRECISION,
    parcelle_id BIGINT,
    culture_id BIGINT,
    CONSTRAINT fk_campagne_parcelle FOREIGN KEY (parcelle_id) REFERENCES parcelle(id),
    CONSTRAINT fk_campagne_culture FOREIGN KEY (culture_id) REFERENCES culture(id)
);

-- 9. Table Offre (Dépend de Users et Campagne)[cite: 10]
CREATE TABLE offre (
    id BIGSERIAL PRIMARY KEY,
    date_creation TIMESTAMP NOT NULL,
    date_modification TIMESTAMP,
    cree_par BIGINT,
    modifie_par BIGINT,
    marche VARCHAR(255),
    quantity DOUBLE PRECISION,
    prix NUMERIC(19, 2),
    status VARCHAR(50),
    user_id BIGINT,
    campagne_id BIGINT,
    CONSTRAINT fk_offre_user FOREIGN KEY (user_id) REFERENCES users(id),
    CONSTRAINT fk_offre_campagne FOREIGN KEY (campagne_id) REFERENCES campagne(id)
);

-- 10. Table Redevance
CREATE TABLE redevance (
    id BIGSERIAL PRIMARY KEY,
    date_creation TIMESTAMP NOT NULL,
    date_modification TIMESTAMP,
    cree_par BIGINT,
    modifie_par BIGINT,
    type VARCHAR(255),
    taux DOUBLE PRECISION,
    marche VARCHAR(255)
);

-- 11. Table Commande (Dépend de Users et Offre)[cite: 5]
CREATE TABLE commande (
    id BIGSERIAL PRIMARY KEY,
    date_creation TIMESTAMP NOT NULL,
    date_modification TIMESTAMP,
    cree_par BIGINT,
    modifie_par BIGINT,
    quantity DOUBLE PRECISION,
    status VARCHAR(50),
    date_commande DATE,
    acheteur_id BIGINT,
    offre_id BIGINT,
    CONSTRAINT fk_commande_acheteur FOREIGN KEY (acheteur_id) REFERENCES users(id),
    CONSTRAINT fk_commande_offre FOREIGN KEY (offre_id) REFERENCES offre(id)
);

-- 12. Table Paiement (Dépend de Commande et Redevance)
CREATE TABLE paiement (
    id BIGSERIAL PRIMARY KEY,
    date_creation TIMESTAMP NOT NULL,
    date_modification TIMESTAMP,
    cree_par BIGINT,
    modifie_par BIGINT,
    amount NUMERIC(19, 2),
    prestataire VARCHAR(255),
    reference VARCHAR(255),
    status VARCHAR(50),
    montant_redevance NUMERIC(19, 2),
    commande_id BIGINT,
    redevance_id BIGINT,
    CONSTRAINT fk_paiement_commande FOREIGN KEY (commande_id) REFERENCES commande(id),
    CONSTRAINT fk_paiement_redevance FOREIGN KEY (redevance_id) REFERENCES redevance(id)
);

-- 13. Table Alerte (Dépend de Parcelle, Maladie, Campagne)[cite: 1]
CREATE TABLE alerte (
    id BIGSERIAL PRIMARY KEY,
    date_creation TIMESTAMP NOT NULL,
    date_modification TIMESTAMP,
    cree_par BIGINT,
    modifie_par BIGINT,
    type VARCHAR(50) NOT NULL,
    niveau VARCHAR(50) NOT NULL,
    message VARCHAR(255) NOT NULL,
    is_read BOOLEAN,
    parcelle_id BIGINT,
    maladie_id BIGINT,
    campagne_id BIGINT,
    CONSTRAINT fk_alerte_parcelle FOREIGN KEY (parcelle_id) REFERENCES parcelle(id),
    CONSTRAINT fk_alerte_maladie FOREIGN KEY (maladie_id) REFERENCES maladie(id),
    CONSTRAINT fk_alerte_campagne FOREIGN KEY (campagne_id) REFERENCES campagne(id)
);

-- 14. Table Article (Dépend de Culture et Users)[cite: 2]
CREATE TABLE article (
    id BIGSERIAL PRIMARY KEY,
    date_creation TIMESTAMP NOT NULL,
    date_modification TIMESTAMP,
    cree_par BIGINT,
    modifie_par BIGINT,
    titre VARCHAR(255),
    contenu TEXT,
    categorie VARCHAR(50),
    culture_id BIGINT,
    langue VARCHAR(50),
    url_audio VARCHAR(255),
    auteur_id BIGINT,
    CONSTRAINT fk_article_culture FOREIGN KEY (culture_id) REFERENCES culture(id),
    CONSTRAINT fk_article_auteur FOREIGN KEY (auteur_id) REFERENCES users(id)
);

-- 15. Table ObservationClimat (Dépend de Parcelle)[cite: 9]
CREATE TABLE observation_climat (
    id BIGSERIAL PRIMARY KEY,
    date_creation TIMESTAMP NOT NULL,
    date_modification TIMESTAMP,
    cree_par BIGINT,
    modifie_par BIGINT,
    jour DATE,
    temperature DOUBLE PRECISION,
    humidity DOUBLE PRECISION,
    pluie_mm DOUBLE PRECISION,
    source VARCHAR(255),
    parcelle_id BIGINT,
    CONSTRAINT fk_observation_climat_parcelle FOREIGN KEY (parcelle_id) REFERENCES parcelle(id)
);