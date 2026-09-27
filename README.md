# agri-intelligente
Plateforme de soutient a la production agricole
## Limites et perspectives

Nous avons choisi un **monolithe modulaire** (Spring Boot + Angular, une seule
base PostgreSQL) plutôt que des microservices, pour livrer un parcours complet
et fonctionnel en 3 jours. Ce choix a des limites assumées, que nous détaillons
ici avec la trajectoire que nous suivrions pour les lever.

### Passage à l'échelle

Le backend se déploie et évolue comme un seul bloc : impossible aujourd'hui de
faire monter en charge le module monitoring (météo, alertes) indépendamment du
reste. À l'usage, le module `monitoring` est le candidat naturel pour devenir
un service à part, avec une file de messages (RabbitMQ) pour découpler la
collecte météo, le calcul des alertes et leur envoi.

### Alertes et disponibilité

Les tâches planifiées tournent dans l'API elle-même : sur une seule instance,
c'est fiable ; avec plusieurs instances, un verrou distribué (ShedLock) serait
nécessaire pour éviter les alertes en double. De même, notre hébergement de
démonstration peut se mettre en veille après inactivité : en production, un
plan payant ou un ordonnanceur externe garantirait une collecte météo continue.

### Règles d'alerte

Les seuils (pluie, humidité, température) sont codés dans le backend pour
aller vite et rester fiables pendant la démo. La suite logique : les stocker
en base avec une interface d'administration, pour qu'un agronome de l'État
puisse les ajuster sans redéploiement, culture par culture et région par
région.

### Hors ligne

La PWA met en cache les alertes et fiches déjà consultées, ce qui permet de
les relire sans connexion. Elle ne permet pas encore de **créer** une offre ou
une parcelle hors ligne puis de synchroniser au retour du réseau : c'est
l'évolution prioritaire pour les zones à connectivité très instable.

### Canaux d'accès

Aujourd'hui, l'agriculteur doit ouvrir l'application. Beaucoup d'entre eux ne
le feront pas spontanément : un canal **SMS ou WhatsApp** pour les alertes
critiques est la prochaine étape, pour toucher aussi les téléphones basiques.

### Langue et inclusion

L'interface est en français, avec lecture vocale des alertes. Une extension
naturelle : des enregistrements audio en fon et en yoruba, et des icônes
encore plus indépendantes du texte.

### Données et paiement

Les intégrations météo (Open-Meteo) et paiement (Kkiapay/FedaPay) sont
utilisées en mode gratuit ou sandbox pour la démonstration. Une mise en
production réelle demanderait des clés de production, un suivi des quotas
d'appels, et des mécanismes de reprise en cas d'indisponibilité d'un
fournisseur externe.

### Précision agronomique

Les alertes reposent sur des règles météorologiques simples, pas encore sur
une détection visuelle des maladies. Une évolution naturelle : permettre à
l'agriculteur de soumettre une photo, analysée par un modèle de reconnaissance
d'images, en complément des règles climatiques.
