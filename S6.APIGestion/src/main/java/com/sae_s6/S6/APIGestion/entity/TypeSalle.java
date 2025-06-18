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
@Table(name = "typesalle")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TypeSalle {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "libelle_typesalle", nullable = false, length = 100)
    private String libelleTypeSalle;

    // @OneToMany(mappedBy = "typeSalleNavigation")
    // private List<Salle> salles;

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        TypeSalle other = (TypeSalle) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (libelleTypeSalle == null) {
            if (other.libelleTypeSalle != null)
                return false;
        } else if (!libelleTypeSalle.equals(other.libelleTypeSalle))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((libelleTypeSalle == null) ? 0 : libelleTypeSalle.hashCode());
        return result;
    }

    public String getDesc() {
        return libelleTypeSalle;
    }
}