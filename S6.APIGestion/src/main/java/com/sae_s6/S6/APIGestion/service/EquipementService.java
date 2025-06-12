package com.sae_s6.S6.APIGestion.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import com.sae_s6.S6.APIGestion.repository.EquipementRepo;
import java.util.List;
import java.util.Optional;

import com.sae_s6.S6.APIGestion.entity.Equipement;

@Service
@RequiredArgsConstructor
@Slf4j
public class EquipementService {
    private final EquipementRepo equipementRepo;

    public List<Equipement> getAllEquipements() {
        List<Equipement> equipements = equipementRepo.findAll();
        log.debug("Liste des équipements récupérés: {}", equipements);
        return equipements;
    }

    /**
     * 
     * @param id
     * @return
     */

    public Equipement getEquipementById(Integer id) {
        Optional<Equipement> optionalEquipement = equipementRepo.findById(id);
        if (optionalEquipement.isPresent()) {
            log.debug("Détails de l'équipement trouvé: {}", optionalEquipement.get());
            return optionalEquipement.get();
        }
        log.warn("Aucun équipement trouvé avec l'id: {}", id);
        return null;
    }

       /**
         * 
         * @param equipement
         * @return
         */

    public Equipement saveEquipement(Equipement equipement) {
        Equipement savedEquipement = equipementRepo.save(equipement);
        log.info("Equipement sauvegardé avec succès avec l'id: {}", savedEquipement.getId());
        log.debug("Détails de l'équipement sauvegardé: {}", savedEquipement);
        return savedEquipement;
    }

    
    /**
     * 
     * @param equipement
     * @return
     */

    public Equipement updateEquipement(Equipement equipement) {
        Equipement updatedEquipement = equipementRepo.save(equipement);
        log.info("Equipement mis à jour avec succès avec l'id: {}", updatedEquipement.getId());
        log.debug("Détails de l'équipement après mise à jour: {}", updatedEquipement);
        return updatedEquipement;
    }

     /**
     * 
     * @param id
     * @return
     */

    public void deleteEquipementById(Integer id) {
        equipementRepo.deleteById(id);
        log.debug("Equipement avec id: {} supprimé avec succès", id);
    }
}
