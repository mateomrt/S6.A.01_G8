package com.sae_s6.S6.APIGestion.controller;

import com.sae_s6.S6.APIGestion.entity.Salle;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.sae_s6.S6.APIGestion.service.SalleService;

import java.util.List;

/**
 * Contrôleur REST pour la gestion des salles.
 * Fournit des endpoints pour effectuer des opérations CRUD sur les entités Salle.
 */
@RestController
@RequestMapping("/api/salle")
@RequiredArgsConstructor
@Validated
public class SalleController {

    // Service pour gérer les opérations liées aux salles.
    private final SalleService salleService;

    /**
     * Endpoint pour récupérer toutes les salles.
     * URL: localhost:8080/api/salle/
     *
     * @return Une réponse contenant la liste de toutes les salles.
     */
    @GetMapping("/")
    public ResponseEntity<List<Salle>> getAllSalles() {
        // Récupère toutes les salles via le service.
        List<Salle> salles = salleService.getAllSalles();
        // Vérifie si la liste est nulle et retourne une réponse appropriée.
        if (salles == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(salles);
    }

    /**
     * Endpoint pour récupérer une salle par son ID.
     * URL: localhost:8080/api/salle/{id}
     *
     * @param id L'identifiant de la salle.
     * @return Une réponse contenant la salle correspondante ou une réponse 400 s'il n'est pas trouvée.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Salle> getSalleById(@PathVariable("id") Integer id) {
        // Récupère la salle par son ID via le service.
        Salle salle = salleService.getSalleById(id);
        // Vérifie si la salle existe et retourne une réponse appropriée.
        if (salle == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(salle);
    }

    /**
     * Endpoint pour créer une nouvelle salle.
     * URL: localhost:8080/api/salle/
     *
     * @param salle L'entité Salle à créer.
     * @return Une réponse contenant la salle créée.
     */
    @PostMapping("/")
    public ResponseEntity<Salle> saveSalle(@RequestBody Salle salle) {
        // Sauvegarde la salle via le service.
        Salle savedSalle = salleService.saveSalle(salle);
        // Vérifie si la sauvegarde a réussi et retourne une réponse appropriée.
        if (savedSalle == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(savedSalle);
    }

    /**
     * Endpoint pour mettre à jour une salle existante.
     * URL: localhost:8080/api/salle/
     *
     * @param salle L'entité Salle à mettre à jour.
     * @return Une réponse contenant la salle mise à jour ou une réponse 400 s'il n'est pas trouvée.
     */
    @PutMapping("/")
    public ResponseEntity<Salle> updateSalle(@RequestBody Salle salle) {
        // Met à jour la salle via le service.
        Salle updatedSalle = salleService.updateSalle(salle);
        // Vérifie si la mise à jour a réussi et retourne une réponse appropriée.
        if (updatedSalle == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(updatedSalle);
    }

    /**
     * Endpoint pour supprimer une salle par son ID.
     * URL: localhost:8080/api/salle/{id}
     *
     * @param id L'identifiant de la salle à supprimer.
     * @return Une réponse indiquant que la suppression a été effectuée avec succès.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSalleById(@PathVariable("id") Integer id) {
        // Récupère la salle par son ID via le service.
        Salle salle = salleService.getSalleById(id);
        // Vérifie si la salle existe avant de la supprimer.
        if (salle == null) {
            return ResponseEntity.badRequest().build();
        }
        // Supprime la salle via le service.
        salleService.deleteSalleById(id);
        return ResponseEntity.ok("Salle supprimée avec succès");
    }

    /**
     * Endpoint pour rechercher des salles par leur libellé exact.
     * URL: localhost:8080/api/salle/search?libelleSalle={libelleSalle}
     *
     * @param libelleSalle Le libellé exact à rechercher.
     * @return Une réponse contenant la liste des salles correspondantes.
     */
    @GetMapping("/search")
    public ResponseEntity<List<Salle>> getSallesByLibelleSalle(@RequestParam(name = "libelleSalle") String libelleSalle) {
        // Recherche les salles par leur libellé via le service.
        return ResponseEntity.ok().body(salleService.getSallesByLibelleSalle(libelleSalle));
    }
}