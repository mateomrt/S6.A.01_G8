package com.sae_s6.S6.APIGestion.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sae_s6.S6.APIGestion.entity.TypeCapteurDonnee;


public interface TypeCapteurDonneeRepo extends JpaRepository<TypeCapteurDonnee, Integer> {
    
}
