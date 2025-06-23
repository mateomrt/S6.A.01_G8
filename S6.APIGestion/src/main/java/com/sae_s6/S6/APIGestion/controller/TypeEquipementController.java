package com.sae_s6.S6.APIGestion.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.sae_s6.S6.APIGestion.entity.TypeEquipement;
import com.sae_s6.S6.APIGestion.service.TypeEquipementService;

import lombok.RequiredArgsConstructor;

/**
 * Contrôleur REST pour la gestion des types d'équipements.
 * Fournit des endpoints pour effectuer des opérations CRUD sur les entités TypeEquipement.
 */
@RestController
@RequestMapping("/api/typeequipement")
@RequiredArgsConstructor
@Validated
public class TypeEquipementController {

    // Service pour gérer les opérations liées aux types d'équipements.
    private final TypeEquipementService typeEquipementService;

    /**
     * Endpoint pour récupérer tous les types d'équipements.
     * URL: localhost:8080/api/typeequipement/
     *
     * @return Une réponse contenant la liste de tous les types d'équipements.
     */
    @GetMapping("/")
    public ResponseEntity<List<TypeEquipement>> getAllTypeEquipements() {
        // Récupère tous les types d'équipements via le service.
        List<TypeEquipement> typeEquipements = typeEquipementService.getAllTypeEquipements();
        // Vérifie si la liste est nulle et retourne une réponse appropriée.
        if (typeEquipements == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(typeEquipements);
    }

    /**
     * Endpoint pour récupérer un type d'équipement par son ID.
     * URL: localhost:8080/api/typeequipement/{id}
     *
     * @param id L'identifiant du type d'équipement.
     * @return Une réponse contenant le type d'équipement correspondant ou une réponse 400 s'il n'est pas trouvé.
     */
    @GetMapping("/{id}")
    public ResponseEntity<TypeEquipement> getTypeEquipementById(@PathVariable("id") Integer id) {
        // Récupère le type d'équipement par son ID via le service.
        TypeEquipement typeEquipement = typeEquipementService.getTypeEquipementById(id);
        // Vérifie si le type d'équipement existe et retourne une réponse appropriée.
        if (typeEquipement == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(typeEquipement);
    }

    /**
     * Endpoint pour créer un nouveau type d'équipement.
     * URL: localhost:8080/api/typeequipement/
     *
     * @param typeEquipement L'entité TypeEquipement à créer.
     * @return Une réponse contenant le type d'équipement créé.
     */
    @PostMapping("/")
    public ResponseEntity<TypeEquipement> saveTypeEquipement(@RequestBody TypeEquipement typeEquipement) {
        // Sauvegarde le type d'équipement via le service.
        TypeEquipement savedTypeEquipement = typeEquipementService.saveTypeEquipement(typeEquipement);
        // Vérifie si la sauvegarde a réussi et retourne une réponse appropriée.
        if (savedTypeEquipement == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(savedTypeEquipement);
    }

    /**
     * Endpoint pour mettre à jour un type d'équipement existant.
     * URL: localhost:8080/api/typeequipement/
     *
     * @param typeEquipement L'entité TypeEquipement à mettre à jour.
     * @return Une réponse contenant le type d'équipement mis à jour ou une réponse 400 s'il n'est pas trouvé.
     */
    @PutMapping("/")
    public ResponseEntity<TypeEquipement> updateTypeEquipement(@RequestBody TypeEquipement typeEquipement) {
        // Met à jour le type d'équipement via le service.
        TypeEquipement updatedTypeEquipement = typeEquipementService.updateTypeEquipement(typeEquipement);
        // Vérifie si la mise à jour a réussi et retourne une réponse appropriée.
        if (updatedTypeEquipement == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(updatedTypeEquipement);
    }

    /**
     * Endpoint pour supprimer un type d'équipement par son ID.
     * URL: localhost:8080/api/typeequipement/{id}
     *
     * @param id L'identifiant du type d'équipement à supprimer.
     * @return Une réponse indiquant que la suppression a été effectuée avec succès.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTypeEquipementById(@PathVariable("id") Integer id) {
        // Récupère le type d'équipement par son ID via le service.
        TypeEquipement typeEquipement = typeEquipementService.getTypeEquipementById(id);
        // Vérifie si le type d'équipement existe avant de le supprimer.
        if (typeEquipement == null) {
            return ResponseEntity.badRequest().build();
        }
        // Supprime le type d'équipement via le service.
        typeEquipementService.deleteTypeEquipementById(id);
        return ResponseEntity.ok("Type équipement supprimé avec succès");
    }
}