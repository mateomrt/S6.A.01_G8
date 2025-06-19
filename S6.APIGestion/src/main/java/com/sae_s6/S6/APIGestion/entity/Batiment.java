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
 * Représente un bâtiment dans le système.
 * Cette classe est une entité JPA associée à la table "batiment" dans la base de données.
 */
@Entity
@Table(name = "batiment")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Batiment {

    /**
     * Identifiant unique du bâtiment.
     * Généré automatiquement par la base de données.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * Libellé du bâtiment.
     * Ce champ est obligatoire et sa longueur maximale est de 100 caractères.
     */
    @Column(name = "libelle_batiment", nullable = false, length = 100)
    private String libelleBatiment;

    /**
     * Vérifie si deux objets Batiment sont égaux.
     * Deux bâtiments sont considérés égaux s'ils ont le même identifiant et le même libellé.
     *
     * @param o L'objet à comparer.
     * @return true si les objets sont égaux, false sinon.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Batiment that)) return false;
        return Objects.equals(id, that.id) && Objects.equals(libelleBatiment, that.libelleBatiment);
    }

    /**
     * Calcule le hash code de l'objet Batiment.
     * Utilise les champs id et libelleBatiment pour le calcul.
     *
     * @return Le hash code de l'objet.
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, libelleBatiment);
    }

    /**
     * Retourne la description du bâtiment.
     * Actuellement, cela correspond au libellé du bâtiment.
     *
     * @return Le libellé du bâtiment.
     */
    public String getDesc() {
        return libelleBatiment;
    }
}