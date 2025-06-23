package com.sae_s6.S6.APIGestion.entity;

import java.util.Objects;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.EnumType;

/**
 * Représente un mur dans le système.
 * Cette classe est une entité JPA associée à la table "mur" dans la base de données.
 */
@Entity
@Table(name = "mur")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Mur {

    /**
     * Identifiant unique du mur.
     * Généré automatiquement par la base de données.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * Libellé du mur.
     * Ce champ est obligatoire et sa longueur maximale est de 75 caractères.
     */
    @Column(name = "libelle_mur", nullable = false, length = 75)
    private String libelleMur;

    /**
     * Hauteur du mur.
     * Ce champ est obligatoire et représente la hauteur en unités.
     */
    @Column(name = "hauteur", nullable = false)
    private Double hauteur;

    /**
     * Longueur du mur.
     * Ce champ est obligatoire et représente la longueur en unités.
     */
    @Column(name = "longueur", nullable = false)
    private Double longueur;

    /**
     * Orientation du mur.
     * Ce champ est obligatoire et utilise une énumération pour représenter l'orientation.
     * Les valeurs possibles sont : N, E, S, O, NE, NO, SE, SO.
     */
    @Enumerated(EnumType.STRING) // Stocke le nom de l'enum dans la colonne au lieu de son index.
    @Column(name = "orientation", nullable = false)
    private Orientation orientation;

    /**
     * Référence à la salle associée au mur.
     * Ce champ est obligatoire et représente une relation Many-to-One avec l'entité Salle.
     */
    @ManyToOne
    @JoinColumn(name = "salle_id", referencedColumnName = "id", nullable = false)
    private Salle salleNavigation;

    /**
     * Enumération représentant les orientations possibles d'un mur.
     */
    public enum Orientation {
        N,  // Nord
        E,  // Est
        S,  // Sud
        O,  // Ouest
        NE, // Nord-Est
        NO, // Nord-Ouest
        SE, // Sud-Est
        SO  // Sud-Ouest
    }

    /**
     * Vérifie si deux objets Mur sont égaux.
     * Deux murs sont considérés égaux s'ils ont le même identifiant, libellé, dimensions, orientation et salle associée.
     *
     * @param o L'objet à comparer.
     * @return true si les objets sont égaux, false sinon.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Mur mur)) return false;
        return Objects.equals(id, mur.id) &&
               Objects.equals(libelleMur, mur.libelleMur) &&
               Objects.equals(hauteur, mur.hauteur) &&
               Objects.equals(longueur, mur.longueur) &&
               Objects.equals(orientation, mur.orientation) &&
               Objects.equals(salleNavigation, mur.salleNavigation);
    }

    /**
     * Calcule le hash code de l'objet Mur.
     * Utilise les champs id, libellé, dimensions, orientation et salle associée pour le calcul.
     *
     * @return Le hash code de l'objet.
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, libelleMur, hauteur, longueur, orientation, salleNavigation);
    }

    /**
     * Retourne la description du mur.
     * Actuellement, cela correspond au libellé du mur.
     *
     * @return Le libellé du mur.
     */
    public String getDesc() {
        return libelleMur;
    }
}