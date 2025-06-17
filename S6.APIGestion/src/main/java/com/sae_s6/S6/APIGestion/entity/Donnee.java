package com.sae_s6.S6.APIGestion.entity;


import java.util.List;

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
@Table(name = "donnee")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Donnee {


    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "libelle_donnee", nullable = false, length = 50)
    private String libelleDonnee;

    @Column(name = "mode", nullable = false, length = 25)
    private String mode;

    @OneToMany(mappedBy = "donneeNavigation")
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
        if (libelleDonnee == null) {
            if (other.libelleDonnee != null)
                return false;
        } else if (!libelleDonnee.equals(other.libelleDonnee))
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
        result = prime * result + ((libelleDonnee == null) ? 0 : libelleDonnee.hashCode());
        result = prime * result + ((mode == null) ? 0 : mode.hashCode());
        result = prime * result + ((typeCapteurDonnees == null) ? 0 : typeCapteurDonnees.hashCode());
        return result;
    }

}
