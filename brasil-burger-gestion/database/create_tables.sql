-- Script de creation des tables pour Brasil Burger
-- Base de donnees: PostgreSQL (Neon)
-- Compatible avec PostgreSQL 14+

-- Table burgers
CREATE TABLE IF NOT EXISTS burgers (
    id BIGSERIAL PRIMARY KEY,
    nom VARCHAR(255) NOT NULL,
    prix NUMERIC(10,2) NOT NULL,
    image VARCHAR(255),
    archive BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Table menus
CREATE TABLE IF NOT EXISTS menus (
    id BIGSERIAL PRIMARY KEY,
    nom VARCHAR(255) NOT NULL,
    prix NUMERIC(10,2) NOT NULL,
    image VARCHAR(255),
    archive BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Table complements
CREATE TABLE IF NOT EXISTS complements (
    id BIGSERIAL PRIMARY KEY,
    nom VARCHAR(255) NOT NULL,
    prix NUMERIC(10,2) NOT NULL,
    image VARCHAR(255),
    archive BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Insertion de donnees de test pour les burgers
INSERT INTO burgers (nom, prix, image, archive) VALUES
('Big Brasil', 5000, '/images/big-brasil.jpg', FALSE),
('Chicken Brasil', 4500, '/images/chicken-brasil.jpg', FALSE),
('Cheese Brasil', 4000, '/images/cheese-brasil.jpg', FALSE);

-- Insertion de donnees de test pour les complements
INSERT INTO complements (nom, prix, image, archive) VALUES
('Frites', 1000, '/images/frites.jpg', FALSE),
('Coca Cola', 800, '/images/coca.jpg', FALSE),
('Sprite', 800, '/images/sprite.jpg', FALSE),
('Jus d''Orange', 1200, '/images/jus-orange.jpg', FALSE);

-- Insertion de donnees de test pour les menus
INSERT INTO menus (nom, prix, image, archive) VALUES
('Menu Big Brasil', 6500, '/images/menu-big-brasil.jpg', FALSE),
('Menu Chicken Brasil', 6000, '/images/menu-chicken-brasil.jpg', FALSE),
('Menu Cheese Brasil', 5500, '/images/menu-cheese-brasil.jpg', FALSE);

-- Affichage des donnees inserees
SELECT 'Burgers:' as Table_Name;
SELECT * FROM burgers;

SELECT 'Menus:' as Table_Name;
SELECT * FROM menus;

SELECT 'Complements:' as Table_Name;
SELECT * FROM complements;
