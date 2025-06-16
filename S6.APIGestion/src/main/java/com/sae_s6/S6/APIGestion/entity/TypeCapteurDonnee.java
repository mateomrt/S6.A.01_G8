package com.sae_s6.S6.APIGestion.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.sae_s6.S6.APIGestion.entity.TypeCapteur;



@Entity
@Table(name = "typecapteurdonnee")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TypeCapteurDonnee {


    @Column(name = "donnee_id", nullable = false)
    private Integer idDonnee;

    @Column(name = "typecapteur_id", nullable = false)
    private Integer idTypeCapteur;

    @ManyToOne
    @JoinColumn(name = "donnee_id", insertable = false, updatable = false)
    private Donnee donneeNavigation;

    @ManyToOne
    @JoinColumn(name = "typecapteur_id", insertable = false, updatable = false)
    private TypeCapteur typeCapteurNavigation;



}
