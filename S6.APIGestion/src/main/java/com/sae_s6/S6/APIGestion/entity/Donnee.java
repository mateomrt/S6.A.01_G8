package com.sae_s6.S6.APIGestion.entity;




import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

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

    @Column(name = "libelle_donnee", nullable = false, length = 100)
    private String libelleDonnee;

    @Column(name = "unite", nullable = false, length = 50)
    private String unite;

    // @OneToMany(mappedBy = "donneeNavigation")
    // private List<TypeCapteurDonnee> typeCapteurDonnees;

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
        if (unite == null) {
            if (other.unite != null)
                return false;
        } else if (!unite.equals(other.unite))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((libelleDonnee == null) ? 0 : libelleDonnee.hashCode());
        result = prime * result + ((unite == null) ? 0 : unite.hashCode());
        return result;
    }

}
