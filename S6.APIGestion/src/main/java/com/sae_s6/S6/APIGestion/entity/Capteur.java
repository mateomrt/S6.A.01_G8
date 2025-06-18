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

    @Column(name = "libelle_capteur", nullable = false, length = 100)
    private String libelleCapteur;

    @Column(name = "position_x", nullable = false)
    private Double positionXCapteur;

    @Column(name = "position_y", nullable = false)
    private Double positionYCapteur;

    @ManyToOne
    @JoinColumn(name = "mur_id", referencedColumnName = "id", nullable = false)
    private Mur murNavigation;

    @ManyToOne
    @JoinColumn(name = "salle_id", referencedColumnName = "id", nullable = false)
    private Salle salleNavigation;

    @ManyToOne
    @JoinColumn(name = "typecapteur_id", referencedColumnName = "id", nullable = false)
    private TypeCapteur typeCapteurNavigation;

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