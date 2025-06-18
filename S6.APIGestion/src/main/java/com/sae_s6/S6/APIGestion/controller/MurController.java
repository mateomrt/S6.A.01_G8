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

@RestController
@RequestMapping("/api/murs")
@RequiredArgsConstructor
@Validated
@Slf4j
public class MurController {
    private final MurService murService;

    /**
     * Cette méthode est appelée lors d’une requête GET.
     * URL: localhost:8080/api/murs/
     * But: Récupère tous les murs dans la table mur.
     * @return Liste des murs.
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
     * Cette méthode est appelée lors d’une requête GET.
     * URL: localhost:8080/api/murs/{id}
     * But: Récupère le mur avec l’id associé.
     * @param id - id du mur.
     * @return Mur avec l’id associé.
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
     * Cette méthode est appelée lors d’une requête POST.
     * URL: localhost:8080/api/murs/
     * But: Création d’une entité mur.
     * @param mur – le body de la requête est une entité mur.
     * @return Entité mur créée.
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
     * Cette méthode est appelée lors d’une requête PUT.
     * URL: localhost:8080/api/murs/
     * But: Met à jour une entité mur.
     * @param mur - entité mur à mettre à jour.
     * @return Entité mur mise à jour.
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
     * Cette méthode est appelée lors d’une requête DELETE.
     * URL: localhost:8080/api/murs/{id}
     * But: Supprime une entité mur.
     * @param id - l’id du mur à supprimer.
     * @return Un message indiquant que l’enregistrement a été supprimé avec succès.
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