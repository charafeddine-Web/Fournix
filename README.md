# 🏭 Tricol - Système de Gestion des Commandes Fournisseurs

## 📋 Vue d'ensemble

Application de gestion des commandes fournisseurs développée pour l'entreprise **Tricol**, spécialisée dans la conception et la fabrication de vêtements professionnels. Ce module permet d'assurer un suivi rigoureux des approvisionnements en matières premières et équipements.

## 🎯 Objectifs du projet

Développer une API REST complète avec Spring Boot permettant de gérer l'ensemble du cycle de vie des commandes fournisseurs, depuis leur création jusqu'à leur suivi, en appliquant les bonnes pratiques et concepts modernes du développement Java.

## ✨ Fonctionnalités principales

### 1. Gestion des Fournisseurs
- ✅ CRUD complet (Créer, Lire, Mettre à jour, Supprimer)
- 📊 Informations détaillées : société, adresse, contact, email, téléphone, ville, ICE
- 🔍 Recherche et filtrage avec pagination

### 2. Gestion des Produits
- ✅ Catalogue complet des produits
- 💰 Informations : nom, description, prix unitaire, catégorie, stock actuel
- 📦 Suivi des quantités en stock

### 3. Gestion des Commandes Fournisseurs
- 📝 Création et modification de commandes
- 🔗 Association fournisseur-produits
- 💵 Calcul automatique du montant total
- 📊 Gestion des statuts : `EN_ATTENTE`, `VALIDÉE`, `LIVRÉE`, `ANNULÉE`
- 🗂️ Consultation de l'historique complet

### 4. Gestion des Mouvements de Stock
- 📈 Suivi automatique des entrées/sorties
- 🔄 Mise à jour automatique du stock lors de la livraison
- 📅 Historique complet des mouvements
- 🏷️ Types de mouvements : `ENTREE`, `SORTIE`, `AJUSTEMENT`

### 5. Valorisation du Stock
Deux méthodes de valorisation supportées :
- **FIFO** (First In, First Out) : première entrée, première sortie
- **CUMP** (Coût Unitaire Moyen Pondéré) : coût moyen recalculé après chaque entrée

## 🏗️ Architecture

L'application suit une architecture en couches claire et maintenable :

```
src/main/java/com/tricol/
├── controller/          # Endpoints REST
├── service/            # Logique métier
├── repository/         # Accès aux données
├── dto/               # Data Transfer Objects
├── mapper/            # MapStruct mappers
├── entity/            # Entités JPA
├── exception/         # Gestion des exceptions
└── config/            # Configuration Spring
```

## 🛠️ Technologies utilisées

| Technologie | Version | Usage |
|------------|---------|-------|
| **Spring Boot** | 3.x | Framework principal |
| **Spring Data JPA** | 3.x | Accès aux données |
| **MapStruct** | 1.5.x | Mapping Entity/DTO |
| **Liquibase** | 4.x | Gestion des migrations DB |
| **Swagger/OpenAPI** | 3.x | Documentation API |
| **Jakarta Validation** | 3.x | Validation des données |
| **MySQL** | 8.x/15.x | Base de données |
| **Maven** | 3.9.x | Gestion des dépendances |

## 📊 Modèle de données

### Entités principales

```
Fournisseur
├── id (Long)
├── societe (String)
├── adresse (String)
├── contact (String)
├── email (String)
├── telephone (String)
├── ville (String)
└── ice (String)

Produit
├── id (Long)
├── nom (String)
├── description (String)
├── prixUnitaire (BigDecimal)
├── categorie (String)
└── stockActuel (Integer)

CommandeFournisseur
├── id (Long)
├── dateCommande (LocalDateTime)
├── statut (StatutCommande)
├── montantTotal (BigDecimal)
├── fournisseur (ManyToOne)
└── produits (ManyToMany)

MouvementStock
├── id (Long)
├── dateMouvement (LocalDateTime)
├── quantite (Integer)
├── typeMouvement (TypeMouvement)
├── produit (ManyToOne)
└── commandeFournisseur (ManyToOne)
```

## 🚀 Installation et lancement

### Prérequis
- Java 17 ou supérieur
- Maven 3.9+
- MySQL 8.0+ ou PostgreSQL 15+
- IDE (IntelliJ IDEA, Eclipse, VS Code)

### Configuration

1. **Cloner le repository**
```bash
git clone https://github.com/votre-username/tricol-commandes.git
cd tricol-commandes
```

2. **Configurer la base de données**

Modifier le fichier `application.properties` ou `application.yml` :

