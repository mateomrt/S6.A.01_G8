package com.sae_s6.S6.APIGestion.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sae_s6.S6.APIGestion.entity.Donnee;


public interface DonneeRepo extends JpaRepository<Donnee, Integer> {
    
}
