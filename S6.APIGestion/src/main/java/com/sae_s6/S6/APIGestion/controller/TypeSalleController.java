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

    private final TypeSalleService typeSalleService;

    /**
     * Endpoint pour récupérer tous les types de salles.
     * URL: localhost:8080/api/typesalle/
     *
     * @return Une réponse contenant la liste de tous les types de salles.
     */
    @GetMapping("/")
    public ResponseEntity<List<TypeSalle>> getAllSalles() {
        List<TypeSalle> typeSalles = typeSalleService.getAllTypeSalles();
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
        TypeSalle typeSalle = typeSalleService.getTypeSalleById(id);
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
        TypeSalle savedTypeSalle = typeSalleService.saveTypeSalle(salle);
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
        TypeSalle updatedTypeSalle = typeSalleService.updateTypeSalle(typeSalle);
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
        TypeSalle typeSalle = typeSalleService.getTypeSalleById(id);
        if (typeSalle == null) {
            return ResponseEntity.badRequest().build();
        }
        typeSalleService.deleteTypeSalleById(id);
        return ResponseEntity.ok("Salle supprimée avec succès");
    }
}