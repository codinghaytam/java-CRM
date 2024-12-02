-- Base de données actuelle
CREATE DATABASE IF NOT EXISTS crm_db;
USE crm_db;

CREATE TABLE agent_commercial (
    CNE VARCHAR(10) PRIMARY KEY,        
    nom VARCHAR(50) NOT NULL,           
    prenom VARCHAR(50) NOT NULL,        
    password VARCHAR(255) NOT NULL,  
    supervisor_CNE VARCHAR(10),         
    FOREIGN KEY (supervisor_CNE) REFERENCES Supervisor(CNE)  
);

CREATE TABLE Supervisor (
    CNE VARCHAR(10) PRIMARY KEY,       
    nom VARCHAR(50) NOT NULL,           
    prenom VARCHAR(50) NOT NULL,        
    password VARCHAR(255) NOT NULL      
);
CREATE TABLE leads (
                       entrepriseId CHAR(36) PRIMARY KEY DEFAULT (UUID()), -- UUID auto-généré
                       entrepriseName VARCHAR(100) NOT NULL,
                       headquarters VARCHAR(150),
                       phone VARCHAR(15),
                       email VARCHAR(150),
                       agent_CNE VARCHAR(10),
                       FOREIGN KEY (agent_CNE) REFERENCES agent_commercial(CNE) ON DELETE SET NULL
);
CREATE TABLE carteDeFidelite (
                                 carteDeFideliteId CHAR(36) PRIMARY KEY DEFAULT (UUID()), -- UUID auto-généré
                                 entrepriseId CHAR(36) NOT NULL,
                                 statut ENUM('active','suspendue') DEFAULT 'suspendue',
                                 dateDeCreation DATETIME DEFAULT CURRENT_TIMESTAMP,
                                 FOREIGN KEY (entrepriseId) REFERENCES clients(entrepriseId) ON DELETE CASCADE
);
CREATE TABLE clients (
                       entrepriseId CHAR(36) PRIMARY KEY DEFAULT (UUID()), -- UUID auto-généré
                       entrepriseName VARCHAR(100) NOT NULL,
                       headquarters VARCHAR(150),
                       phone VARCHAR(15),
                       email VARCHAR(150),
                       agent_CNE VARCHAR(10),
                       FOREIGN KEY (agent_CNE) REFERENCES agent_commercial(CNE) ON DELETE SET NULL
);

CREATE TABLE categories (
                            categorieId CHAR(8) PRIMARY KEY,-- United Nations Standard Products and Services Code
                            nomCategorie VARCHAR(100) NOT NULL
);

CREATE TABLE remises (
                         carteDeFideliteId CHAR(36) NOT NULL,
                         categorieId CHAR(8) NOT NULL,
                         remisePourcentage FLOAT NOT NULL,
                         PRIMARY KEY (carteDeFideliteId, categorieId),
                         FOREIGN KEY (carteDeFideliteId) REFERENCES carteDeFidelite(carteDeFideliteId) ON DELETE CASCADE,
                         FOREIGN KEY (categorieId) REFERENCES categories(categorieId) ON DELETE CASCADE
);

CREATE TABLE demandes (
                          demandeId CHAR(36) PRIMARY KEY DEFAULT (UUID()), -- UUID auto-généré
                          entrepriseId CHAR(36),
                          CNE VARCHAR(10),
                          carteDeFideliteId CHAR(36),
                          statut ENUM('enAttente','approuvee','rejettee') DEFAULT 'enAttente',
                          dateDeCreation DATETIME DEFAULT CURRENT_TIMESTAMP,
                          FOREIGN KEY (entrepriseId) REFERENCES leads(entrepriseId) ON DELETE SET NULL,
                          FOREIGN KEY (CNE) REFERENCES agent_commercial(CNE) ON DELETE SET NULL,
                          FOREIGN KEY (carteDeFideliteId) REFERENCES carteDeFidelite(carteDeFideliteId) ON DELETE SET NULL
);

CREATE TABLE produits (
                          produitId VARCHAR(50) PRIMARY KEY, -- Identifiant manuel basé sur le marché
                          produitNom VARCHAR(50) NOT NULL,
                          produitsCategorie CHAR(8) NOT NULL, -- Référence vers la table categories
                          prix FLOAT NOT NULL,
                          FOREIGN KEY (produitsCategorie) REFERENCES categories(categorieId) ON DELETE CASCADE
);

CREATE TABLE commande (
                          commandeId CHAR(36) PRIMARY KEY DEFAULT (UUID()), -- UUID auto-généré
                          CNE VARCHAR(36),
                          carteDeFideliteId CHAR(36),
                          prixTotal FLOAT NOT NULL,
                          dateDeCreation DATETIME DEFAULT CURRENT_TIMESTAMP,
                          FOREIGN KEY (CNE) REFERENCES agent_commercial(CNE) ON DELETE SET NULL,
                          FOREIGN KEY (carteDeFideliteId) REFERENCES carteDeFidelite(carteDeFideliteId) ON DELETE SET NULL
);

CREATE TABLE produitcommande (
                                 commandeId CHAR(36),
                                 produitId VARCHAR(50),
                                 quantite INT NOT NULL,
                                 PRIMARY KEY (commandeId, produitId),
                                 FOREIGN KEY (commandeId) REFERENCES commande(commandeId) ON DELETE CASCADE,
                                 FOREIGN KEY (produitId) REFERENCES produits(produitId) ON DELETE CASCADE
);
