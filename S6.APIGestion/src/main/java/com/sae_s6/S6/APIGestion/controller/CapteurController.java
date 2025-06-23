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

    // Service pour gérer les opérations liées aux capteurs.
    private final CapteurService capteurService;

    /**
     * Endpoint pour récupérer tous les capteurs.
     * URL: localhost:8080/api/capteur/
     *
     * @return Une réponse contenant la liste de tous les capteurs.
     */
    @GetMapping("/")
    public ResponseEntity<List<Capteur>> getAllCapteurs() {
        // Récupère tous les capteurs via le service.
        List<Capteur> capteurs = capteurService.getAllCapteurs();
        // Vérifie si la liste est nulle et retourne une réponse appropriée.
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
        // Récupère le capteur par son ID via le service.
        Capteur capteur = capteurService.getCapteurById(id);
        // Vérifie si le capteur existe et retourne une réponse appropriée.
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
        // Sauvegarde le capteur via le service.
        Capteur savedCapteur = capteurService.saveCapteur(capteur);
        // Vérifie si la sauvegarde a réussi et retourne une réponse appropriée.
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
        // Met à jour le capteur via le service.
        Capteur updatedCapteur = capteurService.updateCapteur(capteur);
        // Vérifie si la mise à jour a réussi et retourne une réponse appropriée.
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
        // Récupère le capteur par son ID via le service.
        Capteur capteur = capteurService.getCapteurById(id);
        // Vérifie si le capteur existe avant de le supprimer.
        if (capteur == null) {
            return ResponseEntity.badRequest().build();
        }
        // Supprime le capteur via le service.
        capteurService.deleteCapteurById(id);
        return ResponseEntity.ok("Capteur supprimé avec succès");
    }
}