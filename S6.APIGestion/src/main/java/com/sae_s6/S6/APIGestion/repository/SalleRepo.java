package com.sae_s6.S6.APIGestion.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.sae_s6.S6.APIGestion.entity.Salle;

/**
 * Interface de gestion des opérations CRUD pour l'entité Salle.
 * Cette interface étend JpaRepository, qui fournit des méthodes prêtes à l'emploi pour interagir avec la base de données.
 */
public interface SalleRepo extends JpaRepository<Salle, Integer> {

    /**
     * Recherche les salles dont le libellé correspond exactement à une chaîne de caractères donnée.
     *
     * @param libelleSalle Le libellé exact à rechercher.
     * @return Une liste de salles correspondant au libellé donné.
     */
    List<Salle> findByLibelleSalle(String libelleSalle);

    /**
     * Recherche les salles dont le libellé contient une chaîne de caractères donnée, sans tenir compte de la casse.
     *
     * @param libelleSalle La chaîne de caractères à rechercher dans le libellé des salles.
     * @return Une liste de salles correspondant à la recherche.
     */
    List<Salle> findByLibelleSalleContainingIgnoreCase(String libelleSalle);
}