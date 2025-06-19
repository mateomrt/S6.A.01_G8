package com.sae_s6.S6.APIGestion.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import com.sae_s6.S6.APIGestion.repository.TypeSalleRepo;
import com.sae_s6.S6.APIGestion.entity.TypeSalle;

/**
 * Service pour la gestion des types de salles.
 * Fournit des méthodes pour effectuer des opérations CRUD sur les entités TypeSalle.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class TypeSalleService {

    private final TypeSalleRepo typeSalleRepo;

    /**
     * Récupère tous les types de salles.
     *
     * @return Une liste de tous les types de salles.
     */
    public List<TypeSalle> getAllTypeSalles() {
        List<TypeSalle> typeSalles = typeSalleRepo.findAll();
        log.debug("Liste des types de salles récupérées: {}", typeSalles);
        return typeSalles;
    }

    /**
     * Récupère un type de salle par son ID.
     *
     * @param id L'identifiant du type de salle.
     * @return Le type de salle correspondant ou null s'il n'est pas trouvé.
     */
    public TypeSalle getTypeSalleById(Integer id) {
        Optional<TypeSalle> optionalTypeSalle = typeSalleRepo.findById(id);
        if (optionalTypeSalle.isPresent()) {
            log.debug("Détails du type de salle trouvé: {}", optionalTypeSalle.get());
            return optionalTypeSalle.get();
        }
        log.warn("Aucun type de salle trouvé avec l'id: {}", id);
        return null;
    }

    /**
     * Enregistre un nouveau type de salle.
     *
     * @param typeSalle L'entité TypeSalle à enregistrer.
     * @return Le type de salle enregistré.
     */
    public TypeSalle saveTypeSalle(TypeSalle typeSalle) {
        TypeSalle savedTypeSalle = typeSalleRepo.save(typeSalle);
        log.info("Type de salle sauvegardé avec succès avec l'id: {}", savedTypeSalle.getId());
        log.debug("Détails du type de salle sauvegardé: {}", savedTypeSalle);
        return savedTypeSalle;
    }

    /**
     * Met à jour un type de salle existant.
     *
     * @param typeSalle L'entité TypeSalle avec les nouvelles données.
     * @return Le type de salle mis à jour.
     */
    public TypeSalle updateTypeSalle(TypeSalle typeSalle) {
        TypeSalle updatedTypeSalle = typeSalleRepo.save(typeSalle);
        log.info("Type de salle mis à jour avec succès avec l'id: {}", updatedTypeSalle.getId());
        log.debug("Détails du type de salle après mise à jour: {}", updatedTypeSalle);
        return updatedTypeSalle;
    }

    /**
     * Supprime un type de salle par son ID.
     *
     * @param id L'identifiant du type de salle à supprimer.
     */
    public void deleteTypeSalleById(Integer id) {
        typeSalleRepo.deleteById(id);
        log.debug("Type de salle avec id: {} supprimé avec succès", id);
    }

    /**
     * Recherche les types de salle dont le libellé contient une chaîne de caractères donnée, sans tenir compte de la casse.
     *
     * @param libelleSalle La chaîne de caractères à rechercher dans le libellé des types de salle.
     * @return Une liste de types de salle correspondant à la recherche.
     */
    public List<TypeSalle> getByLibelleTypeSalleContainingIgnoreCase(String libelleSalle) {
        return typeSalleRepo.findByLibelleTypeSalleContainingIgnoreCase(libelleSalle);
    }
}