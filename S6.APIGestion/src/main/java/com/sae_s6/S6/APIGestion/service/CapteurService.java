package com.sae_s6.S6.APIGestion.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import com.sae_s6.S6.APIGestion.repository.BatimentRepo;
import com.sae_s6.S6.APIGestion.repository.CapteurRepo;
import com.sae_s6.S6.APIGestion.repository.MurRepo;
import com.sae_s6.S6.APIGestion.repository.SalleRepo;
import com.sae_s6.S6.APIGestion.repository.TypeCapteurRepo;
import com.sae_s6.S6.APIGestion.repository.TypeSalleRepo;

import java.util.List;
import java.util.Optional;

import com.sae_s6.S6.APIGestion.entity.Capteur;
import com.sae_s6.S6.APIGestion.entity.Mur;
import com.sae_s6.S6.APIGestion.entity.Salle;
import com.sae_s6.S6.APIGestion.entity.TypeCapteur;

/**
 * Service pour la gestion des capteurs.
 * Fournit des méthodes pour effectuer des opérations CRUD sur les entités Capteur.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class CapteurService {
    private final CapteurRepo capteurRepo;
    private final SalleRepo salleRepo;
    private final TypeCapteurRepo typeCapteurRepo;
    private final MurRepo murRepo;
    private final BatimentRepo batimentRepo;
    private final TypeSalleRepo typeSalleRepo;

    /**
     * Récupère tous les capteurs.
     *
     * @return Une liste de tous les capteurs.
     */
    public List<Capteur> getAllCapteurs() {
        List<Capteur> capteurs = capteurRepo.findAll();
        log.debug("Liste des capteurs récupérés: {}", capteurs);
        return capteurs;
    }

    /**
     * Récupère un capteur par son ID.
     *
     * @param id L'identifiant du capteur.
     * @return Le capteur correspondant ou null s'il n'est pas trouvé.
     */
    public Capteur getCapteurById(Integer id) {
        Optional<Capteur> optionalCapteur = capteurRepo.findById(id);
        if (optionalCapteur.isPresent()) {
            log.debug("Détails du capteur trouvé: {}", optionalCapteur.get());
            return optionalCapteur.get();
        }
        log.warn("Aucun capteur trouvé avec l'id: {}", id);
        return null;
    }

    /**
     * Enregistre un nouveau capteur.
     * Hydrate les relations associées avant de sauvegarder.
     *
     * @param capteur L'entité Capteur à enregistrer.
     * @return Le capteur enregistré.
     */
    public Capteur saveCapteur(Capteur capteur) {
        // Hydrater la salle liée
        Salle salle = salleRepo.findById(capteur.getSalleNavigation().getId())
            .orElseThrow(() -> new RuntimeException("Salle non trouvée"));
        salle.setBatimentNavigation(
            batimentRepo.findById(salle.getBatimentNavigation().getId())
                .orElseThrow(() -> new RuntimeException("Bâtiment non trouvé"))
        );
        salle.setTypeSalleNavigation(
            typeSalleRepo.findById(salle.getTypeSalleNavigation().getId())
                .orElseThrow(() -> new RuntimeException("Type de salle non trouvé"))
        );
        capteur.setSalleNavigation(salle);

        // Hydrater le mur lié
        Mur mur = murRepo.findById(capteur.getMurNavigation().getId())
            .orElseThrow(() -> new RuntimeException("Mur non trouvé"));

        // Hydrater la salle du mur (optionnel selon besoin)
        Salle salleMur = salleRepo.findById(mur.getSalleNavigation().getId())
            .orElseThrow(() -> new RuntimeException("Salle du mur non trouvée"));
        salleMur.setBatimentNavigation(
            batimentRepo.findById(salleMur.getBatimentNavigation().getId())
                .orElseThrow(() -> new RuntimeException("Bâtiment non trouvé"))
        );
        salleMur.setTypeSalleNavigation(
            typeSalleRepo.findById(salleMur.getTypeSalleNavigation().getId())
                .orElseThrow(() -> new RuntimeException("Type salle non trouvé"))
        );
        mur.setSalleNavigation(salleMur);

        capteur.setMurNavigation(mur);

        // Hydrater le type de capteur
        TypeCapteur typeCapteur = typeCapteurRepo.findById(capteur.getTypeCapteurNavigation().getId())
            .orElseThrow(() -> new RuntimeException("Type de capteur non trouvé"));
        capteur.setTypeCapteurNavigation(typeCapteur);

        return capteurRepo.save(capteur);
    }

    /**
     * Met à jour un capteur existant.
     *
     * @param capteur L'entité Capteur avec les nouvelles données.
     * @return Le capteur mis à jour.
     */
    public Capteur updateCapteur(Capteur capteur) {
        Capteur updatedCapteur = capteurRepo.save(capteur);
        log.info("Capteur mis à jour avec succès avec l'id: {}", updatedCapteur.getId());
        log.debug("Détails du capteur après mise à jour: {}", updatedCapteur);
        return updatedCapteur;
    }

    /**
     * Supprime un capteur par son ID.
     *
     * @param id L'identifiant du capteur à supprimer.
     */
    public void deleteCapteurById(Integer id) {
        capteurRepo.deleteById(id);
        log.debug("Capteur avec id: {} supprimé avec succès", id);
    }

    /**
     * Recherche les capteurs dont le libellé contient une chaîne de caractères donnée, sans tenir compte de la casse.
     *
     * @param libelleCapteur La chaîne de caractères à rechercher.
     * @return Une liste de capteurs correspondant à la recherche.
     */
    public List<Capteur> getByLibelleCapteurContainingIgnoreCase(String libelleCapteur) {
        return capteurRepo.findByLibelleCapteurContainingIgnoreCase(libelleCapteur);
    }
}