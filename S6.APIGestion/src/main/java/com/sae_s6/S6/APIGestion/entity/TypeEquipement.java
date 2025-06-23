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
 * Représente un type d'équipement dans le système.
 * Cette classe est une entité JPA associée à la table "typeequipement" dans la base de données.
 */
@Entity
@Table(name = "typeequipement")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TypeEquipement {

    /**
     * Identifiant unique du type d'équipement.
     * Généré automatiquement par la base de données.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * Libellé du type d'équipement.
     * Ce champ est obligatoire et sa longueur maximale est de 100 caractères.
     */
    @Column(name = "libelle_typeequipement", nullable = false, length = 100)
    private String libelleTypeEquipement;

    /**
     * Vérifie si deux objets TypeEquipement sont égaux.
     * Deux types d'équipement sont considérés égaux s'ils ont le même identifiant et libellé.
     *
     * @param o L'objet à comparer.
     * @return true si les objets sont égaux, false sinon.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TypeEquipement typeEquipement)) return false;
        return Objects.equals(id, typeEquipement.id) &&
               Objects.equals(libelleTypeEquipement, typeEquipement.libelleTypeEquipement);
    }

    /**
     * Calcule le hash code de l'objet TypeEquipement.
     * Utilise les champs id et libellé pour le calcul.
     *
     * @return Le hash code de l'objet.
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, libelleTypeEquipement);
    }

    /**
     * Retourne la description du type d'équipement.
     * Actuellement, cela correspond au libellé du type d'équipement.
     *
     * @return Le libellé du type d'équipement.
     */
    public String getDesc() {
        return libelleTypeEquipement;
    }
}