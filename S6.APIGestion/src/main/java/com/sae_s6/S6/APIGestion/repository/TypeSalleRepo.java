package com.sae_s6.S6.APIGestion.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sae_s6.S6.APIGestion.entity.TypeSalle;

/**
 * Interface de gestion des opérations CRUD pour l'entité TypeSalle.
 * Cette interface étend JpaRepository, qui fournit des méthodes prêtes à l'emploi pour interagir avec la base de données.
 */
public interface TypeSalleRepo extends JpaRepository<TypeSalle, Integer> {

    /**
     * Recherche les types de salle dont le libellé contient une chaîne de caractères donnée, sans tenir compte de la casse.
     *
     * @param libelleTypeSalle La chaîne de caractères à rechercher dans le libellé des types de salle.
     * @return Une liste de types de salle correspondant à la recherche.
     */
    List<TypeSalle> findByLibelleTypeSalleContainingIgnoreCase(String libelleTypeSalle);
}