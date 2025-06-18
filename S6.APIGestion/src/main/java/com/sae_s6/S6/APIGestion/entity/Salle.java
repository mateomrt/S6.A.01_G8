package com.sae_s6.S6.APIGestion.entity;


import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "salle")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Salle {


    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "libelle_salle", nullable = true, length = 50)
    private String libelleSalle;

    @Column(name = "superficie", nullable = true)
    private Double superficie;

    @ManyToOne
    @JoinColumn(name = "batiment_id", referencedColumnName = "id" , nullable = true)
    private Batiment batimentNavigation;

    @ManyToOne
    @JoinColumn(name = "typesalle_id", referencedColumnName = "id", nullable = true)
    private TypeSalle typeSalleNavigation;

    // @OneToMany(mappedBy = "salleNavigation")
    // private List<Mur> murs;

    // @OneToMany(mappedBy = "salleNavigation")
    // private List<Equipement> equipements;

    // @OneToMany(mappedBy = "salleNavigation")
    // private List<Capteur> capteurs;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Salle salle)) return false;
        return Objects.equals(id, salle.id)
                && Objects.equals(libelleSalle, salle.libelleSalle)
                && Objects.equals(superficie, salle.superficie)
                && Objects.equals(batimentNavigation, salle.batimentNavigation)
                && Objects.equals(typeSalleNavigation, salle.typeSalleNavigation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, libelleSalle, superficie, batimentNavigation, typeSalleNavigation);
    }

}
