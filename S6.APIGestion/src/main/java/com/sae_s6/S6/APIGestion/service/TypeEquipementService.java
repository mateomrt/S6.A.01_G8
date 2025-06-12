package com.sae_s6.S6.APIGestion.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import com.sae_s6.S6.APIGestion.repository.TypeEquipementRepo;
import com.sae_s6.S6.APIGestion.entity.TypeEquipement;


@Service
@RequiredArgsConstructor
@Slf4j
public class TypeEquipementService {
    private final TypeEquipementRepo typeEquipementRepo; 

    public List<TypeEquipement> getAllTypeEquipements() {
        List<TypeEquipement> typeEquipements = typeEquipementRepo.findAll();
        log.debug("Liste des type d'équipements récupérées: {}", typeEquipements);
        return typeEquipements;
    }


     /**
     * 
     * @param id
     * @return
     */

     public TypeEquipement getTypeEquipementById(Integer id) {
        Optional<TypeEquipement> optionalTypeEquipement = typeEquipementRepo.findById(id);
        if (optionalTypeEquipement.isPresent()) {
            log.debug("Détails du type d'équipement trouvé: {}", optionalTypeEquipement.get());
            return optionalTypeEquipement.get();
        }
        log.warn("Aucun type d'équipement trouvé avec l'id: {}", id);
        return null;
    }

       /**
         * 
         * @param typeEquipement
         * @return
         */

    public TypeEquipement saveTypeEquipement(TypeEquipement typeEquipement) {
        TypeEquipement savedTypeEquipement = typeEquipementRepo.save(typeEquipement);
        log.info("Type d'équipement sauvegardé avec succès avec l'id: {}", savedTypeEquipement.getIdTypeEquipement());
        log.debug("Détails du type d'équipement sauvegardé: {}", savedTypeEquipement);
        return savedTypeEquipement;
    }

    
    /**
     * 
     * @param typeEquipement
     * @return
     */

    public TypeEquipement updateTypeEquipement(TypeEquipement typeEquipement) {
        TypeEquipement updatedTypeEquipement = typeEquipementRepo.save(typeEquipement);
        log.info("Type d'équipement mis à jour avec succès avec l'id: {}", updatedTypeEquipement.getIdTypeEquipement());
        log.debug("Détails du type d'équipement après mise à jour: {}", updatedTypeEquipement);
        return updatedTypeEquipement;
    }

     /**
     * 
     * @param id
     * @return
     */

    public void deleteTypeEquipementById(Integer id) {
        typeEquipementRepo.deleteById(id);
        log.debug("Type d'équipement avec id: {} supprimé avec succès", id);
    }
}
