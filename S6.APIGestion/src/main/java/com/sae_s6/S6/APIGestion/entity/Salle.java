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

/**
 * Représente une salle dans le système.
 * Cette classe est une entité JPA associée à la table "salle" dans la base de données.
 */
@Entity
@Table(name = "salle")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Salle {

    /**
     * Identifiant unique de la salle.
     * Généré automatiquement par la base de données.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * Libellé de la salle.
     * Ce champ est optionnel et sa longueur maximale est de 50 caractères.
     */
    @Column(name = "libelle_salle", nullable = true, length = 50)
    private String libelleSalle;

    /**
     * Superficie de la salle.
     * Ce champ est optionnel et représente la superficie en unités.
     */
    @Column(name = "superficie", nullable = true)
    private Double superficie;

    /**
     * Référence au bâtiment associé à la salle.
     * Ce champ est optionnel et représente une relation Many-to-One avec l'entité Batiment.
     */
    @ManyToOne
    @JoinColumn(name = "batiment_id", referencedColumnName = "id", nullable = true)
    private Batiment batimentNavigation;

    /**
     * Référence au type de salle.
     * Ce champ est optionnel et représente une relation Many-to-One avec l'entité TypeSalle.
     */
    @ManyToOne
    @JoinColumn(name = "typesalle_id", referencedColumnName = "id", nullable = true)
    private TypeSalle typeSalleNavigation;

    /**
     * Vérifie si deux objets Salle sont égaux.
     * Deux salles sont considérées égales si elles ont le même identifiant, libellé, superficie, bâtiment et type.
     *
     * @param o L'objet à comparer.
     * @return true si les objets sont égaux, false sinon.
     */
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

    /**
     * Calcule le hash code de l'objet Salle.
     * Utilise les champs id, libellé, superficie, bâtiment et type pour le calcul.
     *
     * @return Le hash code de l'objet.
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, libelleSalle, superficie, batimentNavigation, typeSalleNavigation);
    }

    /**
     * Retourne la description de la salle.
     * Actuellement, cela correspond au libellé de la salle.
     *
     * @return Le libellé de la salle.
     */
    public String getDesc() {
        return libelleSalle;
    }
}