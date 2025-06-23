package com.sae_s6.S6.APIGestion.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.sae_s6.S6.APIGestion.entity.TypeCapteurDonnee;
import com.sae_s6.S6.APIGestion.entity.TypeCapteurDonneeEmbedId;
import com.sae_s6.S6.APIGestion.service.TypeCapteurDonneeService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Contrôleur REST pour la gestion des associations entre types de capteurs et données.
 * Fournit des endpoints pour effectuer des opérations CRUD sur les entités TypeCapteurDonnee.
 */
@RestController
@RequestMapping("/api/type_capteur_donnee")
@RequiredArgsConstructor
@Validated
@Slf4j
public class TypeCapteurDonneeController {

    private final TypeCapteurDonneeService typeCapteurDonneeService;

    /**
     * Endpoint pour récupérer toutes les associations entre types de capteurs et données.
     * URL: localhost:8080/api/type_capteur_donnee/
     *
     * @return Une réponse contenant la liste de toutes les associations TypeCapteurDonnee.
     */
    @GetMapping("/")
    public ResponseEntity<List<TypeCapteurDonnee>> getAllTypeCapteurDonnees() {
        List<TypeCapteurDonnee> typeCapteurDonnees = typeCapteurDonneeService.getAllTypeCapteurDonnee();
        if (typeCapteurDonnees == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(typeCapteurDonnees);
    }

    /**
     * Endpoint pour récupérer une association TypeCapteurDonnee par sa clé composite.
     * URL: localhost:8080/api/type_capteur_donnee/{donneeId}/{typeCapteurId}
     *
     * @param donneeId L'identifiant de la donnée.
     * @param typeCapteurId L'identifiant du type de capteur.
     * @return Une réponse contenant l'association TypeCapteurDonnee correspondante ou une réponse 400 si elle n'est pas trouvée.
     */
    @GetMapping("/{donneeId}/{typeCapteurId}")
    public ResponseEntity<TypeCapteurDonnee> getTypeCapteurDonneeById(
            @PathVariable("donneeId") Integer donneeId,
            @PathVariable("typeCapteurId") Integer typeCapteurId) {
        TypeCapteurDonneeEmbedId id = new TypeCapteurDonneeEmbedId(donneeId, typeCapteurId);
        TypeCapteurDonnee typeCapteurDonnee = typeCapteurDonneeService.getTypeCapteurDonneeById(id);
        if (typeCapteurDonnee == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(typeCapteurDonnee);
    }

    /**
     * Endpoint pour créer une nouvelle association TypeCapteurDonnee.
     * URL: localhost:8080/api/type_capteur_donnee/
     *
     * @param typeCapteurDonnee L'entité TypeCapteurDonnee à créer.
     * @return Une réponse contenant l'association TypeCapteurDonnee créée.
     */
    @PostMapping("/")
    public ResponseEntity<TypeCapteurDonnee> saveTypeCapteurDonnee(@RequestBody TypeCapteurDonnee typeCapteurDonnee) {
        TypeCapteurDonnee savedTypeCapteurDonnee = typeCapteurDonneeService.saveTypeCapteurDonnee(typeCapteurDonnee);
        if (savedTypeCapteurDonnee == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(savedTypeCapteurDonnee);
    }

    /**
     * Endpoint pour mettre à jour une association TypeCapteurDonnee existante.
     * URL: localhost:8080/api/type_capteur_donnee/
     *
     * @param typeCapteurDonnee L'entité TypeCapteurDonnee à mettre à jour.
     * @return Une réponse contenant l'association TypeCapteurDonnee mise à jour ou une réponse 400 si elle n'est pas trouvée.
     */
    @PutMapping("/")
    public ResponseEntity<TypeCapteurDonnee> updateTypeCapteurDonnee(@RequestBody TypeCapteurDonnee typeCapteurDonnee) {
        if (typeCapteurDonnee.getId() == null) {
            log.warn("La clé composite de l'association TypeCapteurDonnee est manquante");
            return ResponseEntity.badRequest().build();
        }

        TypeCapteurDonnee updatedTypeCapteurDonnee = typeCapteurDonneeService.updateTypeCapteurDonnee(typeCapteurDonnee);
        if (updatedTypeCapteurDonnee == null) {
            log.warn("Échec de la mise à jour de l'association TypeCapteurDonnee avec l'id composite: {}", typeCapteurDonnee.getId());
            return ResponseEntity.badRequest().build();
        }

        log.info("Association TypeCapteurDonnee mise à jour avec succès avec l'id composite: {}", updatedTypeCapteurDonnee.getId());
        return ResponseEntity.ok(updatedTypeCapteurDonnee);
    }

    /**
     * Endpoint pour supprimer une association TypeCapteurDonnee par sa clé composite.
     * URL: localhost:8080/api/type_capteur_donnee/{donneeId}/{typeCapteurId}
     *
     * @param donneeId L'identifiant de la donnée.
     * @param typeCapteurId L'identifiant du type de capteur.
     * @return Une réponse indiquant que la suppression a été effectuée avec succès.
     */
    @DeleteMapping("/{donneeId}/{typeCapteurId}")
    public ResponseEntity<String> deleteTypeCapteurDonneeById(
            @PathVariable("donneeId") Integer donneeId,
            @PathVariable("typeCapteurId") Integer typeCapteurId) {
        TypeCapteurDonneeEmbedId id = new TypeCapteurDonneeEmbedId(donneeId, typeCapteurId);
        TypeCapteurDonnee typeCapteurDonnee = typeCapteurDonneeService.getTypeCapteurDonneeById(id);
        if (typeCapteurDonnee == null) {
            return ResponseEntity.badRequest().build();
        }
        typeCapteurDonneeService.deleteTypeCapteurDonneeById(id);
        return ResponseEntity.ok("Association TypeCapteurDonnee supprimée avec succès");
    }
}