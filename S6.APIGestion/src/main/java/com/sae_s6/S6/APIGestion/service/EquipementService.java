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

/**
 * Service pour la gestion des équipements.
 * Fournit des méthodes pour effectuer des opérations CRUD sur les entités Equipement.
 */
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

    /**
     * Récupère tous les équipements.
     *
     * @return Une liste de tous les équipements.
     */
    public List<Equipement> getAllEquipements() {
        List<Equipement> equipements = equipementRepo.findAll();
        log.debug("Liste des équipements récupérés: {}", equipements);
        return equipements;
    }

    /**
     * Récupère un équipement par son ID.
     *
     * @param id L'identifiant de l'équipement.
     * @return L'équipement correspondant ou null s'il n'est pas trouvé.
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
     * Enregistre un nouvel équipement.
     * Hydrate les relations associées avant de sauvegarder.
     *
     * @param equipement L'entité Equipement à enregistrer.
     * @return L'équipement enregistré.
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
     * Met à jour un équipement existant.
     *
     * @param equipement L'entité Equipement avec les nouvelles données.
     * @return L'équipement mis à jour.
     */
    public Equipement updateEquipement(Equipement equipement) {
        Equipement updatedEquipement = equipementRepo.save(equipement);
        log.info("Equipement mis à jour avec succès avec l'id: {}", updatedEquipement.getId());
        log.debug("Détails de l'équipement après mise à jour: {}", updatedEquipement);
        return updatedEquipement;
    }

    /**
     * Supprime un équipement par son ID.
     *
     * @param id L'identifiant de l'équipement à supprimer.
     */
    public void deleteEquipementById(Integer id) {
        equipementRepo.deleteById(id);
        log.debug("Equipement avec id: {} supprimé avec succès", id);
    }

    /**
     * Recherche les équipements dont le libellé contient une chaîne de caractères donnée, sans tenir compte de la casse.
     *
     * @param libelleEquipement La chaîne de caractères à rechercher.
     * @return Une liste d'équipements correspondant à la recherche.
     */
    public List<Equipement> getByLibelleEquipementContainingIgnoreCase(String libelleEquipement) {
        return equipementRepo.findByLibelleEquipementContainingIgnoreCase(libelleEquipement);
    }
}