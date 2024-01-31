CREATE DATABASE IF NOT EXISTS Devise;
-- Création de la table Utilisateurs
CREATE DOMAIN RoleDomain AS VARCHAR(255) CHECK (VALUE IN ('USER', 'ADMIN'));
CREATE TABLE IF NOT EXISTS Utilisateur (
                                           IdUtilisateur SERIAL PRIMARY KEY,
                                           Nom VARCHAR(255),
                                           Prenom VARCHAR(255),
                                           DateNaissance DATE,
                                           MotDePasseHache VARCHAR(255),
                                           Email VARCHAR(255),
                                           Adresse TEXT,
                                           roleutilisateur RoleDomain,
                                           DateCreation TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                           DerniereDateMAJ TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Création de la table Devise
CREATE TABLE IF NOT EXISTS Devise (
    --IdDevise SERIAL PRIMARY KEY,
                                      CodeDevise VARCHAR(3) PRIMARY KEY,
                                      NomDevise VARCHAR(255)
);

-- Création de la table DemandeDevise
CREATE DOMAIN StatutDemande AS VARCHAR(255) CHECK (VALUE IN ('ENATTENTE', 'ENCOURS', 'ACCEPTER',
                                                             'PAYER',
                                                             'ANNULER',
                                                             'TERMINER'));
CREATE TABLE IF NOT EXISTS DemandeDevise (
                                             IdDemande SERIAL PRIMARY KEY,
                                             IdDemandeur INTEGER REFERENCES Utilisateur(IdUtilisateur),
                                             deviseVoulu VARCHAR(3) REFERENCES Devise(CodeDevise),
                                             deviseOfferte VARCHAR(3) REFERENCES Devise(CodeDevise),
                                             montantVoulu NUMERIC(15, 2),
                                             Statut StatutDemande,
                                             DateDemande TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Création de la table OffreDevise
CREATE DOMAIN StatutOffre AS VARCHAR(255) CHECK (VALUE IN ('ENATTENTE', 'ACCEPTER', 'REFUSER', 'PAYER',
                                                           'ANNULER', 'TERMINER'));
CREATE TABLE IF NOT EXISTS OffreDevise (
                                           IdOffre SERIAL PRIMARY KEY,
                                           IdDemande INTEGER REFERENCES DemandeDevise(IdDemande),
                                           IdOffreur INTEGER REFERENCES Utilisateur(IdUtilisateur),
                                           Statut StatutOffre,
                                           DateOffre TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Création de la table Transaction
-- CREATE TABLE Transaction (
--                              IdTransaction SERIAL PRIMARY KEY,
--                              IdDemande INTEGER REFERENCES DemandeDevise(IdDemande),
--                              IdOffre INTEGER REFERENCES OffreDevise(IdOffre),
--                              DateTransaction TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
--                              StatutTransaction VARCHAR(255)
-- );

-- Create a table for comments
CREATE TABLE IF NOT EXISTS Commentaire (
                                           IdCommentaire SERIAL PRIMARY KEY,
                                           idutilisateurnoteur INTEGER REFERENCES Utilisateur(IdUtilisateur),
                                           idutilisateurnote INTEGER REFERENCES Utilisateur(IdUtilisateur),
                                           IdOffre INTEGER REFERENCES DemandeDevise(IdDemande),
                                           IdDemande INTEGER REFERENCES OffreDevise(IdOffre),
                                           Note SMALLINT,
                                           TexteCommentaire TEXT,
                                           CommentDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);