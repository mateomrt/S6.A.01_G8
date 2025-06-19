package com.sae_s6.S6.APIGestion.repository;

import org.springframework.stereotype.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.sae_s6.S6.APIGestion.entity.Mur;

/**
 * Interface de gestion des opérations CRUD pour l'entité Mur.
 * Cette interface étend JpaRepository, qui fournit des méthodes prêtes à l'emploi pour interagir avec la base de données.
 */
@Repository
public interface MurRepo extends JpaRepository<Mur, Integer> {

    /**
     * Recherche les murs dont le libellé contient une chaîne de caractères donnée, sans tenir compte de la casse.
     *
     * @param mur La chaîne de caractères à rechercher dans le libellé des murs.
     * @return Une liste de murs correspondant à la recherche.
     */
    List<Mur> findByLibelleMurContainingIgnoreCase(String mur);
}