```properties
# Database Configuration
spring.datasource.url=jdbc:mysql://localhost:3306/tricol_db
spring.datasource.username=your_username
spring.datasource.password=your_password

# JPA Configuration
spring.jpa.hibernate.ddl-auto=validate
spring.jpa.show-sql=true

# Liquibase
spring.liquibase.enabled=true
spring.liquibase.change-log=classpath:db/changelog/db.changelog-master.xml

# Valorisation du stock (FIFO ou CUMP)
app.stock.valorisation-method=CUMP
```

3. **Installer les dépendances**
```bash
mvn clean install
```

4. **Lancer l'application**
```bash
mvn spring-boot:run
```

L'application sera accessible sur : `http://localhost:8080`

## 📚 Documentation API

### Swagger UI
Une fois l'application lancée, accédez à la documentation interactive :
- **Swagger UI** : `http://localhost:8080/swagger-ui.html`
- **OpenAPI JSON** : `http://localhost:8080/v3/api-docs`

### Endpoints principaux

#### Fournisseurs
```
GET    /api/fournisseurs          # Liste avec pagination
GET    /api/fournisseurs/{id}     # Détails d'un fournisseur
POST   /api/fournisseurs          # Créer un fournisseur
PUT    /api/fournisseurs/{id}     # Modifier un fournisseur
DELETE /api/fournisseurs/{id}     # Supprimer un fournisseur
```

#### Produits
```
GET    /api/produits              # Liste avec pagination
GET    /api/produits/{id}         # Détails d'un produit
POST   /api/produits              # Créer un produit
PUT    /api/produits/{id}         # Modifier un produit
DELETE /api/produits/{id}         # Supprimer un produit
```

#### Commandes Fournisseurs
```
GET    /api/commandes             # Liste avec pagination et filtrage
GET    /api/commandes/{id}        # Détails d'une commande
POST   /api/commandes             # Créer une commande
PUT    /api/commandes/{id}        # Modifier une commande
PATCH  /api/commandes/{id}/statut # Changer le statut
DELETE /api/commandes/{id}        # Annuler une commande
```

#### Mouvements de Stock
```
GET    /api/mouvements            # Historique des mouvements
GET    /api/mouvements/produit/{id} # Mouvements d'un produit
GET    /api/mouvements/commande/{id} # Mouvements d'une commande
```

### Paramètres de pagination

Tous les endpoints GET supportent la pagination :
- `page` : numéro de page (défaut: 0)
- `size` : taille de la page (défaut: 10)
- `sort` : tri (ex: `sort=societe,asc`)

**Exemple** :
```
GET /api/fournisseurs?page=0&size=20&sort=societe,asc
```

## 🧪 Tests

### Lancer les tests unitaires
```bash
mvn test
```

### Collection Postman/Insomnia
Une collection JSON complète est disponible dans le dossier `/postman` pour tester tous les endpoints.

## 📦 Build et déploiement

### Créer le JAR exécutable
```bash
mvn clean package
```

Le fichier JAR sera généré dans : `target/tricol-commandes-1.0.0.jar`

### Lancer le JAR
```bash
java -jar target/tricol-commandes-1.0.0.jar
```

## 🔒 Gestion des exceptions

L'application implémente une gestion centralisée des exceptions :
- `ResourceNotFoundException` : Ressource non trouvée (404)
- `BadRequestException` : Requête invalide (400)
- `ValidationException` : Erreur de validation (422)
- `GlobalExceptionHandler` : Gestionnaire global des erreurs

## ✅ Principes SOLID appliqués

- **S**ingle Responsibility : Chaque classe a une responsabilité unique
- **O**pen/Closed : Ouvert à l'extension, fermé à la modification
- **L**iskov Substitution : Substitution des classes dérivées
- **I**nterface Segregation : Interfaces spécifiques et ciblées
- **D**ependency Inversion : Dépendance sur les abstractions

## 📈 Gestion du projet

### Suivi des tâches
Le projet est géré via **Jira** : https://charafeddinetbibzat.atlassian.net/jira/software/projects/FOUR/boards/211/timeline?timeline=WEEKS&atlOrigin=eyJpIjoiOWI1Mzg0YjdiMzdmNDgxZWJhNTYyYjE0ZTI5MmE1NTYiLCJwIjoiaiJ9

### Diagramme de classes
Le diagramme UML complet est disponible dans : `/docs/class-diagram.png`

## 👥 Contributeurs

- **charaf eddine tbibzat** - Développeur principal

## 📄 Licence

Ce projet est développé dans le cadre de la formation à YouCode.

## 📞 Contact

Pour toute question ou suggestion :
- Email : charafeddinetbibzat@gmail.com
- GitHub : charafeddine

---

**Tricol** © 2025 - Tous droits réservés
