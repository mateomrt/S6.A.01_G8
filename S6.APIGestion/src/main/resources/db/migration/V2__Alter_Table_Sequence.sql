CREATE SEQUENCE IF NOT EXISTS batiment_id_seq;
CREATE SEQUENCE IF NOT EXISTS typesalle_id_seq;
CREATE SEQUENCE IF NOT EXISTS salle_id_seq;
CREATE SEQUENCE IF NOT EXISTS mur_id_seq;
CREATE SEQUENCE IF NOT EXISTS typecapteur_id_seq;
CREATE SEQUENCE IF NOT EXISTS donnee_id_seq;
CREATE SEQUENCE IF NOT EXISTS typeequipement_id_seq;
CREATE SEQUENCE IF NOT EXISTS equipement_id_seq;
CREATE SEQUENCE IF NOT EXISTS capteur_id_seq;

ALTER TABLE batiment
    ALTER COLUMN id SET DEFAULT nextval('batiment_id_seq'),
    ALTER COLUMN id SET NOT NULL;

ALTER TABLE typesalle
    ALTER COLUMN id SET DEFAULT nextval('typesalle_id_seq'),
    ALTER COLUMN id SET NOT NULL;

ALTER TABLE salle
    ALTER COLUMN id SET DEFAULT nextval('salle_id_seq'),
    ALTER COLUMN id SET NOT NULL;

ALTER TABLE mur
    ALTER COLUMN id SET DEFAULT nextval('mur_id_seq'),
    ALTER COLUMN id SET NOT NULL;

ALTER TABLE typecapteur
    ALTER COLUMN id SET DEFAULT nextval('typecapteur_id_seq'),
    ALTER COLUMN id SET NOT NULL;

ALTER TABLE donnee
    ALTER COLUMN id SET DEFAULT nextval('donnee_id_seq'),
    ALTER COLUMN id SET NOT NULL;

ALTER TABLE typeequipement
    ALTER COLUMN id SET DEFAULT nextval('typeequipement_id_seq'),
    ALTER COLUMN id SET NOT NULL;

ALTER TABLE equipement
    ALTER COLUMN id SET DEFAULT nextval('equipement_id_seq'),
    ALTER COLUMN id SET NOT NULL;

ALTER TABLE capteur
    ALTER COLUMN id SET DEFAULT nextval('capteur_id_seq'),
    ALTER COLUMN id SET NOT NULL;

ALTER SEQUENCE batiment_id_seq OWNED BY batiment.id;
ALTER SEQUENCE typesalle_id_seq OWNED BY typesalle.id;
ALTER SEQUENCE salle_id_seq OWNED BY salle.id;
ALTER SEQUENCE mur_id_seq OWNED BY mur.id;
ALTER SEQUENCE typecapteur_id_seq OWNED BY typecapteur.id;
ALTER SEQUENCE donnee_id_seq OWNED BY donnee.id;
ALTER SEQUENCE typeequipement_id_seq OWNED BY typeequipement.id;
ALTER SEQUENCE equipement_id_seq OWNED BY equipement.id;
ALTER SEQUENCE capteur_id_seq OWNED BY capteur.id;
