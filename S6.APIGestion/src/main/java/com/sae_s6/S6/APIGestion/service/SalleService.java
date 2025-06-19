package com.sae_s6.S6.APIGestion.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import com.sae_s6.S6.APIGestion.repository.SalleRepo;
import com.sae_s6.S6.APIGestion.repository.BatimentRepo;
import com.sae_s6.S6.APIGestion.repository.TypeSalleRepo;
import java.util.List;
import java.util.Optional;

import com.sae_s6.S6.APIGestion.entity.Batiment;
import com.sae_s6.S6.APIGestion.entity.Salle;
import com.sae_s6.S6.APIGestion.entity.TypeSalle;

/**
 * Service pour la gestion des salles.
 * Fournit des méthodes pour effectuer des opérations CRUD sur les entités Salle.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class SalleService {

    private final SalleRepo salleRepo;
    private final BatimentRepo batimentRepo;
    private final TypeSalleRepo typeSalleRepo;

    /**
     * Récupère toutes les salles.
     *
     * @return Une liste de toutes les salles.
     */
    public List<Salle> getAllSalles() {
        List<Salle> salles = salleRepo.findAll();
        log.debug("Liste des salles récupérées: {}", salles);
        return salles;
    }

    /**
     * Récupère une salle par son ID.
     *
     * @param id L'identifiant de la salle.
     * @return La salle correspondante ou null si elle n'est pas trouvée.
     */
    public Salle getSalleById(Integer id) {
        Optional<Salle> optionalSalle = salleRepo.findById(id);
        if (optionalSalle.isPresent()) {
            log.debug("Détails de la salle trouvée: {}", optionalSalle.get());
            return optionalSalle.get();
        }
        log.warn("Aucune salle trouvée avec l'id: {}", id);
        return null;
    }

    /**
     * Enregistre une nouvelle salle.
     * Hydrate les relations associées avant de sauvegarder.
     *
     * @param salle L'entité Salle à enregistrer.
     * @return La salle enregistrée.
     */
    public Salle saveSalle(Salle salle) {
        // Hydrater le bâtiment lié
        Batiment batiment = batimentRepo.findById(salle.getBatimentNavigation().getId())
            .orElseThrow(() -> new RuntimeException("Bâtiment non trouvé"));

        // Hydrater le type de salle lié
        TypeSalle typeSalle = typeSalleRepo.findById(salle.getTypeSalleNavigation().getId())
            .orElseThrow(() -> new RuntimeException("Type de salle non trouvé"));

        salle.setBatimentNavigation(batiment);
        salle.setTypeSalleNavigation(typeSalle);

        return salleRepo.save(salle);
    }

    /**
     * Met à jour une salle existante.
     *
     * @param salle L'entité Salle avec les nouvelles données.
     * @return La salle mise à jour.
     */
    public Salle updateSalle(Salle salle) {
        Salle updatedSalle = salleRepo.save(salle);
        log.info("Salle mise à jour avec succès avec l'id: {}", updatedSalle.getId());
        log.debug("Détails de la salle après mise à jour: {}", updatedSalle);
        return updatedSalle;
    }

    /**
     * Supprime une salle par son ID.
     *
     * @param id L'identifiant de la salle à supprimer.
     */
    public void deleteSalleById(Integer id) {
        salleRepo.deleteById(id);
        log.debug("Salle avec id: {} supprimée avec succès", id);
    }

    /**
     * Recherche les salles par leur libellé exact.
     *
     * @param libelleSalle Le libellé exact à rechercher.
     * @return Une liste de salles correspondant au libellé donné.
     */
    public List<Salle> getSallesByLibelleSalle(String libelleSalle) {
        return salleRepo.findByLibelleSalle(libelleSalle);
    }

    /**
     * Recherche les salles dont le libellé contient une chaîne de caractères donnée, sans tenir compte de la casse.
     *
     * @param libelleSalle La chaîne de caractères à rechercher dans le libellé des salles.
     * @return Une liste de salles correspondant à la recherche.
     */
    public List<Salle> getByLibelleSalleContainingIgnoreCase(String libelleSalle) {
        return salleRepo.findByLibelleSalleContainingIgnoreCase(libelleSalle);
    }
}