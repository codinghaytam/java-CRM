CREATE DATABASE IF NOT EXISTS CRMDB;
USE CRMDB;

-- Fonction pour générer des UUID automatiquement (MySQL 8+ uniquement)
-- Si MySQL 8+ n'est pas disponible, les UUID devront être générés côté application.
-- Les UUID() peuvent être généré dehors dans le code java
-- Exemple : INSERT INTO table_name (id, ...) VALUES (UUID(), ...);

-- Table Employees
CREATE TABLE employees (
                           CIN VARCHAR(36) PRIMARY KEY, -- Clé primaire sous forme de chaîne (par exemple, numéro de carte d'identité)
                           nom VARCHAR(100) NOT NULL,
                           prenom VARCHAR(100) NOT NULL,
                           telephone VARCHAR(15),
                           email VARCHAR(150) UNIQUE,
                           post VARCHAR(100), -- Poste occupée par l'employé
                           superviseurId VARCHAR(36), -- ID du superviseur (référence à CIN d'un autre employé)
                           dateDeCreation DATETIME DEFAULT CURRENT_TIMESTAMP,
                           FOREIGN KEY (superviseurId) REFERENCES employees(CIN) ON DELETE SET NULL -- Clé étrangère vers la même table
);

-- Table Clients
CREATE TABLE clients (
                         clientId CHAR(36) PRIMARY KEY DEFAULT (UUID()), -- UUID auto-généré
                         nom VARCHAR(100) NOT NULL,
                         telephone VARCHAR(15),
                         email VARCHAR(150) UNIQUE,
                         employeeId VARCHAR(36), -- ID de l'employé responsable (référence à CIN dans employees)
                         dateDeCreation DATETIME DEFAULT CURRENT_TIMESTAMP,
                         FOREIGN KEY (employeeId) REFERENCES employees(CIN) ON DELETE SET NULL -- Clé étrangère vers la table employees
);


-- Table CarteDeFidelite
CREATE TABLE carteDeFidelite (
                        carteDeFideliteId CHAR(36) PRIMARY KEY DEFAULT (UUID()), -- UUID auto-généré
                        clientId CHAR(36) NOT NULL,
                        statut ENUM('active','suspendue') DEFAULT 'suspendue',
                        dateDeCreation DATETIME DEFAULT CURRENT_TIMESTAMP,
                        FOREIGN KEY (clientId) REFERENCES clients(clientId) ON DELETE CASCADE
);

-- Table Leads
CREATE TABLE leads (
                       leadId CHAR(36) PRIMARY KEY DEFAULT (UUID()), -- UUID auto-généré
                       nom VARCHAR(100) NOT NULL,
                       telephone VARCHAR(15),
                       email VARCHAR(150),
                       employeeId VARCHAR(36),
                       statut ENUM('nouveau','contacte','qualifie','converti','ferme') DEFAULT 'nouveau',
                       dateDeCreation DATETIME DEFAULT CURRENT_TIMESTAMP,
                       FOREIGN KEY (employeeId) REFERENCES employees(CIN) ON DELETE SET NULL
);

-- Table Demandes
CREATE TABLE demandes (
                      demandeId CHAR(36) PRIMARY KEY DEFAULT (UUID()), -- UUID auto-généré
                      leadId CHAR(36),
                      employeeId VARCHAR(36),
                      carteDeFideliteId CHAR(36),
                      statut ENUM('enAttente','approuvee','rejettee') DEFAULT 'enAttente',
                      dateDeCreation DATETIME DEFAULT CURRENT_TIMESTAMP,
                      FOREIGN KEY (leadId) REFERENCES leads(leadId) ON DELETE SET NULL,
                      FOREIGN KEY (employeeId) REFERENCES employees(CIN) ON DELETE SET NULL,
                      FOREIGN KEY (carteDeFideliteId) REFERENCES carteDeFidelite(carteDeFideliteId) ON DELETE SET NULL
);

-- Table catégories de produits
CREATE TABLE categories (
                        categorieId CHAR(8) PRIMARY KEY,-- United Nations Standard Products and Services Code
                        nomCategorie VARCHAR(100) NOT NULL
);

-- Table remises
CREATE TABLE remises (
                         carteDeFideliteId CHAR(36) NOT NULL,
                         categorieId CHAR(8) NOT NULL,
                         remisePourcentage FLOAT NOT NULL,
                         PRIMARY KEY (carteDeFideliteId, categorieId),
                         FOREIGN KEY (carteDeFideliteId) REFERENCES carteDeFidelite(carteDeFideliteId) ON DELETE CASCADE,
                         FOREIGN KEY (categorieId) REFERENCES categories(categorieId) ON DELETE CASCADE
);

-- Table produits
CREATE TABLE produits (
                          produitId VARCHAR(50) PRIMARY KEY, -- Identifiant manuel basé sur le marché
                          produitTitle VARCHAR(50) NOT NULL, -- Le nom du produit
                          categorieId CHAR(8) NOT NULL, -- Référence vers la table categories
                          prix FLOAT NOT NULL,
                          FOREIGN KEY (categorieId) REFERENCES categories(categorieId) ON DELETE CASCADE
);

-- Table Commande
CREATE TABLE commande (
                          commandeId CHAR(36) PRIMARY KEY DEFAULT (UUID()), -- UUID auto-généré
                          employeeId VARCHAR(36),
                          carteDeFideliteId CHAR(36),
                          prixTotal FLOAT NOT NULL,
                          dateDeCreation DATETIME DEFAULT CURRENT_TIMESTAMP,
                          FOREIGN KEY (employeeId) REFERENCES employees(CIN) ON DELETE SET NULL,
                          FOREIGN KEY (carteDeFideliteId) REFERENCES carteDeFidelite(carteDeFideliteId) ON DELETE SET NULL
);

-- Table ProduitCommande
CREATE TABLE produitcommande (
                                 commandeId CHAR(36),
                                 produitId VARCHAR(50),
                                 quantite INT NOT NULL,
                                 PRIMARY KEY (commandeId, produitId),
                                 FOREIGN KEY (commandeId) REFERENCES commande(commandeId) ON DELETE CASCADE,
                                 FOREIGN KEY (produitId) REFERENCES produits(produitId) ON DELETE CASCADE
);
