INSERT INTO "sae6apigestion".batiment (libelle_batiment) VALUES 
('Bâtiment A'), 
('Bâtiment B');

INSERT INTO "sae6apigestion".typesalle (libelle_typesalle) VALUES 
('Salle de réunion'), 
('Bureau'), 
('Laboratoire');

INSERT INTO "sae6apigestion".salle (batiment_id, typesalle_id, libelle_salle, superficie) VALUES 
(1, 1, 'Salle 101', 35.5), 
(1, 2, 'Bureau 102', 18.0), 
(2, 3, 'Lab 201', 45.0);

INSERT INTO "sae6apigestion".mur (salle_id, libelle_mur, hauteur, longueur, orientation) VALUES 
(1, 'Mur nord', 250, 600, 'N'),
(1, 'Mur sud', 250, 600, 'S'),
(2, 'Mur est', 250, 400, 'E'),
(3, 'Mur ouest', 300, 500, 'O');

INSERT INTO "sae6apigestion".typecapteur (libelle_typecapteur, mode) VALUES 
('Thermique', 'actif'), 
('Humidité', 'passif');

INSERT INTO "sae6apigestion".donnee (libelle_donnee, unite) VALUES 
('Température', '°C'), 
('Humidité relative', '%');

INSERT INTO "sae6apigestion".typecapteurdonnee (donnee_id, typecapteur_id) VALUES 
(1, 1), 
(2, 2);

INSERT INTO "sae6apigestion".typeequipement (libelle_typeequipement) VALUES 
('Ordinateur'), 
('Projecteur');

INSERT INTO "sae6apigestion".equipement (mur_id, salle_id, typeequipement_id, libelle_equipement, hauteur, largeur, position_x, position_y) VALUES 
(1, 1, 1, 'PC principal', 50, 30, 100, 200), 
(2, 1, 2, 'Vidéo projecteur', 20, 40, 250, 100);

INSERT INTO "sae6apigestion".capteur (mur_id, salle_id, typecapteur_id, libelle_capteur, position_x, position_y) VALUES 
(1, 1, 1, 'Capteur Température 1', 100, 150), 
(2, 1, 2, 'Capteur Humidité 1', 200, 150), 
(3, 2, 2, 'Capteur Humidité 2', 50, 100);
