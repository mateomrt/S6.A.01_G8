package com.sae_s6.S6.APIGestion.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    private final MurService murService;

    /**
     * Endpoint pour récupérer tous les murs.
     * URL: localhost:8080/api/murs/
     *
     * @return Une réponse contenant la liste de tous les murs.
     */
    @GetMapping("/")
    public ResponseEntity<List<Mur>> getAllMurs() {
        List<Mur> murs = murService.getAllMurs();
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
        Mur mur = murService.getMurById(id);
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
        log.info("Requête POST reçue pour sauvegarder un mur: {}", mur);
        Mur savedMur = murService.saveMur(mur);
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
        Mur updatedMur = murService.updateMur(mur);
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
        Mur mur = murService.getMurById(id);
        if (mur == null) {
            return ResponseEntity.badRequest().build();
        }
        murService.deleteMurById(id);
        return ResponseEntity.ok("Mur supprimé avec succès");
    }
}