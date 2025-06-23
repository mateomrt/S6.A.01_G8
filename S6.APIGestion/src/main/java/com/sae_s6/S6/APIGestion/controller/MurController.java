package com.sae_s6.S6.APIGestion.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.sae_s6.S6.APIGestion.entity.Mur;
import com.sae_s6.S6.APIGestion.service.MurService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Contrôleur REST pour la gestion des murs.
 * Fournit des endpoints pour effectuer des opérations CRUD sur les entités Mur.
 */
@RestController
@RequestMapping("/api/murs")
@RequiredArgsConstructor
@Validated
@Slf4j
public class MurController {

    // Service pour gérer les opérations liées aux murs.
    private final MurService murService;

    /**
     * Endpoint pour récupérer tous les murs.
     * URL: localhost:8080/api/murs/
     *
     * @return Une réponse contenant la liste de tous les murs.
     */
    @GetMapping("/")
    public ResponseEntity<List<Mur>> getAllMurs() {
        // Récupère tous les murs via le service.
        List<Mur> murs = murService.getAllMurs();
        // Vérifie si la liste est nulle et retourne une réponse appropriée.
        if (murs == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(murs);
    }

    /**
     * Endpoint pour récupérer un mur par son ID.
     * URL: localhost:8080/api/murs/{id}
     *
     * @param id L'identifiant du mur.
     * @return Une réponse contenant le mur correspondant ou une réponse 400 s'il n'est pas trouvé.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Mur> getMurById(@PathVariable("id") Integer id) {
        // Récupère le mur par son ID via le service.
        Mur mur = murService.getMurById(id);
        // Vérifie si le mur existe et retourne une réponse appropriée.
        if (mur == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(mur);
    }

    /**
     * Endpoint pour créer un nouveau mur.
     * URL: localhost:8080/api/murs/
     *
     * @param mur L'entité Mur à créer.
     * @return Une réponse contenant le mur créé.
     */
    @PostMapping("/")
    public ResponseEntity<Mur> saveMur(@RequestBody Mur mur) {
        // Log de la requête reçue.
        log.info("Requête POST reçue pour sauvegarder un mur: {}", mur);
        // Sauvegarde le mur via le service.
        Mur savedMur = murService.saveMur(mur);
        // Vérifie si la sauvegarde a réussi et retourne une réponse appropriée.
        if (savedMur == null) {
            log.warn("Échec de la sauvegarde du mur: {}", mur);
            return ResponseEntity.badRequest().build();
        }
        log.info("Mur sauvegardé avec succès: {}", savedMur);
        return ResponseEntity.ok(savedMur);
    }

    /**
     * Endpoint pour mettre à jour un mur existant.
     * URL: localhost:8080/api/murs/
     *
     * @param mur L'entité Mur à mettre à jour.
     * @return Une réponse contenant le mur mis à jour ou une réponse 400 s'il n'est pas trouvé.
     */
    @PutMapping("/")
    public ResponseEntity<Mur> updateMur(@RequestBody Mur mur) {
        // Met à jour le mur via le service.
        Mur updatedMur = murService.updateMur(mur);
        // Vérifie si la mise à jour a réussi et retourne une réponse appropriée.
        if (updatedMur == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(updatedMur);
    }

    /**
     * Endpoint pour supprimer un mur par son ID.
     * URL: localhost:8080/api/murs/{id}
     *
     * @param id L'identifiant du mur à supprimer.
     * @return Une réponse indiquant que la suppression a été effectuée avec succès.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteMurById(@PathVariable("id") Integer id) {
        // Récupère le mur par son ID via le service.
        Mur mur = murService.getMurById(id);
        // Vérifie si le mur existe avant de le supprimer.
        if (mur == null) {
            return ResponseEntity.badRequest().build();
        }
        // Supprime le mur via le service.
        murService.deleteMurById(id);
        return ResponseEntity.ok("Mur supprimé avec succès");
    }
}