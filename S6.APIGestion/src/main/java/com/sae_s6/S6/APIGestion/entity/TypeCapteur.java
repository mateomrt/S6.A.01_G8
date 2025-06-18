package com.sae_s6.S6.APIGestion.entity;

import java.util.List;
import java.util.Objects;

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
@Table(name = "typecapteur")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TypeCapteur {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "libelle_typecapteur", nullable = false, length = 100)
    private String libelleTypeCapteur;

    @Column(name = "mode", nullable = false, length = 50)
    private String modeTypeCapteur;

    @OneToMany(mappedBy = "typeCapteurNavigation")
    private List<Capteur> capteurs;

    @OneToMany(mappedBy = "typeCapteurNavigation")
    private List<TypeCapteurDonnee> typeCapteurDonnees;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TypeCapteur typeCapteur)) return false;
        return Objects.equals(id, typeCapteur.id) &&
               Objects.equals(libelleTypeCapteur, typeCapteur.libelleTypeCapteur) &&
               Objects.equals(modeTypeCapteur, typeCapteur.modeTypeCapteur);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, libelleTypeCapteur, modeTypeCapteur);
    }
}