package com.sae_s6.S6.APIGestion.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import com.sae_s6.S6.APIGestion.repository.BatimentRepo;
import com.sae_s6.S6.APIGestion.repository.EquipementRepo;
import com.sae_s6.S6.APIGestion.repository.MurRepo;
import com.sae_s6.S6.APIGestion.repository.SalleRepo;
import com.sae_s6.S6.APIGestion.repository.TypeEquipementRepo;
import com.sae_s6.S6.APIGestion.repository.TypeSalleRepo;

import java.util.List;
import java.util.Optional;

import com.sae_s6.S6.APIGestion.entity.Equipement;
import com.sae_s6.S6.APIGestion.entity.Mur;
import com.sae_s6.S6.APIGestion.entity.Salle;

@Service
@RequiredArgsConstructor
@Slf4j
public class EquipementService {
    private final EquipementRepo equipementRepo;
    private final SalleRepo salleRepo;
    private final MurRepo murRepo;
    private final TypeEquipementRepo typeEquipementRepo;
    private final BatimentRepo batimentRepo;
    private final TypeSalleRepo typeSalleRepo;
    

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
        Salle salle = salleRepo.findById(equipement.getSalleNavigation().getId())
            .orElseThrow(() -> new RuntimeException("Salle non trouvée"));
        salle.setBatimentNavigation(
            batimentRepo.findById(salle.getBatimentNavigation().getId())
                .orElseThrow(() -> new RuntimeException("Bâtiment non trouvé"))
        );
        salle.setTypeSalleNavigation(
            typeSalleRepo.findById(salle.getTypeSalleNavigation().getId())
                .orElseThrow(() -> new RuntimeException("Type de salle non trouvé"))
        );
        equipement.setSalleNavigation(salle);

        if (equipement.getMurNavigation() != null) {
            Mur mur = murRepo.findById(equipement.getMurNavigation().getId())
                .orElseThrow(() -> new RuntimeException("Mur non trouvé"));

            Salle salleMur = salleRepo.findById(mur.getSalleNavigation().getId())
                .orElseThrow(() -> new RuntimeException("Salle du mur non trouvée"));

            salleMur.setBatimentNavigation(
                batimentRepo.findById(salleMur.getBatimentNavigation().getId())
                    .orElseThrow(() -> new RuntimeException("Bâtiment non trouvé"))
            );
            salleMur.setTypeSalleNavigation(
                typeSalleRepo.findById(salleMur.getTypeSalleNavigation().getId())
                    .orElseThrow(() -> new RuntimeException("Type de salle non trouvé"))
            );
            mur.setSalleNavigation(salleMur);
            equipement.setMurNavigation(mur);
        }

        if (equipement.getTypeEquipementNavigation() != null) {
            equipement.setTypeEquipementNavigation(
                typeEquipementRepo.findById(equipement.getTypeEquipementNavigation().getId())
                    .orElseThrow(() -> new RuntimeException("Type équipement non trouvé"))
            );
        }

        return equipementRepo.save(equipement);
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
