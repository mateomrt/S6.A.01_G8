
-- Insertion dans batiment
INSERT INTO batiment (titre) VALUES 
('Bâtiment A'), 
('Bâtiment B');

-- Insertion dans typesalle
INSERT INTO typesalle (libelle_type_salle) VALUES 
('Salle de réunion'), 
('Bureau'), 
('Laboratoire');

-- Insertion dans salle
INSERT INTO salle (batiment_id, typesalle_id, titre, superficie) VALUES 
(1, 1, 'Salle 101', 35.5), 
(1, 2, 'Bureau 102', 18.0), 
(2, 3, 'Lab 201', 45.0);

-- Insertion dans mur
INSERT INTO mur (salle_id, titre, hauteur, longueur, orientation) VALUES 
(1, 'Mur nord', 250, 600, 'N'),
(1, 'Mur sud', 250, 600, 'S'),
(2, 'Mur est', 250, 400, 'E'),
(3, 'Mur ouest', 300, 500, 'O');


-- Insertion dans typecapteur
INSERT INTO typecapteur (titre, mode) VALUES 
('Thermique', 'actif'), 
('Humidité', 'passif');

-- Insertion dans donnee
INSERT INTO donnee (titre, unite) VALUES 
('Température', '°C'), 
('Humidité relative', '%');

-- Insertion dans typecapteurdonnee
INSERT INTO typecapteurdonnee (donnee_id, typecapteur_id) VALUES 
(1, 1), 
(2, 2);

-- Insertion dans typeequipement
INSERT INTO typeequipement (titre) VALUES 
('Ordinateur'), 
('Projecteur');

-- Insertion dans equipement
INSERT INTO equipement (mur_id, salle_id, typeequipement_id, titre, hauteur, largeur, position_x, position_y) VALUES 
(1, 1, 1, 'PC principal', 50, 30, 100, 200), 
(2, 1, 2, 'Vidéo projecteur', 20, 40, 250, 100);

-- Insertion dans capteur
INSERT INTO capteur (mur_id, salle_id, typecapteur_id, titre, position_x, position_y) VALUES 
(1, 1, 1, 'Capteur Température 1', 100, 150), 
(2, 1, 2, 'Capteur Humidité 1', 200, 150), 
(3, 2, 2, 'Capteur Humidité 2', 50, 100);
