package com.sae_s6.S6.APIGestion.entity;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "equipement")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Equipement {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "titre", nullable = false, length = 250)
    private String titre;

    @Column(name = "hauteur", nullable = false)
    private Integer hauteur;

    @Column(name = "largeur", nullable = false)
    private Integer largeur;

    @Column(name = "position_x", nullable = false)
    private Integer position_x;

    @Column(name = "position_y", nullable = false)
    private Integer position_y;

    @ManyToOne
    @JoinColumn(name = "mur_id", nullable = false)
    private Mur mur;

    @ManyToOne
    @JoinColumn(name = "salle_id", nullable = false)
    private Salle salle;

    @ManyToOne
    @JoinColumn(name = "typeequipement_id", nullable = false)
    private TypeEquipement typeEquipement;

    

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Equipement equipement)) return false;
        return Objects.equals(id, equipement.id)
                && Objects.equals(titre, equipement.titre)
                && Objects.equals(hauteur, equipement.hauteur)
                && Objects.equals(largeur, equipement.largeur)
                && Objects.equals(position_x, equipement.position_x)
                && Objects.equals(position_x, equipement.position_y)
                && Objects.equals(mur, equipement.mur)
                && Objects.equals(salle, equipement.salle)
                && Objects.equals(typeEquipement, equipement.typeEquipement);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, titre, hauteur, largeur, position_x, position_y, mur, salle, typeEquipement);
    }
}
