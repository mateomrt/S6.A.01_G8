package com.sae_s6.S6.APIGestion.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.sae_s6.S6.APIGestion.entity.TypeCapteurDonnee;
import com.sae_s6.S6.APIGestion.repository.TypeCapteurDonneeRepo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Service
@RequiredArgsConstructor
@Slf4j
public class TypeCapteurDonneeService {

    private final TypeCapteurDonneeRepo typeCapteurDonneeRepo;

    public List<TypeCapteurDonnee> getAllTypeCapteurDonnee() {
        List<TypeCapteurDonnee> donnees = typeCapteurDonneeRepo.findAll();
        log.debug("Liste des données récupérées: {}", donnees);
        return donnees;
    }

    /**
     * 
     * @param id
     * @return
     */

    public TypeCapteurDonnee getTypeCapteurDonneeById(Integer id) {
        Optional<TypeCapteurDonnee> optionalDonnee = typeCapteurDonneeRepo.findById(id);
        if (optionalDonnee.isPresent()) {
            log.debug("Détails de TypeCapteurDonnee trouvée: {}", optionalDonnee.get());
            return optionalDonnee.get();
        }
        log.warn("Aucun TypeCapteurDonnee trouvée avec l'id: {}", id);
        return null;
    }

       /**
         * 
         * @param TypeCapteurDonnee
         * @return
         */

    public TypeCapteurDonnee saveTypeCapteurDonnee(TypeCapteurDonnee Donnee) {
        TypeCapteurDonnee savedDonnee = typeCapteurDonneeRepo.save(Donnee);
        log.info("TypeCapteurDonnee sauvegardé avec succès avec l'id: {}", savedDonnee.getId());
        log.debug("Détails de TypeCapteurDonnee sauvegardée: {}", savedDonnee);
        return savedDonnee;
    }

    
    /**
     * 
     * @param TypeCapteurDonnee
     * @return
     */

    public TypeCapteurDonnee updateTypeCapteurDonnee(TypeCapteurDonnee Donnee) {
        TypeCapteurDonnee updatedDonnee = typeCapteurDonneeRepo.save(Donnee);
        log.info("Donnee mise à jour avec succès avec l'id: {}", updatedDonnee.getId());
        log.debug("Détails de TypeCapteurDonnee après mise à jour: {}", updatedDonnee);
        return updatedDonnee;
    }

     /**
     * 
     * @param id
     * @return
     */

    public void deleteTypeCapteurDonneeById(Integer id) {
        typeCapteurDonneeRepo.deleteById(id);
        log.debug("TypeCapteurDonnee with id: {} deleted successfully", id);
    }

   
}
