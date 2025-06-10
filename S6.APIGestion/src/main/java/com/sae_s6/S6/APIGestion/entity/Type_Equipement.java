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
@Table(name = "type_equipement")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Type_Equipement {
    
    @Id
    private Integer id_type_equipement;

    @Column(name = "libelle_type_equipement", nullable = false, length = 250)
    private String libelle_type_equipement;

    @OneToMany(mappedBy = "type_equipement")
    private List<Capteur> capteurs;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Type_Equipement type_equipement)) return false;
        return Objects.equals(id_type_equipement, type_equipement.id_type_equipement)
                && Objects.equals(libelle_type_equipement, type_equipement.libelle_type_equipement);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id_type_equipement, libelle_type_equipement);
    }
}
