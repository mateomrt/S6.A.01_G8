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
        log.debug("Requête reçue pour récupérer toutes les associations TypeCapteurDonnee.");
        List<TypeCapteurDonnee> typeCapteurDonnees = typeCapteurDonneeService.getAllTypeCapteurDonnee();
        if (typeCapteurDonnees == null || typeCapteurDonnees.isEmpty()) {
            log.warn("Aucune association TypeCapteurDonnee trouvée.");
            return ResponseEntity.noContent().build();
        }
        log.info("Associations TypeCapteurDonnee récupérées avec succès.");
        return ResponseEntity.ok(typeCapteurDonnees);
    }

    /**
     * Endpoint pour récupérer une association TypeCapteurDonnee par sa clé composite.
     * URL: localhost:8080/api/type_capteur_donnee/{donneeId}/{typeCapteurId}
     *
     * @param donneeId L'identifiant de la donnée.
     * @param typeCapteurId L'identifiant du type de capteur.
     * @return Une réponse contenant l'association TypeCapteurDonnee correspondante ou une réponse 404 si elle n'est pas trouvée.
     */
    @GetMapping("/{donneeId}/{typeCapteurId}")
    public ResponseEntity<TypeCapteurDonnee> getTypeCapteurDonneeById(
            @PathVariable("donneeId") Integer donneeId,
            @PathVariable("typeCapteurId") Integer typeCapteurId) {
        log.debug("Requête reçue pour récupérer l'association avec donneeId = {} et typeCapteurId = {}", donneeId, typeCapteurId);
        TypeCapteurDonneeEmbedId id = new TypeCapteurDonneeEmbedId(donneeId, typeCapteurId);
        TypeCapteurDonnee typeCapteurDonnee = typeCapteurDonneeService.getTypeCapteurDonneeById(id);
        if (typeCapteurDonnee == null) {
            log.warn("Aucune association TypeCapteurDonnee trouvée pour donneeId = {} et typeCapteurId = {}", donneeId, typeCapteurId);
            return ResponseEntity.notFound().build();
        }
        log.info("Association TypeCapteurDonnee récupérée avec succès pour donneeId = {} et typeCapteurId = {}", donneeId, typeCapteurId);
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
        log.debug("Requête reçue pour créer une nouvelle association TypeCapteurDonnee : {}", typeCapteurDonnee);
        TypeCapteurDonnee savedTypeCapteurDonnee = typeCapteurDonneeService.saveTypeCapteurDonnee(typeCapteurDonnee);
        if (savedTypeCapteurDonnee == null) {
            log.warn("Échec de la création de l'association TypeCapteurDonnee.");
            return ResponseEntity.badRequest().build();
        }
        log.info("Association TypeCapteurDonnee créée avec succès : {}", savedTypeCapteurDonnee);
        return ResponseEntity.ok(savedTypeCapteurDonnee);
    }

    /**
     * Endpoint pour mettre à jour une association TypeCapteurDonnee existante.
     * Permet de mettre à jour le type de capteur, la donnée, ou les deux.
     * URL: localhost:8080/api/type_capteur_donnee/{typeCapteurId}/{donneeId}
     *
     * @param typeCapteurId L'identifiant du type de capteur existant.
     * @param donneeId L'identifiant de la donnée existante.
     * @param updates Un objet JSON contenant les nouveaux identifiants (newIdTypeCapteur et/ou newIdDonnee).
     * @return Une réponse contenant l'association TypeCapteurDonnee mise à jour ou une réponse 400 si elle n'est pas trouvée.
     */
    @PutMapping("/{donneeId}/{typeCapteurId}")
    public ResponseEntity<TypeCapteurDonnee> updateTypeCapteurOrDonnee(
            @PathVariable("typeCapteurId") Integer typeCapteurId,
            @PathVariable("donneeId") Integer donneeId,
            @RequestBody UpdateRequest updates) {
        log.debug("Requête reçue pour mettre à jour l'association avec typeCapteurId = {}, donneeId = {}, newIdTypeCapteur = {}, newIdDonnee = {}",
                typeCapteurId, donneeId, updates.getNewIdTypeCapteur(), updates.getNewIdDonnee());
        try {
            TypeCapteurDonneeEmbedId id = new TypeCapteurDonneeEmbedId(donneeId, typeCapteurId);
            TypeCapteurDonnee updatedTypeCapteurDonnee = typeCapteurDonneeService.updateTypeCapteurOrDonnee(
                    id, updates.getNewIdTypeCapteur(), updates.getNewIdDonnee());
            log.info("Association TypeCapteurDonnee mise à jour avec succès : {}", updatedTypeCapteurDonnee);
            return ResponseEntity.ok(updatedTypeCapteurDonnee);
        } catch (IllegalArgumentException e) {
            log.warn("Échec de la mise à jour de l'association TypeCapteurDonnee : {}", e.getMessage());
            return ResponseEntity.badRequest().body(null);
        }
    }

    /**
     * Classe interne pour représenter le corps JSON de la requête.
     * Contient les nouveaux identifiants pour la mise à jour.
     */
    public static class UpdateRequest {
        private Integer newIdTypeCapteur; // Nouvel identifiant du type de capteur
        private Integer newIdDonnee; // Nouvel identifiant de la donnée

        // Getters et setters
        public Integer getNewIdTypeCapteur() {
            return newIdTypeCapteur;
        }

        public void setNewIdTypeCapteur(Integer newIdTypeCapteur) {
            this.newIdTypeCapteur = newIdTypeCapteur;
        }

        public Integer getNewIdDonnee() {
            return newIdDonnee;
        }

        public void setNewIdDonnee(Integer newIdDonnee) {
            this.newIdDonnee = newIdDonnee;
        }
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
        log.debug("Requête reçue pour supprimer l'association avec donneeId = {} et typeCapteurId = {}", donneeId, typeCapteurId);
        TypeCapteurDonneeEmbedId id = new TypeCapteurDonneeEmbedId(donneeId, typeCapteurId);
        TypeCapteurDonnee typeCapteurDonnee = typeCapteurDonneeService.getTypeCapteurDonneeById(id);
        if (typeCapteurDonnee == null) {
            log.warn("Aucune association TypeCapteurDonnee trouvée pour donneeId = {} et typeCapteurId = {}", donneeId, typeCapteurId);
            return ResponseEntity.notFound().build();
        }
        typeCapteurDonneeService.deleteTypeCapteurDonneeById(id);
        log.info("Association TypeCapteurDonnee supprimée avec succès pour donneeId = {} et typeCapteurId = {}", donneeId, typeCapteurId);
        return ResponseEntity.ok("Association TypeCapteurDonnee supprimée avec succès");
    }
}