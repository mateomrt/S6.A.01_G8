package com.sae_s6.S6.APIGestion.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.sae_s6.S6.APIGestion.entity.Donnee;

/**
 * Interface de gestion des opérations CRUD pour l'entité Donnee.
 * Cette interface étend JpaRepository, qui fournit des méthodes prêtes à l'emploi pour interagir avec la base de données.
 */
public interface DonneeRepo extends JpaRepository<Donnee, Integer> {

    /**
     * Recherche les données dont le libellé contient une chaîne de caractères donnée,
     * en ignorant la casse.
     *
     * @param libelleDonnee Le libellé (ou partie de libellé) à rechercher.
     * @return Une liste de données correspondant au critère de recherche.
     */
    List<Donnee> findByLibelleDonneeContainingIgnoreCase(String libelleDonnee);
}