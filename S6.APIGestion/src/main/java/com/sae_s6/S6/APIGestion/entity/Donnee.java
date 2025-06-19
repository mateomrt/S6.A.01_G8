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
 * Représente une donnée dans le système.
 * Cette classe est une entité JPA associée à la table "donnee" dans la base de données.
 */
@Entity
@Table(name = "donnee")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Donnee {

    /**
     * Identifiant unique de la donnée.
     * Généré automatiquement par la base de données.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * Libellé de la donnée.
     * Ce champ est obligatoire et sa longueur maximale est de 100 caractères.
     */
    @Column(name = "libelle_donnee", nullable = false, length = 100)
    private String libelleDonnee;

    /**
     * Unité de la donnée.
     * Ce champ est obligatoire et sa longueur maximale est de 50 caractères.
     */
    @Column(name = "unite", nullable = false, length = 50)
    private String unite;

    /**
     * Vérifie si deux objets Donnee sont égaux.
     * Deux données sont considérées égales si elles ont le même identifiant, libellé et unité.
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
        Donnee other = (Donnee) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (libelleDonnee == null) {
            if (other.libelleDonnee != null)
                return false;
        } else if (!libelleDonnee.equals(other.libelleDonnee))
            return false;
        if (unite == null) {
            if (other.unite != null)
                return false;
        } else if (!unite.equals(other.unite))
            return false;
        return true;
    }

    /**
     * Calcule le hash code de l'objet Donnee.
     * Utilise les champs id, libellé et unité pour le calcul.
     *
     * @return Le hash code de l'objet.
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((libelleDonnee == null) ? 0 : libelleDonnee.hashCode());
        result = prime * result + ((unite == null) ? 0 : unite.hashCode());
        return result;
    }
}