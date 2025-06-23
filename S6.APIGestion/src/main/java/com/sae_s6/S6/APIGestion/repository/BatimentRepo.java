package com.sae_s6.S6.APIGestion.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.sae_s6.S6.APIGestion.entity.Batiment;

/**
 * Interface de gestion des opérations CRUD pour l'entité Batiment.
 * Cette interface étend JpaRepository, qui fournit des méthodes prêtes à l'emploi pour interagir avec la base de données.
 */
public interface BatimentRepo extends JpaRepository<Batiment, Integer> {   
    
    /**
     * Recherche les bâtiments dont le libellé contient une chaîne de caractères donnée, 
     * en ignorant la casse.
     *
     * @param libelleBatiment Le libellé (ou partie de libellé) à rechercher.
     * @return Une liste de bâtiments correspondant au critère de recherche.
     */
    List<Batiment> findByLibelleBatimentContainingIgnoreCase(String libelleBatiment);
}