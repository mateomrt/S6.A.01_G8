package com.sae_s6.S6.APIGestion.entity;


import java.util.List;

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
@Table(name = "donnee")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Donnee {


    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "titre", nullable = false, length = 250)
    private String titre;

    @Column(name = "mode", nullable = false, length = 250)
    private String mode;

    @OneToMany
    @JoinColumn(name = "typeCapteurDonnees_id")
    private List<TypeCapteurDonnee> typeCapteurDonnees;

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Donnee other = (Donnee) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (titre == null) {
            if (other.titre != null)
                return false;
        } else if (!titre.equals(other.titre))
            return false;
        if (mode == null) {
            if (other.mode != null)
                return false;
        } else if (!mode.equals(other.mode))
            return false;
        if (typeCapteurDonnees == null) {
            if (other.typeCapteurDonnees != null)
                return false;
        } else if (!typeCapteurDonnees.equals(other.typeCapteurDonnees))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((titre == null) ? 0 : titre.hashCode());
        result = prime * result + ((mode == null) ? 0 : mode.hashCode());
        result = prime * result + ((typeCapteurDonnees == null) ? 0 : typeCapteurDonnees.hashCode());
        return result;
    }

}
