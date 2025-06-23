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

import com.sae_s6.S6.APIGestion.entity.Batiment;
import com.sae_s6.S6.APIGestion.service.BatimentService;

import lombok.RequiredArgsConstructor;

/**
 * Contrôleur REST pour la gestion des bâtiments.
 * Fournit des endpoints pour effectuer des opérations CRUD sur les entités Batiment.
 */
@RestController
@RequestMapping("/api/batiment")
@RequiredArgsConstructor
@Validated
public class BatimentController {

    // Service pour gérer les opérations liées aux bâtiments.
    private final BatimentService batimentService;

    /**
     * Endpoint pour récupérer tous les bâtiments.
     * URL: localhost:8080/api/batiment/
     *
     * @return Une réponse contenant la liste de tous les bâtiments.
     */
    @GetMapping("/")
    public ResponseEntity<List<Batiment>> getAllBatiments() {
        // Récupère tous les bâtiments via le service.
        List<Batiment> batiments = batimentService.getAllBatiments();
        // Vérifie si la liste est nulle et retourne une réponse appropriée.
        if (batiments == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(batiments);
    }

    /**
     * Endpoint pour récupérer un bâtiment par son ID.
     * URL: localhost:8080/api/batiment/{id}
     *
     * @param id L'identifiant du bâtiment.
     * @return Une réponse contenant le bâtiment correspondant ou une réponse 404 s'il n'est pas trouvé.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Batiment> getBatimentById(@PathVariable("id") Integer id) {
        // Récupère le bâtiment par son ID via le service.
        Batiment batiment = batimentService.getBatimentById(id);
        // Vérifie si le bâtiment existe et retourne une réponse appropriée.
        if (batiment == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(batiment);
    }

    /**
     * Endpoint pour créer un nouveau bâtiment.
     * URL: localhost:8080/api/batiment/
     *
     * @param batiment L'entité Batiment à créer.
     * @return Une réponse contenant le bâtiment créé.
     */
    @PostMapping("/")
    public ResponseEntity<Batiment> saveBatiment(@RequestBody Batiment batiment) {
        // Sauvegarde le bâtiment via le service.
        Batiment savedBatiment = batimentService.saveBatiment(batiment);
        // Vérifie si la sauvegarde a réussi et retourne une réponse appropriée.
        if (savedBatiment == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(savedBatiment);
    }

    /**
     * Endpoint pour mettre à jour un bâtiment existant.
     * URL: localhost:8080/api/batiment/
     *
     * @param batiment Les nouvelles données du bâtiment.
     * @return Une réponse contenant le bâtiment mis à jour ou une réponse 404 s'il n'est pas trouvé.
     */
    @PutMapping("/")
    public ResponseEntity<Batiment> updateBatiment(@RequestBody Batiment batiment) {
        // Met à jour le bâtiment via le service.
        Batiment updatedBatiment = batimentService.updateBatiment(batiment);
        // Vérifie si la mise à jour a réussi et retourne une réponse appropriée.
        if (updatedBatiment == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(updatedBatiment);
    }

    /**
     * Endpoint pour supprimer un bâtiment par son ID.
     * URL: localhost:8080/api/batiment/{id}
     *
     * @param id L'identifiant du bâtiment à supprimer.
     * @return Une réponse indiquant que la suppression a été effectuée.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBatiment(@PathVariable("id") Integer id) {
        // Récupère le bâtiment par son ID via le service.
        Batiment batiment = batimentService.getBatimentById(id);
        // Vérifie si le bâtiment existe avant de le supprimer.
        if (batiment == null) {
            return ResponseEntity.badRequest().build();
        }
        // Supprime le bâtiment via le service.
        batimentService.deleteBatimentById(id);
        return ResponseEntity.ok("Bâtiment supprimé avec succès");
    }
}