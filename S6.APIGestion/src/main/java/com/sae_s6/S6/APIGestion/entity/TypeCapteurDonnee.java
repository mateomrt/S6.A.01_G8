package com.sae_s6.S6.APIGestion.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Représente une association entre un type de capteur et une donnée dans le système.
 * Cette classe est une entité JPA associée à la table "typecapteurdonnee" dans la base de données.
 */
@Entity
@Table(name = "typecapteurdonnee")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TypeCapteurDonnee {

    /**
     * Identifiant composite de l'association entre un type de capteur et une donnée.
     * Utilise une clé composée définie par la classe TypeCapteurDonneeEmbedId.
     */
    @EmbeddedId
    private TypeCapteurDonneeEmbedId id;

    /**
     * Référence à l'entité Donnee associée.
     * Ce champ représente une relation Many-to-One avec l'entité Donnee.
     * Les colonnes de la clé étrangère ne sont pas modifiables directement.
     */
    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "donnee_id", insertable = false, updatable = false)
    private Donnee donneeNavigation;

    /**
     * Référence à l'entité TypeCapteur associée.
     * Ce champ représente une relation Many-to-One avec l'entité TypeCapteur.
     * Les colonnes de la clé étrangère ne sont pas modifiables directement.
     */
    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "typecapteur_id", insertable = false, updatable = false)
    private TypeCapteur typeCapteurNavigation;

    /**
     * Vérifie si deux objets TypeCapteurDonnee sont égaux.
     * Deux associations sont considérées égales si elles ont le même identifiant composite.
     *
     * @param o L'objet à comparer.
     * @return true si les objets sont égaux, false sinon.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TypeCapteurDonnee that)) return false;
        return id != null && id.equals(that.id);
    }

    /**
     * Calcule le hash code de l'objet TypeCapteurDonnee.
     * Utilise l'identifiant composite pour le calcul.
     *
     * @return Le hash code de l'objet.
     */
    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}