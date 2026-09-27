# 🌾 AgriSignal — Plateforme d'agriculture intelligente

Plateforme de soutien à la production agricole, pensée pour accompagner
l'exploitant béninois dans la gestion de ses parcelles et de ses campagnes
de culture.

Projet réalisé dans le cadre du **Challenge agriculture intelligente** (3 jours).

> ⚠️ **Note sur l'avancement** : par manque de temps, seule une partie du
> périmètre imaginé a pu être implémentée et testée de bout en bout. Ce
> README distingue clairement ce qui est **fonctionnel aujourd'hui** de ce
> qui a été **conçu mais non développé**, pour donner une image honnête de
> l'état du projet.

---

## 🔗 Accès à la plateforme

| | Lien |
|---|---|
| **Application déployée** | https://agri-intelligente-2k9d.vercel.app |
| **API backend** | https://agri-intelligente.onrender.com |
| **Dépôt GitHub** | https://github.com/bassitou63/agri-intelligente |

> ⚠️ Le backend est hébergé sur une offre gratuite (Render) qui se met en veille
> après une période d'inactivité. **Le premier chargement peut prendre 30 à 60
> secondes** — patientez avant de conclure à une panne.

### Comptes de démonstration

| Rôle | NPI | Mot de passe |
|---|---|---|
| Agriculteur | `19850312400123` | `Demo1234!` |
| Admin | `1234567890` | `admin@123` |

---

## 🧭 Le problème que nous adressons

Au Bénin, l'agriculteur gère souvent ses parcelles et ses cycles de culture
« de tête » ou sur papier : difficile de garder une trace claire de ce qui est
planté, où, et depuis quand. AgriSignal pose une première brique simple pour
digitaliser ce suivi de base, avant d'y ajouter des couches de décision plus
riches (alertes, marché, etc. — voir la section Conception ci-dessous).

## 👤 Notre persona

**Alassane, 34 ans, agriculteur à Glazoué.** Il cultive du maïs et du manioc
sur deux petites parcelles. Il possède un smartphone Android d'entrée de
gamme et une connexion internet instable — l'interface doit rester simple et
légère.

---

## ✅ Fonctionnalités implémentées et fonctionnelles

- **Inscription et connexion** — authentification par numéro NPI et mot de
  passe, avec token JWT.
- **Renseignement des parcelles** — un agriculteur peut créer une ou
  plusieurs parcelles (nom, superficie, commune, géolocalisation, type de
  sol).
