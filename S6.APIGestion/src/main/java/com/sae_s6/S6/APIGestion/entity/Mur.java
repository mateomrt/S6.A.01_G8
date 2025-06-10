package com.sae_s6.S6.APIGestion.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Entity
@Table(name = "mur")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Mur {

    @Id
    private Integer id;

    @Column(name = "orientation", nullable = false, length = 50)
    private String orientation;

    @Column(name = "largeur", nullable = false)
    private Double largeur;

    @ManyToOne
    @JoinColumn(name = "salle_id", nullable = false)
    private Salle salle;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Mur mur)) return false;
        return Objects.equals(id, mur.id) && Objects.equals(orientation, mur.orientation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, orientation);
    }
}
