package com.sae_s6.S6.APIGestion.entity;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Représente un type de capteur dans le système.
 * Cette classe est une entité JPA associée à la table "typecapteur" dans la base de données.
 */
@Entity
@Table(name = "typecapteur")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TypeCapteur {

    /**
     * Identifiant unique du type de capteur.
     * Généré automatiquement par la base de données.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * Libellé du type de capteur.
     * Ce champ est obligatoire et sa longueur maximale est de 100 caractères.
     */
    @Column(name = "libelle_typecapteur", nullable = false, length = 100)
    private String libelleTypeCapteur;

    /**
     * Mode du type de capteur.
     * Ce champ est obligatoire et sa longueur maximale est de 50 caractères.
     */
    @Column(name = "mode", nullable = false, length = 50)
    private String modeTypeCapteur;

    /**
     * Vérifie si deux objets TypeCapteur sont égaux.
     * Deux types de capteurs sont considérés égaux s'ils ont le même identifiant, libellé et mode.
     *
     * @param o L'objet à comparer.
     * @return true si les objets sont égaux, false sinon.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TypeCapteur typeCapteur)) return false;
        return Objects.equals(id, typeCapteur.id) &&
               Objects.equals(libelleTypeCapteur, typeCapteur.libelleTypeCapteur) &&
               Objects.equals(modeTypeCapteur, typeCapteur.modeTypeCapteur);
    }

    /**
     * Calcule le hash code de l'objet TypeCapteur.
     * Utilise les champs id, libellé et mode pour le calcul.
     *
     * @return Le hash code de l'objet.
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, libelleTypeCapteur, modeTypeCapteur);
    }
}