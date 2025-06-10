package com.sae_s6.S6.APIGestion.entity;

import java.util.List;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "typesalle")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TypeSalle {

    @Id
    @Column(name = "id")
    private Integer idTypeSalle;

    @Column(name = "libelle", nullable = false)
    private String libelleTypeSalle;

    @OneToMany(mappedBy = "typeSalle")
    private List<Salle> salles;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TypeSalle typeSalle)) return false;
        return Objects.equals(idTypeSalle, typeSalle.idTypeSalle) && 
               Objects.equals(libelleTypeSalle, typeSalle.libelleTypeSalle);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idTypeSalle, libelleTypeSalle);
    }
}