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

    @Column(name = "libelle_equipement", nullable = false, length = 150)
    private String libelleEquipement;

    @Column(name = "hauteur", nullable = false)
    private Double hauteur;

    @Column(name = "largeur", nullable = false)
    private Double largeur;

    @Column(name = "position_x", nullable = false)
    private Double position_x;

    @Column(name = "position_y", nullable = false)
    private Double position_y;

    @ManyToOne
    @JoinColumn(name = "mur_id", referencedColumnName = "id", nullable = true)
    private Mur murNavigation;

    @ManyToOne
    @JoinColumn(name = "salle_id", referencedColumnName = "id", nullable = false)
    private Salle salleNavigation;

    @ManyToOne
    @JoinColumn(name = "typeequipement_id", referencedColumnName = "id", nullable = false)
    private TypeEquipement typeEquipementNavigation;

    

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Equipement equipement)) return false;
        return Objects.equals(id, equipement.id)
                && Objects.equals(libelleEquipement, equipement.libelleEquipement)
                && Objects.equals(hauteur, equipement.hauteur)
                && Objects.equals(largeur, equipement.largeur)
                && Objects.equals(position_x, equipement.position_x)
                && Objects.equals(position_x, equipement.position_y)
                && Objects.equals(murNavigation, equipement.murNavigation)
                && Objects.equals(salleNavigation, equipement.salleNavigation)
                && Objects.equals(typeEquipementNavigation, equipement.typeEquipementNavigation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, libelleEquipement, hauteur, largeur, position_x, position_y, murNavigation, salleNavigation, typeEquipementNavigation);
    }
}
