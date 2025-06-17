package com.sae_s6.S6.APIGestion.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TypeCapteurDonneeEmbedId implements Serializable {

    @Column(name = "donnee_id", nullable = false)
    private Integer idDonnee;

    @Column(name = "typecapteur_id", nullable = false)
    private Integer idTypeCapteur;
}