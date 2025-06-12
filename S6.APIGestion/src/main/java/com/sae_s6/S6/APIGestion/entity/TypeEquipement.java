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
@Table(name = "typeequipement")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TypeEquipement {
    
    @Id
    @Column(name = "id")
    private Integer idTypeEquipement;

    @Column(name = "titre", nullable = false, length = 250)
    private String titre;

    @OneToMany(mappedBy = "type_equipement")
    private List<Capteur> capteurs;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TypeEquipement type_equipement)) return false;
        return Objects.equals(idTypeEquipement, type_equipement.idTypeEquipement)
                && Objects.equals(titre, type_equipement.titre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idTypeEquipement, titre);
    }
}
