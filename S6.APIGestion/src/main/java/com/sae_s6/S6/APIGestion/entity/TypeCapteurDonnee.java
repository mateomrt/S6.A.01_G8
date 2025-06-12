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

@Entity
@Table(name = "typecapteurdonnee")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TypeCapteurDonnee {


    @Id
    @Column(name = "donnee_id", nullable = false)
    private Integer idDonnee;

    @Id
    @Column(name = "typecapteur_id", nullable = false)
    private Integer idTypeCapteur;

    @ManyToOne
    @JoinColumn(name = "donnee_id")
    private Donnee donneeNavigation;

    @ManyToOne
    @JoinColumn(name = "typecapteur_id")
    private TypeCapteur typeCapteurNavigation;



}
