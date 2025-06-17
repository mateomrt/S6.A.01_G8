package com.sae_s6.S6.APIGestion.entity;

import java.util.List;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "batiment")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Batiment {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "libelle_batiment", nullable = false, length = 75)
    private String libelleBatiment;

    @OneToMany(mappedBy = "batimentNavigation")
    private List<Salle> salles;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Batiment that)) return false;
        return Objects.equals(id, that.id) && Objects.equals(libelleBatiment, that.libelleBatiment);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, libelleBatiment);
    }
}
