package com.sae_s6.S6.APIGestion.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.sae_s6.S6.APIGestion.entity.Donnee;
import com.sae_s6.S6.APIGestion.service.DonneeService;

import lombok.RequiredArgsConstructor;

/**
 * Contrôleur REST pour la gestion des données.
 * Fournit des endpoints pour effectuer des opérations CRUD sur les entités Donnee.
 */
@RestController
@RequestMapping("/api/donnee")
@RequiredArgsConstructor
@Validated
public class DonneeController {

    private final DonneeService donneeService;

    /**
     * Endpoint pour récupérer toutes les données.
     * URL: localhost:8080/api/donnee/
     *
     * @return Une réponse contenant la liste de toutes les données.
     */
    @GetMapping("/")
    public ResponseEntity<List<Donnee>> getAllDonnees() {
        List<Donnee> donnees = donneeService.getAllDonnees();
        if (donnees == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(donnees);
    }

    /**
     * Endpoint pour récupérer une donnée par son ID.
     * URL: localhost:8080/api/donnee/{id}
     *
     * @param id L'identifiant de la donnée.
     * @return Une réponse contenant la donnée correspondante ou une réponse 400 s'il n'est pas trouvée.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Donnee> getDonneeById(@PathVariable("id") Integer id) {
        Donnee donnee = donneeService.getDonneeById(id);
        if (donnee == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(donnee);
    }

    /**
     * Endpoint pour créer une nouvelle donnée.
     * URL: localhost:8080/api/donnee/
     *
     * @param donnee L'entité Donnee à créer.
     * @return Une réponse contenant la donnée créée.
     */
    @PostMapping("/")
    public ResponseEntity<Donnee> saveDonnee(@RequestBody Donnee donnee) {
        Donnee savedDonnee = donneeService.saveDonnee(donnee);
        if (savedDonnee == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(savedDonnee);
    }

    /**
     * Endpoint pour mettre à jour une donnée existante.
     * URL: localhost:8080/api/donnee/
     *
     * @param donnee L'entité Donnee à mettre à jour.
     * @return Une réponse contenant la donnée mise à jour ou une réponse 400 s'il n'est pas trouvée.
     */
    @PutMapping("/")
    public ResponseEntity<Donnee> updateDonnee(@RequestBody Donnee donnee) {
        Donnee updatedDonnee = donneeService.updateDonnee(donnee);
        if (updatedDonnee == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(updatedDonnee);
    }

    /**
     * Endpoint pour supprimer une donnée par son ID.
     * URL: localhost:8080/api/donnee/{id}
     *
     * @param id L'identifiant de la donnée à supprimer.
     * @return Une réponse indiquant que la suppression a été effectuée avec succès.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDonneeById(@PathVariable("id") Integer id) {
        Donnee donnee = donneeService.getDonneeById(id);
        if (donnee == null) {
            return ResponseEntity.badRequest().build();
        }
        donneeService.deleteDonneeById(id);
        return ResponseEntity.ok("Donnée supprimée avec succès");
    }
}