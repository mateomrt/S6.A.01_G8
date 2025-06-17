package com.sae_s6.S6.APIGestion.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sae_s6.S6.APIGestion.entity.TypeCapteurDonnee;
import com.sae_s6.S6.APIGestion.entity.TypeCapteurDonneeEmbedId;


public interface TypeCapteurDonneeRepo extends JpaRepository<TypeCapteurDonnee, TypeCapteurDonneeEmbedId> {

    // Additional query methods can be defined here if needed
    
}
