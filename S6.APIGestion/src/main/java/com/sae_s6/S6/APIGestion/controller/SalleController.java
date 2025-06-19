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

    private final SalleService salleService;

    /**
     * Endpoint pour récupérer toutes les salles.
     * URL: localhost:8080/api/salle/
     *
     * @return Une réponse contenant la liste de toutes les salles.
     */
    @GetMapping("/")
    public ResponseEntity<List<Salle>> getAllSalles() {
        List<Salle> salles = salleService.getAllSalles();
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
        Salle salle = salleService.getSalleById(id);
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
        Salle savedSalle = salleService.saveSalle(salle);
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
        Salle updatedSalle = salleService.updateSalle(salle);
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
        Salle salle = salleService.getSalleById(id);
        if (salle == null) {
            return ResponseEntity.badRequest().build();
        }
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
        return ResponseEntity.ok().body(salleService.getSallesByLibelleSalle(libelleSalle));
    }
}