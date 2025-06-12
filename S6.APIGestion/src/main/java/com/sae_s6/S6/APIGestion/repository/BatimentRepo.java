package com.sae_s6.S6.APIGestion.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.sae_s6.S6.APIGestion.entity.Batiment;

@Repository
public interface BatimentRepo extends JpaRepository<Batiment, Integer> {   
}
