package com.sae_s6.S6.APIGestion.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "batiment")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Batiment {

    @Id
    private Integer id;

    @Column(name = "titre", nullable = false, length = 100)
    private String titre;

    @OneToMany(mappedBy = "batiment")
    private List<Salle> salles;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Batiment that)) return false;
        return Objects.equals(id, that.id) && Objects.equals(titre, that.titre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, titre);
    }
}
