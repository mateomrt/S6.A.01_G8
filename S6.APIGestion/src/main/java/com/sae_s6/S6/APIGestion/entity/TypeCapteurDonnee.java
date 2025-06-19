package com.sae_s6.S6.APIGestion.entity;

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
    @ManyToOne
    @JoinColumn(name = "donnee_id", insertable = false, updatable = false)
    private Donnee donneeNavigation;

    /**
     * Référence à l'entité TypeCapteur associée.
     * Ce champ représente une relation Many-to-One avec l'entité TypeCapteur.
     * Les colonnes de la clé étrangère ne sont pas modifiables directement.
     */
    @ManyToOne
    @JoinColumn(name = "typecapteur_id", insertable = false, updatable = false)
    private TypeCapteur typeCapteurNavigation;
}