package com.sae_s6.S6.APIGestion.entity;

import java.util.Objects;

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
 * Représente un capteur dans le système.
 * Cette classe est une entité JPA associée à la table "capteur" dans la base de données.
 */
@Entity
@Table(name = "capteur")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Capteur {

    /**
     * Identifiant unique du capteur.
     * Généré automatiquement par la base de données.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * Libellé du capteur.
     * Ce champ est obligatoire et sa longueur maximale est de 100 caractères.
     */
    @Column(name = "libelle_capteur", nullable = false, length = 100)
    private String libelleCapteur;

    /**
     * Position X du capteur.
     * Ce champ est obligatoire et représente la coordonnée X dans l'espace.
     */
    @Column(name = "position_x", nullable = false)
    private Double positionXCapteur;

    /**
     * Position Y du capteur.
     * Ce champ est obligatoire et représente la coordonnée Y dans l'espace.
     */
    @Column(name = "position_y", nullable = false)
    private Double positionYCapteur;

    /**
     * Référence au mur associé au capteur.
     * Ce champ est obligatoire et représente une relation Many-to-One avec l'entité Mur.
     */
    @ManyToOne
    @JoinColumn(name = "mur_id", referencedColumnName = "id", nullable = false)
    private Mur murNavigation;

    /**
     * Référence à la salle associée au capteur.
     * Ce champ est obligatoire et représente une relation Many-to-One avec l'entité Salle.
     */
    @ManyToOne
    @JoinColumn(name = "salle_id", referencedColumnName = "id", nullable = false)
    private Salle salleNavigation;

    /**
     * Référence au type de capteur.
     * Ce champ est obligatoire et représente une relation Many-to-One avec l'entité TypeCapteur.
     */
    @ManyToOne
    @JoinColumn(name = "typecapteur_id", referencedColumnName = "id", nullable = false)
    private TypeCapteur typeCapteurNavigation;

    /**
     * Vérifie si deux objets Capteur sont égaux.
     * Deux capteurs sont considérés égaux s'ils ont le même identifiant, type, position et relations.
     *
     * @param o L'objet à comparer.
     * @return true si les objets sont égaux, false sinon.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Capteur capteur)) return false;
        return Objects.equals(id, capteur.id) &&
               Objects.equals(typeCapteurNavigation, capteur.typeCapteurNavigation) &&
               Objects.equals(positionXCapteur, capteur.positionXCapteur) &&
               Objects.equals(positionYCapteur, capteur.positionYCapteur) &&
               Objects.equals(murNavigation, capteur.murNavigation) &&
               Objects.equals(salleNavigation, capteur.salleNavigation);
    }

    /**
     * Calcule le hash code de l'objet Capteur.
     * Utilise les champs id, type, position et relations pour le calcul.
     *
     * @return Le hash code de l'objet.
     */
    @Override
    public int hashCode() {
        return Objects.hash(
            id, 
            typeCapteurNavigation, 
            positionXCapteur, 
            positionYCapteur, 
            murNavigation, 
            salleNavigation
        );
    }
}