package com.sae_s6.S6.APIGestion.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import com.sae_s6.S6.APIGestion.repository.TypeEquipementRepo;
import com.sae_s6.S6.APIGestion.entity.TypeEquipement;

/**
 * Service pour la gestion des types d'équipements.
 * Fournit des méthodes pour effectuer des opérations CRUD sur les entités TypeEquipement.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class TypeEquipementService {
    private final TypeEquipementRepo typeEquipementRepo;

    /**
     * Récupère tous les types d'équipements.
     *
     * @return Une liste de tous les types d'équipements.
     */
    public List<TypeEquipement> getAllTypeEquipements() {
        List<TypeEquipement> typeEquipements = typeEquipementRepo.findAll();
        log.debug("Liste des types d'équipements récupérés: {}", typeEquipements);
        return typeEquipements;
    }

    /**
     * Récupère un type d'équipement par son ID.
     *
     * @param id L'identifiant du type d'équipement.
     * @return Le type d'équipement correspondant ou null s'il n'est pas trouvé.
     */
    public TypeEquipement getTypeEquipementById(Integer id) {
        Optional<TypeEquipement> optionalTypeEquipement = typeEquipementRepo.findById(id);
        if (optionalTypeEquipement.isPresent()) {
            log.debug("Détails du type d'équipement trouvé: {}", optionalTypeEquipement.get());
            return optionalTypeEquipement.get();
        }
        log.warn("Aucun type d'équipement trouvé avec l'id: {}", id);
        return null;
    }

    /**
     * Enregistre un nouveau type d'équipement.
     *
     * @param typeEquipement L'entité TypeEquipement à enregistrer.
     * @return Le type d'équipement enregistré.
     */
    public TypeEquipement saveTypeEquipement(TypeEquipement typeEquipement) {
        TypeEquipement savedTypeEquipement = typeEquipementRepo.save(typeEquipement);
        log.info("Type d'équipement sauvegardé avec succès avec l'id: {}", savedTypeEquipement.getId());
        log.debug("Détails du type d'équipement sauvegardé: {}", savedTypeEquipement);
        return savedTypeEquipement;
    }

    /**
     * Met à jour un type d'équipement existant.
     *
     * @param typeEquipement L'entité TypeEquipement avec les nouvelles données.
     * @return Le type d'équipement mis à jour.
     */
    public TypeEquipement updateTypeEquipement(TypeEquipement typeEquipement) {
        TypeEquipement updatedTypeEquipement = typeEquipementRepo.save(typeEquipement);
        log.info("Type d'équipement mis à jour avec succès avec l'id: {}", updatedTypeEquipement.getId());
        log.debug("Détails du type d'équipement après mise à jour: {}", updatedTypeEquipement);
        return updatedTypeEquipement;
    }

    /**
     * Supprime un type d'équipement par son ID.
     *
     * @param id L'identifiant du type d'équipement à supprimer.
     */
    public void deleteTypeEquipementById(Integer id) {
        typeEquipementRepo.deleteById(id);
        log.debug("Type d'équipement avec id: {} supprimé avec succès", id);
    }

        /**
     * Recherche les types d'équipement dont le libellé contient une chaîne de caractères donnée, sans tenir compte de la casse.
     *
     * @param libelleEquipement La chaîne de caractères à rechercher dans le libellé des types d'équipement.
     * @return Une liste de types d'équipement correspondant à la recherche.
     */
    public List<TypeEquipement> getByLibelleTypeEquipementContainingIgnoreCase(String libelleTypeEquipement) {
        return typeEquipementRepo.findByLibelleTypeEquipementContainingIgnoreCase(libelleTypeEquipement);
    }

}