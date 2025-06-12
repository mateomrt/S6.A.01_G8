package com.sae_s6.S6.APIGestion.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.sae_s6.S6.APIGestion.entity.Donnee;
import com.sae_s6.S6.APIGestion.repository.DonneeRepo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Service
@RequiredArgsConstructor
@Slf4j
public class DonneeService {

    private final DonneeRepo donneeRepo;

    public List<Donnee> getAllDonnees() {
        List<Donnee> donnees = donneeRepo.findAll();
        log.debug("Liste des données récupérées: {}", donnees);
        return donnees;
    }

    /**
     * 
     * @param id
     * @return
     */

    public Donnee getDonneeById(Integer id) {
        Optional<Donnee> optionalDonnee = donneeRepo.findById(id);
        if (optionalDonnee.isPresent()) {
            log.debug("Détails de la Donnee trouvée: {}", optionalDonnee.get());
            return optionalDonnee.get();
        }
        log.warn("Aucune Donnee trouvée avec l'id: {}", id);
        return null;
    }

       /**
         * 
         * @param Donnee
         * @return
         */

    public Donnee saveDonnee(Donnee Donnee) {
        Donnee savedDonnee = donneeRepo.save(Donnee);
        log.info("Donnee sauvegardée avec succès avec l'id: {}", savedDonnee.getId());
        log.debug("Détails de la Donnee sauvegardée: {}", savedDonnee);
        return savedDonnee;
    }

    
    /**
     * 
     * @param Donnee
     * @return
     */

    public Donnee updateDonnee(Donnee Donnee) {
        Donnee updatedDonnee = donneeRepo.save(Donnee);
        log.info("Donnee mise à jour avec succès avec l'id: {}", updatedDonnee.getId());
        log.debug("Détails de la Donnee après mise à jour: {}", updatedDonnee);
        return updatedDonnee;
    }

     /**
     * 
     * @param id
     * @return
     */

    public void deleteDonneeById(Integer id) {
        donneeRepo.deleteById(id);
        log.debug("Donnee with id: {} deleted successfully", id);
    }

   
}
