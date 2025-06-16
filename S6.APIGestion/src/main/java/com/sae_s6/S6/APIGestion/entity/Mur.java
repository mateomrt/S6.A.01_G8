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
@Table(name = "mur")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Mur {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "titre", nullable = false, length = 250)
    private String titre;

    @Column(name = "hauteur", nullable = false)
    private Integer hauteur;

    @Column(name = "longueur", nullable = false)
    private Integer longueur;

    @Column(name = "orientation", nullable = false)
    private Integer orientation;

    @ManyToOne
    @JoinColumn(name = "salle_id", nullable = false)
    private Salle salle;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Mur mur)) return false;
        return Objects.equals(id, mur.id) &&
               Objects.equals(titre, mur.titre) &&
               Objects.equals(hauteur, mur.hauteur) &&
               Objects.equals(longueur, mur.longueur) &&
               Objects.equals(orientation, mur.orientation) &&
               Objects.equals(salle, mur.salle);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, titre, hauteur, longueur, orientation, salle);
    
    }
}
