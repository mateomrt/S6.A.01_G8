package com.sae_s6.S6.APIGestion.entity;

import java.util.List;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
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
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "titre", nullable = false)
    private String titre;

    @OneToMany
    @JoinColumn(name = "capteur_id")
    private List<Capteur> capteurs;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TypeEquipement type_equipement)) return false;
        return Objects.equals(id, type_equipement.id)
                && Objects.equals(titre, type_equipement.titre);
        }

    @Override
    public int hashCode() {
        return Objects.hash(id, titre);
    }
}
