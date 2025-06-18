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

        // Si nécessaire, hydrater la salle du mur (optionnel selon besoin)
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
