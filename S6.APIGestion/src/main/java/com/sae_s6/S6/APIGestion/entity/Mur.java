package com.sae_s6.S6.APIGestion.entity;


import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.EnumType;



@Entity
@Table(name = "mur")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Mur {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "libelle_mur", nullable = false, length = 75)
    private String libelleMur;

    @Column(name = "hauteur", nullable = false)
    private Double hauteur;

    @Column(name = "longueur", nullable = false)
    private Double longueur;

    @Enumerated(EnumType.STRING) // IMPORTANT pour stocker le nom de l'enum dans la colonne et non le numero 
    @Column(name = "orientation", nullable = false)
    private Orientation  orientation;

    @ManyToOne
    @JoinColumn(name = "salle_id", referencedColumnName = "id", nullable = false)
    private Salle salleNavigation;

    // @OneToMany(mappedBy = "murNavigation")
    // private List<Equipement> equipements;

    // @OneToMany(mappedBy = "murNavigation")
    // private List<Capteur> capteurs;



    
    public enum Orientation {
        N,
        E,
        S,
        O,
        NE,
        NO,
        SE,
        SO,
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Mur mur)) return false;
        return Objects.equals(id, mur.id) &&
               Objects.equals(libelleMur, mur.libelleMur) &&
               Objects.equals(hauteur, mur.hauteur) &&
               Objects.equals(longueur, mur.longueur) &&
               Objects.equals(orientation, mur.orientation) &&
               Objects.equals(salleNavigation, mur.salleNavigation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, libelleMur, hauteur, longueur, orientation, salleNavigation);
    
    }

}
