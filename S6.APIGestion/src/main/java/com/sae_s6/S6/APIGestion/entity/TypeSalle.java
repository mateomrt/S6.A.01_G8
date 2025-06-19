package com.sae_s6.S6.APIGestion.entity;

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
 * Représente un type de salle dans le système.
 * Cette classe est une entité JPA associée à la table "typesalle" dans la base de données.
 */
@Entity
@Table(name = "typesalle")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TypeSalle {

    /**
     * Identifiant unique du type de salle.
     * Généré automatiquement par la base de données.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * Libellé du type de salle.
     * Ce champ est obligatoire et sa longueur maximale est de 100 caractères.
     */
    @Column(name = "libelle_typesalle", nullable = false, length = 100)
    private String libelleTypeSalle;

    /**
     * Vérifie si deux objets TypeSalle sont égaux.
     * Deux types de salle sont considérés égaux s'ils ont le même identifiant et libellé.
     *
     * @param obj L'objet à comparer.
     * @return true si les objets sont égaux, false sinon.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        TypeSalle other = (TypeSalle) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (libelleTypeSalle == null) {
            if (other.libelleTypeSalle != null)
                return false;
        } else if (!libelleTypeSalle.equals(other.libelleTypeSalle))
            return false;
        return true;
    }

    /**
     * Calcule le hash code de l'objet TypeSalle.
     * Utilise les champs id et libellé pour le calcul.
     *
     * @return Le hash code de l'objet.
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((libelleTypeSalle == null) ? 0 : libelleTypeSalle.hashCode());
        return result;
    }

    /**
     * Retourne la description du type de salle.
     * Actuellement, cela correspond au libellé du type de salle.
     *
     * @return Le libellé du type de salle.
     */
    public String getDesc() {
        return libelleTypeSalle;
    }
}