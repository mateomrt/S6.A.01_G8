package com.sae_s6.S6.APIGestion.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sae_s6.S6.APIGestion.entity.TypeEquipement;

/**
 * Interface de gestion des opérations CRUD pour l'entité TypeEquipement.
 * Cette interface étend JpaRepository, qui fournit des méthodes prêtes à l'emploi pour interagir avec la base de données.
 */
public interface TypeEquipementRepo extends JpaRepository<TypeEquipement, Integer> {
}