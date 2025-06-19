package com.sae_s6.S6.APIGestion.repository;

import org.springframework.stereotype.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.sae_s6.S6.APIGestion.entity.Mur;

@Repository
public interface MurRepo extends JpaRepository<Mur, Integer> {
    List<Mur> findByLibelleMurContainingIgnoreCase(String mur);
}