- **Liste des parcelles d'un producteur** — chaque agriculteur consulte
  uniquement ses propres parcelles (contrôle d'accès par utilisateur).
- **Déclaration de semis (campagne)** — pour une parcelle donnée,
  l'agriculteur déclare avoir semé une culture à une date donnée ; la date
  de récolte estimée est calculée automatiquement à partir de la durée du
  cycle de la culture.

Ce socle couvre la partie « suivi de l'exploitation » du brief : savoir qui
possède quoi, où, et ce qui y est cultivé.

---

## 🧩 Conception réalisée, développement non finalisé

Les éléments suivants ont été **modélisés, conçus et en partie codés côté
backend** durant le challenge, mais n'ont pas pu être branchés bout en bout
(frontend, tests, ou déploiement) dans le temps imparti. Nous les documentons
ici pour montrer la réflexion menée, sans prétendre qu'ils sont utilisables
dans la version déployée :

- **Monitoring climatique et alertes** — observations climatiques par
  parcelle, moteur de règles (forte pluie, risque fongique, récolte proche).
- **Suggestion de culture par parcelle** — score basé sur le climat récent,
  le type de sol, la saison de semis et la rotation des cultures.
- **Marché** — publication d'offres de vente, commandes, paiement en ligne
  (Kkiapay/FedaPay en sandbox).
- **Redevance pour l'État** — calcul automatique d'une taxe sur chaque
  paiement réussi, selon le marché (local/international).
- **Gestion de contenu** — articles de réglementation et conseils agricoles.
- **Coopérative agricole** — regroupement de plusieurs agriculteurs sous un
  responsable, avec vue agrégée des alertes.

Le schéma de données de ces modules, ainsi que les scripts de migration
Flyway correspondants, sont présents dans le dépôt (`db/migration`) à titre
de documentation de la conception, même si les écrans associés ne sont pas
accessibles depuis l'application déployée.

---

## 🏗️ Architecture

Monolithe modulaire : un seul backend Spring Boot, un seul frontend Angular,
une seule base PostgreSQL — choisi pour livrer un socle complet et
fonctionnel en 3 jours plutôt qu'une architecture distribuée sous-utilisée.

```
[Angular]  ──HTTPS/JSON──►  [Spring Boot API]  ──►  [PostgreSQL]
                              └─ Sécurité JWT (NPI + mot de passe)
```

- **Backend** : Java 21, Spring Boot, Spring Security (JWT), Spring Data JPA,
  Flyway, PostgreSQL.
- **Frontend** : Angular (standalone components).
- **Hébergement** : backend sur Render, frontend sur Vercel, base PostgreSQL
  managée (Neon).

---

## ⚙️ Lancer le projet en local

### Prérequis
- Java 21, Maven
- Node.js 20+, Angular CLI
- Docker (pour PostgreSQL local)

### Backend

```bash
cd backend
docker compose up -d          # démarre PostgreSQL local
./mvnw spring-boot:run
```

L'API est disponible sur `http://localhost:8080/api`.

### Frontend

```bash
cd frontend
npm install
ng serve
```

L'application est disponible sur `http://localhost:4200`.

### Variables d'environnement principales

| Variable | Description |
|---|---|
| `DB_URL`, `DB_USERNAME`, `DB_PASSWORD` | Connexion PostgreSQL |
| `SECRET_KEY` | Clé de signature JWT |
| `FRONT_URL`, `APP_API_URL` | Origines autorisées (CORS) |

---

## 🔭 Limites et perspectives

Nous avons choisi un **monolithe modulaire** (Spring Boot + Angular, une seule
base PostgreSQL) plutôt que des microservices, pour aller vite dans le temps
imparti. Au-delà du socle livré (parcelles, campagnes, authentification),
voici comment nous envisageons de construire et faire évoluer les modules
conçus mais non encore branchés, ainsi que les limites que ce choix
d'architecture impliquerait à leur mise en service.

### Passage à l'échelle

Un backend en un seul bloc ne permettrait pas de faire monter en charge le
futur module monitoring (météo, alertes) indépendamment du reste. À terme,
ce module serait le candidat naturel pour devenir un service à part, avec une
file de messages (RabbitMQ) pour découpler la collecte météo, le calcul des
alertes et leur envoi.

### Alertes et disponibilité

Une fois implémentées, des tâches planifiées de calcul d'alertes tournant
dans l'API elle-même resteraient fiables sur une seule instance ; avec
plusieurs instances, un verrou distribué (ShedLock) serait nécessaire pour
éviter les alertes en double. Notre hébergement de démonstration se met par
ailleurs en veille après inactivité : en production, un plan payant ou un
ordonnanceur externe garantirait une collecte météo continue.

### Règles d'alerte

Les seuils (pluie, humidité, température) envisagés seraient d'abord codés
dans le backend pour aller vite et rester fiables en démonstration. La suite
logique : les stocker en base avec une interface d'administration, pour
qu'un agronome de l'État puisse les ajuster sans redéploiement, culture par
culture et région par région.

### Hors ligne

Une PWA mettant en cache les alertes et fiches consultées permettrait de les
relire sans connexion. Elle ne permettrait pas de **créer** une offre ou une
parcelle hors ligne puis de synchroniser au retour du réseau : c'est
l'évolution prioritaire pour les zones à connectivité très instable.

### Canaux d'accès

L'agriculteur devrait ouvrir l'application pour consulter ses informations.
Beaucoup d'entre eux ne le feront pas spontanément : un canal **SMS ou
WhatsApp** pour les alertes critiques serait la prochaine étape, pour
toucher aussi les téléphones basiques.

### Langue et inclusion

L'interface est en français. Une extension naturelle : la lecture vocale des
alertes et du contenu, des enregistrements audio en fon et en yoruba, et des
icônes encore plus indépendantes du texte.

### Données et paiement

Les intégrations météo (Open-Meteo) et paiement (Kkiapay/FedaPay) sont
prévues en mode gratuit ou sandbox pour la démonstration. Une mise en
production réelle demanderait des clés de production, un suivi des quotas
d'appels, et des mécanismes de reprise en cas d'indisponibilité d'un
fournisseur externe.

### Précision agronomique

Les alertes envisagées reposeraient sur des règles météorologiques simples,
pas sur une détection visuelle des maladies. Une évolution naturelle :
permettre à l'agriculteur de soumettre une photo, analysée par un modèle de
reconnaissance d'images, en complément des règles climatiques.

### Priorités pour la suite

1. Brancher le monitoring climatique et le moteur d'alertes au frontend —
   le point du brief avec le plus d'impact perçu pour l'agriculteur.
2. Finaliser le module marché (offres, commandes, paiement).
3. Ajouter la gestion de contenu (réglementations) et la redevance État.
4. Étendre l'inclusivité : lecture audio, mode hors ligne, langues locales.

---

## 👥 Équipe

- Alassane Abdou-Bassitou — Développeur full-stack (Java Spring Boot,
  Angular, PostgreSQL)

---

## 📄 Licence

Projet réalisé dans le cadre du Challenge agriculture intelligente.
