package com.sae_s6.S6.APIGestion.controller;

import com.sae_s6.S6.APIGestion.entity.Capteur;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.sae_s6.S6.APIGestion.service.CapteurService;

import java.util.List;

/**
 * Contrôleur REST pour la gestion des capteurs.
 * Fournit des endpoints pour effectuer des opérations CRUD sur les entités Capteur.
 */
@RestController
@RequestMapping("/api/capteur")
@RequiredArgsConstructor
@Validated
public class CapteurController {

    private final CapteurService capteurService;

    /**
     * Endpoint pour récupérer tous les capteurs.
     * URL: localhost:8080/api/capteur/
     *
     * @return Une réponse contenant la liste de tous les capteurs.
     */
    @GetMapping("/")
    public ResponseEntity<List<Capteur>> getAllCapteurs() {
        List<Capteur> capteurs = capteurService.getAllCapteurs();
        if (capteurs == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(capteurs);
    }

    /**
     * Endpoint pour récupérer un capteur par son ID.
     * URL: localhost:8080/api/capteur/{id}
     *
     * @param id L'identifiant du capteur.
     * @return Une réponse contenant le capteur correspondant ou une réponse 400 s'il n'est pas trouvé.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Capteur> getCapteurById(@PathVariable("id") Integer id) {
        Capteur capteur = capteurService.getCapteurById(id);
        if (capteur == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(capteur);
    }

    /**
     * Endpoint pour créer un nouveau capteur.
     * URL: localhost:8080/api/capteur/
     *
     * @param capteur L'entité Capteur à créer.
     * @return Une réponse contenant le capteur créé.
     */
    @PostMapping("/")
    public ResponseEntity<Capteur> saveCapteur(@RequestBody Capteur capteur) {
        Capteur savedCapteur = capteurService.saveCapteur(capteur);
        if (savedCapteur == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(savedCapteur);
    }

    /**
     * Endpoint pour mettre à jour un capteur existant.
     * URL: localhost:8080/api/capteur/
     *
     * @param capteur L'entité Capteur à mettre à jour.
     * @return Une réponse contenant le capteur mis à jour ou une réponse 400 s'il n'est pas trouvé.
     */
    @PutMapping("/")
    public ResponseEntity<Capteur> updateCapteur(@RequestBody Capteur capteur) {
        Capteur updatedCapteur = capteurService.updateCapteur(capteur);
        if (updatedCapteur == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(updatedCapteur);
    }

    /**
     * Endpoint pour supprimer un capteur par son ID.
     * URL: localhost:8080/api/capteur/{id}
     *
     * @param id L'identifiant du capteur à supprimer.
     * @return Une réponse indiquant que la suppression a été effectuée avec succès.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCapteurById(@PathVariable("id") Integer id) {
        Capteur capteur = capteurService.getCapteurById(id);
        if (capteur == null) {
            return ResponseEntity.badRequest().build();
        }
        capteurService.deleteCapteurById(id);
        return ResponseEntity.ok("Capteur supprimé avec succès");
    }
}