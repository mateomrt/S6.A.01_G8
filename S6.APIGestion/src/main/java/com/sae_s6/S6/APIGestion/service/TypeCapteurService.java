package com.sae_s6.S6.APIGestion.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import com.sae_s6.S6.APIGestion.repository.TypeCapteurRepo;
import com.sae_s6.S6.APIGestion.entity.TypeCapteur;

/**
 * Service pour la gestion des types de capteurs.
 * Fournit des méthodes pour effectuer des opérations CRUD sur les entités TypeCapteur.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class TypeCapteurService {
    private final TypeCapteurRepo typeCapteurRepo;

    /**
     * Récupère tous les types de capteurs.
     *
     * @return Une liste de tous les types de capteurs.
     */
    public List<TypeCapteur> getAllTypeCapteurs() {
        List<TypeCapteur> typeCapteurs = typeCapteurRepo.findAll();
        log.debug("Liste des types de capteurs récupérés: {}", typeCapteurs);
        return typeCapteurs;
    }

    /**
     * Récupère un type de capteur par son ID.
     *
     * @param id L'identifiant du type de capteur.
     * @return Le type de capteur correspondant ou null s'il n'est pas trouvé.
     */
    public TypeCapteur getTypeCapteurById(Integer id) {
        Optional<TypeCapteur> optionalTypeCapteur = typeCapteurRepo.findById(id);
        if (optionalTypeCapteur.isPresent()) {
            log.debug("Détails du type de capteur trouvé: {}", optionalTypeCapteur.get());
            return optionalTypeCapteur.get();
        }
        log.warn("Aucun type de capteur trouvé avec l'id: {}", id);
        return null;
    }

    /**
     * Enregistre un nouveau type de capteur.
     *
     * @param typeCapteur L'entité TypeCapteur à enregistrer.
     * @return Le type de capteur enregistré.
     */
    public TypeCapteur saveTypeCapteur(TypeCapteur typeCapteur) {
        TypeCapteur savedTypeCapteur = typeCapteurRepo.save(typeCapteur);
        log.info("Type de capteur sauvegardé avec succès avec l'id: {}", savedTypeCapteur.getId());
        log.debug("Détails du type de capteur sauvegardé: {}", savedTypeCapteur);
        return savedTypeCapteur;
    }

    /**
     * Met à jour un type de capteur existant.
     *
     * @param typeCapteur L'entité TypeCapteur avec les nouvelles données.
     * @return Le type de capteur mis à jour.
     */
    public TypeCapteur updateTypeCapteur(TypeCapteur typeCapteur) {
        TypeCapteur updatedTypeCapteur = typeCapteurRepo.save(typeCapteur);
        log.info("Type de capteur mis à jour avec succès avec l'id: {}", updatedTypeCapteur.getId());
        log.debug("Détails du type de capteur après mise à jour: {}", updatedTypeCapteur);
        return updatedTypeCapteur;
    }

    /**
     * Supprime un type de capteur par son ID.
     *
     * @param id L'identifiant du type de capteur à supprimer.
     */
    public void deleteTypeCapteurById(Integer id) {
        typeCapteurRepo.deleteById(id);
        log.debug("Type de capteur avec id: {} supprimé avec succès", id);
    }
}