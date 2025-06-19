package com.sae_s6.S6.APIGestion.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.sae_s6.S6.APIGestion.entity.Equipement;

/**
 * Interface de gestion des opérations CRUD pour l'entité Equipement.
 * Cette interface étend JpaRepository, qui fournit des méthodes prêtes à l'emploi pour interagir avec la base de données.
 */
public interface EquipementRepo extends JpaRepository<Equipement, Integer> {
}