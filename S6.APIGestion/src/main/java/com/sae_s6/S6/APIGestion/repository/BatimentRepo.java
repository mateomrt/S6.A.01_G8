package com.sae_s6.S6.APIGestion.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.sae_s6.S6.APIGestion.entity.Batiment;

/**
 * Interface de gestion des opérations CRUD pour l'entité Batiment.
 * Cette interface étend JpaRepository, qui fournit des méthodes prêtes à l'emploi pour interagir avec la base de données.
 */
@Repository
public interface BatimentRepo extends JpaRepository<Batiment, Integer> {   
}