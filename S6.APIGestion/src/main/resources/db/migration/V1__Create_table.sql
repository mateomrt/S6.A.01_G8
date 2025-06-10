-- Table batiment
CREATE TABLE batiment (
    id SERIAL PRIMARY KEY,
    titre VARCHAR(250) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Table typesalle
CREATE TABLE typesalle (
    id SERIAL PRIMARY KEY,
    titre TEXT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Table salle
CREATE TABLE salle (
    id SERIAL PRIMARY KEY,
    batiment_id INTEGER NOT NULL,
    typesalle_id INTEGER NOT NULL,
    titre VARCHAR(250) NOT NULL,
    superficie DOUBLE PRECISION NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_salle_batiment FOREIGN KEY (batiment_id) REFERENCES batiment(id),
    CONSTRAINT fk_salle_typesalle FOREIGN KEY (typesalle_id) REFERENCES typesalle(id)
);

-- Table mur
CREATE TABLE mur (
    id SERIAL PRIMARY KEY,
    salle_id INTEGER NOT NULL,
    titre VARCHAR(250) NOT NULL,
    hauteur INTEGER NOT NULL,
    longueur INTEGER NOT NULL,
    orientation INTEGER NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_mur_salle FOREIGN KEY (salle_id) REFERENCES salle(id)
);

-- Table typecapteur
CREATE TABLE typecapteur (
    id SERIAL PRIMARY KEY,
    titre VARCHAR(250) NOT NULL,
    mode VARCHAR(250) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Table donnee
CREATE TABLE donnee (
    id SERIAL PRIMARY KEY,
    titre VARCHAR(250) NOT NULL,
    unite VARCHAR(250) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Table typecapteurdonnee (relation many-to-many)
CREATE TABLE typecapteurdonnee (
    donnee_id INTEGER NOT NULL,
    typecapteur_id INTEGER NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (donnee_id, typecapteur_id),
    CONSTRAINT fk_tcd_donnee FOREIGN KEY (donnee_id) REFERENCES donnee(id),
    CONSTRAINT fk_tcd_typecapteur FOREIGN KEY (typecapteur_id) REFERENCES typecapteur(id)
);

-- Table typeequipement
CREATE TABLE typeequipement (
    id SERIAL PRIMARY KEY,
    titre VARCHAR(250) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Table equipement
CREATE TABLE equipement (
    id SERIAL PRIMARY KEY,
    mur_id INTEGER NOT NULL,
    salle_id INTEGER NOT NULL,
    typeequipement_id INTEGER NOT NULL,
    titre VARCHAR(250) NOT NULL,
    hauteur INTEGER NOT NULL,
    largeur INTEGER NOT NULL,
    position_x INTEGER NOT NULL,
    position_y INTEGER NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_equipement_mur FOREIGN KEY (mur_id) REFERENCES mur(id),
    CONSTRAINT fk_equipement_salle FOREIGN KEY (salle_id) REFERENCES salle(id),
    CONSTRAINT fk_equipement_typeequipement FOREIGN KEY (typeequipement_id) REFERENCES typeequipement(id)
);

-- Table capteur
CREATE TABLE capteur (
    id SERIAL PRIMARY KEY,
    mur_id INTEGER NOT NULL,
    salle_id INTEGER NOT NULL,
    typecapteur_id INTEGER NOT NULL,
    titre VARCHAR(250) NOT NULL,
    position_x INTEGER NOT NULL,
    position_y INTEGER NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_capteur_mur FOREIGN KEY (mur_id) REFERENCES mur(id),
    CONSTRAINT fk_capteur_salle FOREIGN KEY (salle_id) REFERENCES salle(id),
    CONSTRAINT fk_capteur_typecapteur FOREIGN KEY (typecapteur_id) REFERENCES typecapteur(id)
);


