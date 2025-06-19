package com.sae_s6.S6.APIGestion.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import com.sae_s6.S6.APIGestion.repository.BatimentRepo;
import com.sae_s6.S6.APIGestion.repository.MurRepo;
import com.sae_s6.S6.APIGestion.repository.SalleRepo;
import com.sae_s6.S6.APIGestion.repository.TypeSalleRepo;

import java.util.List;
import java.util.Optional;

import com.sae_s6.S6.APIGestion.entity.Batiment;
import com.sae_s6.S6.APIGestion.entity.Mur;
import com.sae_s6.S6.APIGestion.entity.TypeSalle;
import com.sae_s6.S6.APIGestion.entity.Salle;

/**
 * Service pour la gestion des murs.
 * Fournit des méthodes pour effectuer des opérations CRUD sur les entités Mur.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class MurService {
    private final MurRepo murRepo;
    private final SalleRepo salleRepo;
    private final BatimentRepo batimentRepo;
    private final TypeSalleRepo typeSalleRepo;

    /**
     * Récupère tous les murs.
     *
     * @return Une liste de tous les murs.
     */
    public List<Mur> getAllMurs() {
        List<Mur> murs = murRepo.findAll();
        log.debug("Liste des murs récupérés: {}", murs);
        return murs;
    }

    /**
     * Récupère un mur par son ID.
     *
     * @param id L'identifiant du mur.
     * @return Le mur correspondant ou null s'il n'est pas trouvé.
     */
    public Mur getMurById(Integer id) {
        Optional<Mur> optionalMur = murRepo.findById(id);
        if (optionalMur.isPresent()) {
            log.debug("Détails du mur trouvé: {}", optionalMur.get());
            return optionalMur.get();
        }
        log.warn("Aucun mur trouvé avec l'id: {}", id);
        return null;
    }

    /**
     * Enregistre un nouveau mur.
     * Hydrate les relations associées avant de sauvegarder.
     *
     * @param mur L'entité Mur à enregistrer.
     * @return Le mur enregistré.
     */
    public Mur saveMur(Mur mur) {
        Salle salleInput = mur.getSalleNavigation();
        Salle salle = salleRepo.findById(salleInput.getId())
            .orElseThrow(() -> new RuntimeException("Salle non trouvée"));

        Batiment batiment = batimentRepo.findById(salleInput.getBatimentNavigation().getId())
            .orElseThrow(() -> new RuntimeException("Batiment non trouvé"));

        TypeSalle typeSalle = typeSalleRepo.findById(salleInput.getTypeSalleNavigation().getId())
            .orElseThrow(() -> new RuntimeException("Type de salle non trouvé"));

        salle.setBatimentNavigation(batiment);
        salle.setTypeSalleNavigation(typeSalle);

        mur.setSalleNavigation(salle);
        return murRepo.save(mur);
    }

    /**
     * Met à jour un mur existant.
     *
     * @param mur L'entité Mur avec les nouvelles données.
     * @return Le mur mis à jour.
     */
    public Mur updateMur(Mur mur) {
        Mur updatedMur = murRepo.save(mur);
        log.info("Mur mis à jour avec succès avec l'id: {}", updatedMur.getId());
        log.debug("Détails du mur après mise à jour: {}", updatedMur);
        return updatedMur;
    }

    /**
     * Supprime un mur par son ID.
     *
     * @param id L'identifiant du mur à supprimer.
     */
    public void deleteMurById(Integer id) {
        murRepo.deleteById(id);
        log.debug("Mur avec id: {} supprimé avec succès", id);
    }

    /**
     * Recherche les murs dont le libellé contient une chaîne de caractères donnée, sans tenir compte de la casse.
     *
     * @param libelleSalle La chaîne de caractères à rechercher dans le libellé des murs.
     * @return Une liste de murs correspondant à la recherche.
     */
    public List<Mur> getByLibelleMurContainingIgnoreCase(String libelleSalle) {
        return murRepo.findByLibelleMurContainingIgnoreCase(libelleSalle);
    }
}