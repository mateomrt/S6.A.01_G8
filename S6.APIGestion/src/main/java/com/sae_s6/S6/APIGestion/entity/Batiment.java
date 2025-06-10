package com.sae_s6.S6.APIGestion.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "t_e_batiment_bat")
public class Batiment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bat_id")
    private int id;

    @NotNull
    @Size(max = 50)
    @Column(name = "bat_nom", nullable = false, length = 50)
    private String nom;

    @OneToMany(mappedBy = "batiment", cascade = CascadeType.ALL)
    private List<Salle> salles = new ArrayList<>();

    // Getters & setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public List<Salle> getSalles() {
        return salles;
    }

    public void setSalles(List<Salle> salles) {
        this.salles = salles;
    }
}
