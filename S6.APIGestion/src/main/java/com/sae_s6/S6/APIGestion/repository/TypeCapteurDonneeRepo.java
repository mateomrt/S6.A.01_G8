package com.sae_s6.S6.APIGestion.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sae_s6.S6.APIGestion.entity.TypeCapteurDonnee;
import com.sae_s6.S6.APIGestion.entity.TypeCapteurDonneeEmbedId;

/**
 * Interface de gestion des opérations CRUD pour l'entité TypeCapteurDonnee.
 * Cette interface étend JpaRepository, qui fournit des méthodes prêtes à l'emploi pour interagir avec la base de données.
 * La clé primaire utilisée est une clé composite définie par TypeCapteurDonneeEmbedId.
 */
public interface TypeCapteurDonneeRepo extends JpaRepository<TypeCapteurDonnee, TypeCapteurDonneeEmbedId> {

    // Des méthodes de requête supplémentaires peuvent être définies ici si nécessaire

}