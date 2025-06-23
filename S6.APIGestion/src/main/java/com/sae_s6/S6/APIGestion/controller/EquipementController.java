package com.sae_s6.S6.APIGestion.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.sae_s6.S6.APIGestion.entity.Equipement;
import com.sae_s6.S6.APIGestion.service.EquipementService;

import lombok.RequiredArgsConstructor;

/**
 * Contrôleur REST pour la gestion des équipements.
 * Fournit des endpoints pour effectuer des opérations CRUD sur les entités Equipement.
 */
@RestController
@RequestMapping("/api/equipement")
@RequiredArgsConstructor
@Validated
public class EquipementController {

    // Service pour gérer les opérations liées aux équipements.
    private final EquipementService equipementService;

    /**
     * Endpoint pour récupérer tous les équipements.
     * URL: localhost:8080/api/equipement/
     *
     * @return Une réponse contenant la liste de tous les équipements.
     */
    @GetMapping("/")
    public ResponseEntity<List<Equipement>> getAllEquipements() {
        // Récupère tous les équipements via le service.
        List<Equipement> equipements = equipementService.getAllEquipements();
        // Vérifie si la liste est nulle et retourne une réponse appropriée.
        if (equipements == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(equipements);
    }

    /**
     * Endpoint pour récupérer un équipement par son ID.
     * URL: localhost:8080/api/equipement/{id}
     *
     * @param id L'identifiant de l'équipement.
     * @return Une réponse contenant l'équipement correspondant ou une réponse 400 s'il n'est pas trouvé.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Equipement> getEquipementById(@PathVariable("id") Integer id) {
        // Récupère l'équipement par son ID via le service.
        Equipement equipement = equipementService.getEquipementById(id);
        // Vérifie si l'équipement existe et retourne une réponse appropriée.
        if (equipement == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(equipement);
    }

    /**
     * Endpoint pour créer un nouvel équipement.
     * URL: localhost:8080/api/equipement/
     *
     * @param equipement L'entité Equipement à créer.
     * @return Une réponse contenant l'équipement créé.
     */
    @PostMapping("/")
    public ResponseEntity<Equipement> saveEquipement(@RequestBody Equipement equipement) {
        // Sauvegarde l'équipement via le service.
        Equipement savedEquipement = equipementService.saveEquipement(equipement);
        // Vérifie si la sauvegarde a réussi et retourne une réponse appropriée.
        if (savedEquipement == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(savedEquipement);
    }

    /**
     * Endpoint pour mettre à jour un équipement existant.
     * URL: localhost:8080/api/equipement/
     *
     * @param equipement L'entité Equipement à mettre à jour.
     * @return Une réponse contenant l'équipement mis à jour ou une réponse 400 s'il n'est pas trouvé.
     */
    @PutMapping("/")
    public ResponseEntity<Equipement> updateEquipement(@RequestBody Equipement equipement) {
        // Met à jour l'équipement via le service.
        Equipement updatedEquipement = equipementService.updateEquipement(equipement);
        // Vérifie si la mise à jour a réussi et retourne une réponse appropriée.
        if (updatedEquipement == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(updatedEquipement);
    }

    /**
     * Endpoint pour supprimer un équipement par son ID.
     * URL: localhost:8080/api/equipement/{id}
     *
     * @param id L'identifiant de l'équipement à supprimer.
     * @return Une réponse indiquant que la suppression a été effectuée avec succès.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEquipementById(@PathVariable("id") Integer id) {
        // Récupère l'équipement par son ID via le service.
        Equipement equipement = equipementService.getEquipementById(id);
        // Vérifie si l'équipement existe avant de le supprimer.
        if (equipement == null) {
            return ResponseEntity.badRequest().build();
        }
        // Supprime l'équipement via le service.
        equipementService.deleteEquipementById(id);
        return ResponseEntity.ok("Equipement supprimé avec succès");
    }
}