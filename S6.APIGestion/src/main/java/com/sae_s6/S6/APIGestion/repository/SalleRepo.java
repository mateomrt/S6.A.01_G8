package com.sae_s6.S6.APIGestion.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.sae_s6.S6.APIGestion.entity.Salle;

public interface SalleRepo extends JpaRepository<Salle, Integer>  {
    List<Salle> findByLibelleSalle(String libelleSalle);
    List<Salle> findByLibelleSalleContainingIgnoreCase(String libelleSalle);
}
