package com.sae_s6.S6.APIGestion.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Représente une clé composite pour l'association entre un type de capteur et une donnée.
 * Cette classe est utilisée comme clé primaire composite pour l'entité TypeCapteurDonnee.
 */
@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TypeCapteurDonneeEmbedId implements Serializable {

    /**
     * Identifiant de la donnée associée.
     * Ce champ est obligatoire et correspond à la clé étrangère vers l'entité Donnee.
     */
    @Column(name = "donnee_id", nullable = false)
    private Integer idDonnee;

    /**
     * Identifiant du type de capteur associé.
     * Ce champ est obligatoire et correspond à la clé étrangère vers l'entité TypeCapteur.
     */
    @Column(name = "typecapteur_id", nullable = false)
    private Integer idTypeCapteur;
}