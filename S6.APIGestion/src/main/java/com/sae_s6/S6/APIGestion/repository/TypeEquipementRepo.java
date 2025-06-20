package com.sae_s6.S6.APIGestion.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sae_s6.S6.APIGestion.entity.TypeEquipement;

/**
 * Interface de gestion des opérations CRUD pour l'entité TypeEquipement.
 * Cette interface étend JpaRepository, qui fournit des méthodes prêtes à l'emploi pour interagir avec la base de données.
 */
public interface TypeEquipementRepo extends JpaRepository<TypeEquipement, Integer> {
    /**
     * Recherche les types d'équipement dont le libellé contient une chaîne de caractères donnée, sans tenir compte de la casse.
     *
     * @param libelleTypeEquipemnt La chaîne de caractères à rechercher dans le libellé des types d'équipement.
     * @return Une liste de types d'équipement correspondant à la recherche.
     */
    List<TypeEquipement> findByLibelleTypeEquipementContainingIgnoreCase(String libelleTypeEquipement);

}
