package com.sae_s6.S6.APIGestion.entity;

import java.util.Objects;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Représente un équipement dans le système.
 * Cette classe est une entité JPA associée à la table "equipement" dans la base de données.
 */
@Entity
@Table(name = "equipement")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Equipement {

    /**
     * Identifiant unique de l'équipement.
     * Généré automatiquement par la base de données.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * Libellé de l'équipement.
     * Ce champ est obligatoire et sa longueur maximale est de 150 caractères.
     */
    @Column(name = "libelle_equipement", nullable = false, length = 150)
    private String libelleEquipement;

    /**
     * Hauteur de l'équipement.
     * Ce champ est obligatoire et représente la hauteur en unités.
     */
    @Column(name = "hauteur", nullable = false)
    private Double hauteur;

    /**
     * Largeur de l'équipement.
     * Ce champ est obligatoire et représente la largeur en unités.
     */
    @Column(name = "largeur", nullable = false)
    private Double largeur;

    /**
     * Position X de l'équipement.
     * Ce champ est obligatoire et représente la coordonnée X dans l'espace.
     */
    @Column(name = "position_x", nullable = false)
    private Double position_x;

    /**
     * Position Y de l'équipement.
     * Ce champ est obligatoire et représente la coordonnée Y dans l'espace.
     */
    @Column(name = "position_y", nullable = false)
    private Double position_y;

    /**
     * Référence au mur associé à l'équipement.
     * Ce champ est optionnel et représente une relation Many-to-One avec l'entité Mur.
     */
    @ManyToOne
    @JoinColumn(name = "mur_id", referencedColumnName = "id", nullable = true)
    private Mur murNavigation;

    /**
     * Référence à la salle associée à l'équipement.
     * Ce champ est obligatoire et représente une relation Many-to-One avec l'entité Salle.
     */
    @ManyToOne
    @JoinColumn(name = "salle_id", referencedColumnName = "id", nullable = false)
    private Salle salleNavigation;

    /**
     * Référence au type d'équipement.
     * Ce champ est obligatoire et représente une relation Many-to-One avec l'entité TypeEquipement.
     */
    @ManyToOne
    @JoinColumn(name = "typeequipement_id", referencedColumnName = "id", nullable = false)
    private TypeEquipement typeEquipementNavigation;

    /**
     * Vérifie si deux objets Equipement sont égaux.
     * Deux équipements sont considérés égaux s'ils ont le même identifiant, libellé, dimensions, positions et relations.
     *
     * @param o L'objet à comparer.
     * @return true si les objets sont égaux, false sinon.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Equipement equipement)) return false;
        return Objects.equals(id, equipement.id)
                && Objects.equals(libelleEquipement, equipement.libelleEquipement)
                && Objects.equals(hauteur, equipement.hauteur)
                && Objects.equals(largeur, equipement.largeur)
                && Objects.equals(position_x, equipement.position_x)
                && Objects.equals(position_y, equipement.position_y)
                && Objects.equals(murNavigation, equipement.murNavigation)
                && Objects.equals(salleNavigation, equipement.salleNavigation)
                && Objects.equals(typeEquipementNavigation, equipement.typeEquipementNavigation);
    }

    /**
     * Calcule le hash code de l'objet Equipement.
     * Utilise les champs id, libellé, dimensions, positions et relations pour le calcul.
     *
     * @return Le hash code de l'objet.
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, libelleEquipement, hauteur, largeur, position_x, position_y, murNavigation, salleNavigation, typeEquipementNavigation);
    }
}