package com.sae_s6.S6.APIGestion.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import com.sae_s6.S6.APIGestion.repository.BatimentRepo;
import com.sae_s6.S6.APIGestion.repository.MurRepo;
import com.sae_s6.S6.APIGestion.repository.SalleRepo;
import com.sae_s6.S6.APIGestion.repository.TypeSalleRepo;

import java.util.List;
import java.util.Optional;

import com.sae_s6.S6.APIGestion.entity.Batiment;
import com.sae_s6.S6.APIGestion.entity.Mur;
import com.sae_s6.S6.APIGestion.entity.Salle;
import com.sae_s6.S6.APIGestion.entity.TypeSalle;

@Service
@RequiredArgsConstructor
@Slf4j
public class MurService {
    private final MurRepo murRepo;
    private final SalleRepo salleRepo;
    private final BatimentRepo batimentRepo;
    private final TypeSalleRepo typeSalleRepo;

    public List<Mur> getAllMurs() {
        List<Mur> murs = murRepo.findAll();
        log.debug("Liste des murs récupérés: {}", murs);
        return murs;
    }

    public Mur getMurById(Integer id) {
        Optional<Mur> optionalMur = murRepo.findById(id);
        if (optionalMur.isPresent()) {
            log.debug("Détails du mur trouvé: {}", optionalMur.get());
            return optionalMur.get();
        }
        log.warn("Aucun mur trouvé avec l'id: {}", id);
        return null;
    }

    public Mur saveMur(Mur mur) {
        Salle salleInput = mur.getSalleNavigation();
        Salle salle = salleRepo.findById(salleInput.getId())
            .orElseThrow(() -> new RuntimeException("Salle non trouvée"));
    
        // Optionnel mais recommandé si tu veux forcer la cohérence
        Batiment batiment = batimentRepo.findById(salleInput.getBatimentNavigation().getId())
            .orElseThrow(() -> new RuntimeException("Batiment non trouvé"));
    
        TypeSalle typeSalle = typeSalleRepo.findById(salleInput.getTypeSalleNavigation().getId())
            .orElseThrow(() -> new RuntimeException("Type de salle non trouvé"));
    
        salle.setBatimentNavigation(batiment);
        salle.setTypeSalleNavigation(typeSalle);
    
        mur.setSalleNavigation(salle);
        return murRepo.save(mur);
    }


    public Mur updateMur(Mur mur) {
        Mur updatedMur = murRepo.save(mur);
        log.info("Mur mis à jour avec succès avec l'id: {}", updatedMur.getId());
        log.debug("Détails du mur après mise à jour: {}", updatedMur);
        return updatedMur;
    }

    public void deleteMurById(Integer id) {
        murRepo.deleteById(id);
        log.debug("Mur avec id: {} supprimé avec succès", id);
    }
}