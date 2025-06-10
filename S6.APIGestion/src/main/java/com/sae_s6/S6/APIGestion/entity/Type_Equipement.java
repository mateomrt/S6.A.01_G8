package com.sae_s6.S6.APIGestion.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "type_equipement")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Type_Equipement {
    
    @Id
    private Integer id_type_equipement;

    @Column(name = "libelle_type_equipement", nullable = false, length = 250)
    private String titre;
}
