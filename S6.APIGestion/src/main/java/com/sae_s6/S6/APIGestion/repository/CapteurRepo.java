package com.sae_s6.S6.APIGestion.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.sae_s6.S6.APIGestion.entity.Capteur;

/**
 * Interface de gestion des opérations CRUD pour l'entité Capteur.
 * Cette interface étend JpaRepository, qui fournit des méthodes prêtes à l'emploi pour interagir avec la base de données.
 */
public interface CapteurRepo extends JpaRepository<Capteur, Integer>{
    
    List<Capteur> findByLibelleCapteurContainingIgnoreCase(String libelleCapteur);

}