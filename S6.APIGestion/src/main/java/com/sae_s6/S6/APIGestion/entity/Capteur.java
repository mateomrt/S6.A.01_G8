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
@Table(name = "capteur")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Capteur {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "typecapteur_id", nullable = false)
    private Integer idTypeCapteur;

    @Column(name = "salle_id", nullable = false)
    private Integer idSalle;

    @Column(name = "mur_id", nullable = false)
    private Integer idMur;

    @Column(name = "capteur_libelle", nullable = false, length = 64)
    private String libelleCapteur;

    @Column(name = "capteur_x", nullable = false)
    private Integer positionXCapteur;

    @Column(name = "capteur-y", nullable = false)
    private Integer positionYCapteur;

    @ManyToOne
    @JoinColumn(name = "typecapteur_id", referencedColumnName = "id", nullable = false)
    private TypeCapteur typeCapteurNavigation;

    @ManyToOne
    @JoinColumn(name = "mur_id", referencedColumnName = "id", nullable = false)
    private Mur murNavigation;

    @ManyToOne
    @JoinColumn(name = "salle_id", referencedColumnName = "id", nullable = false)
    private Salle salleNavigation;

    

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Capteur capteur)) return false;
        return Objects.equals(id, capteur.id) &&
               Objects.equals(typeCapteurNavigation, capteur.typeCapteurNavigation) &&
               Objects.equals(positionXCapteur, capteur.positionXCapteur) &&
               Objects.equals(positionYCapteur, capteur.positionYCapteur) &&
               Objects.equals(murNavigation, capteur.murNavigation) &&
               Objects.equals(salleNavigation, capteur.salleNavigation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id, 
            typeCapteurNavigation, 
            positionXCapteur, 
            positionYCapteur, 
            murNavigation, 
            salleNavigation
        );
    }
}