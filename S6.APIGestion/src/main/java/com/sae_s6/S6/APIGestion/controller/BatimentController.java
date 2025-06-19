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

    private final BatimentService batimentService;

    /**
     * Endpoint pour récupérer tous les bâtiments.
     *
     * @return Une réponse contenant la liste de tous les bâtiments.
     */
    @GetMapping("/")
    public ResponseEntity<List<Batiment>> getAllBatiments() {
        List<Batiment> batiments = batimentService.getAllBatiments();
        if (batiments == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(batiments);
    }

    /**
     * Endpoint pour récupérer un bâtiment par son ID.
     *
     * @param id L'identifiant du bâtiment.
     * @return Une réponse contenant le bâtiment correspondant ou une réponse 404 s'il n'est pas trouvé.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Batiment> getBatimentById(@PathVariable("id") Integer id) {
        Batiment batiment = batimentService.getBatimentById(id);
        if (batiment == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(batiment);
    }

    /**
     * Endpoint pour créer un nouveau bâtiment.
     *
     * @param batiment L'entité Batiment à créer.
     * @return Une réponse contenant le bâtiment créé.
     */
    @PostMapping("/")
    public ResponseEntity<Batiment> saveBatiment(@RequestBody Batiment batiment) {
        Batiment savedBatiment = batimentService.saveBatiment(batiment);
        if (savedBatiment == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(savedBatiment);
    }

    /**
     * Endpoint pour mettre à jour un bâtiment existant.
     *
     * @param id L'identifiant du bâtiment à mettre à jour.
     * @param updated Les nouvelles données du bâtiment.
     * @return Une réponse contenant le bâtiment mis à jour ou une réponse 404 s'il n'est pas trouvé.
     */
    @PutMapping("/")
    public ResponseEntity<Batiment> updateBatiment(@RequestBody Batiment batiment) {
        Batiment updatedBatiment = batimentService.updateBatiment(batiment);
        if (updatedBatiment == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(updatedBatiment);
    }

    /**
     * Endpoint pour supprimer un bâtiment par son ID.
     *
     * @param id L'identifiant du bâtiment à supprimer.
     * @return Une réponse indiquant que la suppression a été effectuée.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBatiment(@PathVariable("id") Integer id) {
        Batiment batiment = batimentService.getBatimentById(id);
        if (batiment == null) {
            return ResponseEntity.badRequest().build();
        }
        
        batimentService.deleteBatimentById(id);
        return ResponseEntity.ok("Bâtiment supprimé avec succès");
    }
}