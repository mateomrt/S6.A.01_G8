package com.sae_s6.S6.APIGestion.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.sae_s6.S6.APIGestion.entity.Donnee;
import com.sae_s6.S6.APIGestion.repository.DonneeRepo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Service pour la gestion des données.
 * Fournit des méthodes pour effectuer des opérations CRUD sur les entités Donnee.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class DonneeService {

    private final DonneeRepo donneeRepo;

    /**
     * Récupère toutes les données.
     *
     * @return Une liste de toutes les données.
     */
    public List<Donnee> getAllDonnees() {
        List<Donnee> donnees = donneeRepo.findAll();
        log.debug("Liste des données récupérées: {}", donnees);
        return donnees;
    }

    /**
     * Récupère une donnée par son ID.
     *
     * @param id L'identifiant de la donnée.
     * @return La donnée correspondante ou null si elle n'est pas trouvée.
     */
    public Donnee getDonneeById(Integer id) {
        Optional<Donnee> optionalDonnee = donneeRepo.findById(id);
        if (optionalDonnee.isPresent()) {
            log.debug("Détails de la donnée trouvée: {}", optionalDonnee.get());
            return optionalDonnee.get();
        }
        log.warn("Aucune donnée trouvée avec l'id: {}", id);
        return null;
    }

    /**
     * Enregistre une nouvelle donnée.
     *
     * @param donnee L'entité Donnee à enregistrer.
     * @return La donnée enregistrée.
     */
    public Donnee saveDonnee(Donnee donnee) {
        Donnee savedDonnee = donneeRepo.save(donnee);
        log.info("Donnée sauvegardée avec succès avec l'id: {}", savedDonnee.getId());
        log.debug("Détails de la donnée sauvegardée: {}", savedDonnee);
        return savedDonnee;
    }

    /**
     * Met à jour une donnée existante.
     *
     * @param donnee L'entité Donnee avec les nouvelles données.
     * @return La donnée mise à jour.
     */
    public Donnee updateDonnee(Donnee donnee) {
        Donnee updatedDonnee = donneeRepo.save(donnee);
        log.info("Donnée mise à jour avec succès avec l'id: {}", updatedDonnee.getId());
        log.debug("Détails de la donnée après mise à jour: {}", updatedDonnee);
        return updatedDonnee;
    }

    /**
     * Supprime une donnée par son ID.
     *
     * @param id L'identifiant de la donnée à supprimer.
     */
    public void deleteDonneeById(Integer id) {
        donneeRepo.deleteById(id);
        log.debug("Donnée avec id: {} supprimée avec succès", id);
    }

    /**
     * Recherche les données dont le libellé contient une chaîne de caractères donnée, sans tenir compte de la casse.
     *
     * @param libelleDonnee La chaîne de caractères à rechercher.
     * @return Une liste de données correspondant à la recherche.
     */
    public List<Donnee> getByLibelleDonneeContainingIgnoreCase(String libelleDonnee) {
        return donneeRepo.findByLibelleDonneeContainingIgnoreCase(libelleDonnee);
    }
}