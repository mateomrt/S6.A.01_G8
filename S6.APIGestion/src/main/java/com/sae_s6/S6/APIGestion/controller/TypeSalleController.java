package com.sae_s6.S6.APIGestion.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.sae_s6.S6.APIGestion.entity.TypeSalle;
import com.sae_s6.S6.APIGestion.service.TypeSalleService;

import lombok.RequiredArgsConstructor;

/**
 * Contrôleur REST pour la gestion des types de salles.
 * Fournit des endpoints pour effectuer des opérations CRUD sur les entités TypeSalle.
 */
@RestController
@RequestMapping("/api/typesalle")
@RequiredArgsConstructor
@Validated
public class TypeSalleController {

    // Service pour gérer les opérations liées aux types de salles.
    private final TypeSalleService typeSalleService;

    /**
     * Endpoint pour récupérer tous les types de salles.
     * URL: localhost:8080/api/typesalle/
     *
     * @return Une réponse contenant la liste de tous les types de salles.
     */
    @GetMapping("/")
    public ResponseEntity<List<TypeSalle>> getAllSalles() {
        // Récupère tous les types de salles via le service.
        List<TypeSalle> typeSalles = typeSalleService.getAllTypeSalles();
        // Vérifie si la liste est nulle et retourne une réponse appropriée.
        if (typeSalles == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(typeSalles);
    }

    /**
     * Endpoint pour récupérer un type de salle par son ID.
     * URL: localhost:8080/api/typesalle/{id}
     *
     * @param id L'identifiant du type de salle.
     * @return Une réponse contenant le type de salle correspondant ou une réponse 400 s'il n'est pas trouvé.
     */
    @GetMapping("/{id}")
    public ResponseEntity<TypeSalle> getSalleById(@PathVariable("id") Integer id) {
        // Récupère le type de salle par son ID via le service.
        TypeSalle typeSalle = typeSalleService.getTypeSalleById(id);
        // Vérifie si le type de salle existe et retourne une réponse appropriée.
        if (typeSalle == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(typeSalle);
    }

    /**
     * Endpoint pour créer un nouveau type de salle.
     * URL: localhost:8080/api/typesalle/
     *
     * @param typeSalle L'entité TypeSalle à créer.
     * @return Une réponse contenant le type de salle créé.
     */
    @PostMapping("/")
    public ResponseEntity<TypeSalle> saveSalle(@RequestBody TypeSalle salle) {
        // Sauvegarde le type de salle via le service.
        TypeSalle savedTypeSalle = typeSalleService.saveTypeSalle(salle);
        // Vérifie si la sauvegarde a réussi et retourne une réponse appropriée.
        if (savedTypeSalle == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(savedTypeSalle);
    }

    /**
     * Endpoint pour mettre à jour un type de salle existant.
     * URL: localhost:8080/api/typesalle/
     *
     * @param typeSalle L'entité TypeSalle à mettre à jour.
     * @return Une réponse contenant le type de salle mis à jour ou une réponse 400 s'il n'est pas trouvé.
     */
    @PutMapping("/")
    public ResponseEntity<TypeSalle> updateSalle(@RequestBody TypeSalle typeSalle) {
        // Met à jour le type de salle via le service.
        TypeSalle updatedTypeSalle = typeSalleService.updateTypeSalle(typeSalle);
        // Vérifie si la mise à jour a réussi et retourne une réponse appropriée.
        if (updatedTypeSalle == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(updatedTypeSalle);
    }

    /**
     * Endpoint pour supprimer un type de salle par son ID.
     * URL: localhost:8080/api/typesalle/{id}
     *
     * @param id L'identifiant du type de salle à supprimer.
     * @return Une réponse indiquant que la suppression a été effectuée avec succès.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSalleById(@PathVariable("id") Integer id) {
        // Récupère le type de salle par son ID via le service.
        TypeSalle typeSalle = typeSalleService.getTypeSalleById(id);
        // Vérifie si le type de salle existe avant de le supprimer.
        if (typeSalle == null) {
            return ResponseEntity.badRequest().build();
        }
        // Supprime le type de salle via le service.
        typeSalleService.deleteTypeSalleById(id);
        return ResponseEntity.ok("Salle supprimée avec succès");
    }
}