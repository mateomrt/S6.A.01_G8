package com.sae_s6.S6.APIGestion.entity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "typecapteurdonnee")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TypeCapteurDonnee {

    @EmbeddedId
    private TypeCapteurDonneeEmbedId id;

    @ManyToOne
    @JoinColumn(name = "donnee_id", insertable = false, updatable = false)
    private Donnee donneeNavigation;

    @ManyToOne
    @JoinColumn(name = "typecapteur_id", insertable = false, updatable = false)
    private TypeCapteur typeCapteurNavigation;
}