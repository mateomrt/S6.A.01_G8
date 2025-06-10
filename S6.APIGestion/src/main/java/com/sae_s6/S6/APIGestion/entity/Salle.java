// package com.sae_s6.S6.APIGestion.entity;


// import java.util.List;
// import java.util.Objects;

// import jakarta.persistence.Column;
// import jakarta.persistence.Entity;
// import jakarta.persistence.Id;
// import jakarta.persistence.JoinColumn;
// import jakarta.persistence.ManyToOne;
// import jakarta.persistence.OneToMany;
// import jakarta.persistence.Table;
// import lombok.AllArgsConstructor;
// import lombok.Data;
// import lombok.NoArgsConstructor;

// @Entity
// @Table(name = "salle")
// @Data
// @NoArgsConstructor
// @AllArgsConstructor
// public class Salle {


//     @Id
//     private Integer id;

//     @Column(name = "titre", nullable = false, length = 250)
//     private String titre;

//     @Column(name = "superficie", nullable = false)
//     private Double superficie;

//     @ManyToOne
//     @JoinColumn(name = "batiment_id", nullable = false)
//     private Batiment batiment;

//     @ManyToOne
//     @JoinColumn(name = "typesalle_id", nullable = false)
//     private TypeSalle typeSalle;

//     @OneToMany(mappedBy = "salle")
//     private List<Mur> murs;

//     @OneToMany(mappedBy = "salle")
//     private List<Equipement> equipements;

//     @OneToMany(mappedBy = "salle")
//     private List<Capteur> capteurs;

//     @Override
//     public boolean equals(Object o) {
//         if (this == o) return true;
//         if (!(o instanceof Salle salle)) return false;
//         return Objects.equals(id, salle.id)
//                 && Objects.equals(titre, salle.titre)
//                 && Objects.equals(superficie, salle.superficie)
//                 && Objects.equals(batiment, salle.batiment)
//                 && Objects.equals(typeSalle, salle.typeSalle);
//     }

//     @Override
//     public int hashCode() {
//         return Objects.hash(id, titre, superficie, batiment, typeSalle);
//     }

// }
