package com.sae_s6.S6.APIGestion.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import com.sae_s6.S6.APIGestion.repository.CapteurRepo;
import com.sae_s6.S6.APIGestion.repository.SalleRepo;
import com.sae_s6.S6.APIGestion.repository.TypeCapteurRepo;
import java.util.List;
import java.util.Optional;

import com.sae_s6.S6.APIGestion.entity.Capteur;
import com.sae_s6.S6.APIGestion.entity.Salle;
import com.sae_s6.S6.APIGestion.entity.TypeCapteur;

@Service
@RequiredArgsConstructor
@Slf4j
public class CapteurService {
    private final CapteurRepo capteurRepo;
    private final SalleRepo salleRepo;
    private final TypeCapteurRepo typeCapteurRepo;

    public List<Capteur> getAllCapteurs() {
        List<Capteur> capteurs = capteurRepo.findAll();
        log.debug("Liste des équipements récupérés: {}", capteurs);
        return capteurs;
    }

    /**
     * 
     * @param id
     * @return
     */

    public Capteur getCapteurById(Integer id) {
        Optional<Capteur> optionalCapteur = capteurRepo.findById(id);
        if (optionalCapteur.isPresent()) {
            log.debug("Détails de l'équipement trouvé: {}", optionalCapteur.get());
            return optionalCapteur.get();
        }
        log.warn("Aucun équipement trouvé avec l'id: {}", id);
        return null;
    }

       /**
         * 
         * @param Capteur
         * @return
         */

    public Capteur saveCapteur(Capteur capteur) {
        // Charger les informations liées à partir des IDs
        Salle salle = salleRepo.findById(capteur.getSalleNavigation().getId())
            .orElseThrow(() -> new RuntimeException("Salle non trouvée"));

        TypeCapteur typeCapteur = typeCapteurRepo.findById(capteur.getTypeCapteurNavigation().getId())
            .orElseThrow(() -> new RuntimeException("Type de capteur non trouvé"));

        // Associer les entités liées au capteur
        capteur.setSalleNavigation(salle);
        capteur.setTypeCapteurNavigation(typeCapteur);

        Capteur savedCapteur = capteurRepo.save(capteur);
        log.info("Capteur sauvegardé avec succès avec l'id: {}", savedCapteur.getId());
        log.debug("Détails du capteur sauvegardé: {}", savedCapteur);
        return savedCapteur;
    }

    
    /**
     * 
     * @param Capteur
     * @return
     */

    public Capteur updateCapteur(Capteur Capteur) {
        Capteur updatedCapteur = capteurRepo.save(Capteur);
        log.info("Capteur mis à jour avec succès avec l'id: {}", updatedCapteur.getId());
        log.debug("Détails de l'équipement après mise à jour: {}", updatedCapteur);
        return updatedCapteur;
    }

     /**
     * 
     * @param id
     * @return
     */

    public void deleteCapteurById(Integer id) {
        capteurRepo.deleteById(id);
        log.debug("Capteur avec id: {} supprimé avec succès", id);
    }
}
