package com.sae_s6.S6.APIGestion.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sae_s6.S6.APIGestion.entity.TypeCapteur;

/**
 * Interface de gestion des opérations CRUD pour l'entité TypeCapteur.
 * Cette interface étend JpaRepository, qui fournit des méthodes prêtes à l'emploi pour interagir avec la base de données.
 */
public interface TypeCapteurRepo extends JpaRepository<TypeCapteur, Integer> {
    
    /**
     * Recherche les types de capteurs dont le libellé contient une chaîne de caractères donnée,
     * en ignorant la casse.
     *
     * @param libelleTypeCapteur Le libellé (ou partie de libellé) à rechercher.
     * @return Une liste de types de capteurs correspondant au critère de recherche.
     */
    List<TypeCapteur> findByLibelleTypeCapteurContainingIgnoreCase(String libelleTypeCapteur);
}