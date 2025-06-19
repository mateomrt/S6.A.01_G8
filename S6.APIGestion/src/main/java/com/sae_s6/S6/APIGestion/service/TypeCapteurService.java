package com.sae_s6.S6.APIGestion.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import com.sae_s6.S6.APIGestion.repository.TypeCapteurRepo;
import com.sae_s6.S6.APIGestion.entity.Salle;
import com.sae_s6.S6.APIGestion.entity.TypeCapteur;


@Service
@RequiredArgsConstructor
@Slf4j
public class TypeCapteurService {
    private final TypeCapteurRepo typeCapteurRepo; 

    public List<TypeCapteur> getAllTypeCapteurs() {
        List<TypeCapteur> typeCapteurs = typeCapteurRepo.findAll();
        log.debug("Liste des type de capteurs récupérées: {}", typeCapteurs);
        return typeCapteurs;
    }


     /**
     * 
     * @param id
     * @return
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
         * 
         * @param typeCapteur
         * @return
         */

    public TypeCapteur saveTypeCapteur(TypeCapteur typeCapteur) {
        TypeCapteur savedTypeCapteur = typeCapteurRepo.save(typeCapteur);
        log.info("Type de capteur sauvegardé avec succès avec l'id: {}", savedTypeCapteur.getId());
        log.debug("Détails du type de capteur sauvegardé: {}", savedTypeCapteur);
        return savedTypeCapteur;
    }

    
    /**
     * 
     * @param typeCapteur
     * @return
     */

    public TypeCapteur updateTypeCapteur(TypeCapteur typeCapteur) {
        TypeCapteur updatedTypeCapteur = typeCapteurRepo.save(typeCapteur);
        log.info("Type de capteur mis à jour avec succès avec l'id: {}", updatedTypeCapteur.getId());
        log.debug("Détails du type de capteur après mise à jour: {}", updatedTypeCapteur);
        return updatedTypeCapteur;
    }

     /**
     * 
     * @param id
     * @return
     */

    public void deleteTypeCapteurById(Integer id) {
        typeCapteurRepo.deleteById(id);
        log.debug("Type de capteur avec id: {} supprimé avec succès", id);
    }
    
    public List<TypeCapteur> getByLibelleTypeCapteurContainingIgnoreCase(String libelleTypeCapteur) {
        return typeCapteurRepo.findByLibelleTypeCapteurContainingIgnoreCase(libelleTypeCapteur);
    }
}
