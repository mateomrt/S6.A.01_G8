package com.sae_s6.S6.APIGestion.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sae_s6.S6.APIGestion.entity.TypeSalle;

public interface TypeSalleRepo extends JpaRepository<TypeSalle, Integer>{
    List<TypeSalle> findByLibelleTypeSalleContainingIgnoreCase(String libelleTypeSalle);
}
