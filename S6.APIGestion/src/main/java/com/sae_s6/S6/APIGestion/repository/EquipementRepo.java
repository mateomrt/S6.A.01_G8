package com.sae_s6.S6.APIGestion.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sae_s6.S6.APIGestion.entity.Equipement;

/**
 * Interface de gestion des opérations CRUD pour l'entité Equipement.
 * Cette interface étend JpaRepository, qui fournit des méthodes prêtes à l'emploi pour interagir avec la base de données.
 */
public interface EquipementRepo extends JpaRepository<Equipement, Integer> {

    /**
     * Recherche les équipements dont le libellé contient une chaîne de caractères donnée,
     * en ignorant la casse.
     *
     * @param libelleEquipement Le libellé (ou partie de libellé) à rechercher.
     * @return Une liste d'équipements correspondant au critère de recherche.
     */
    List<Equipement> findByLibelleEquipementContainingIgnoreCase(String libelleEquipement);
}