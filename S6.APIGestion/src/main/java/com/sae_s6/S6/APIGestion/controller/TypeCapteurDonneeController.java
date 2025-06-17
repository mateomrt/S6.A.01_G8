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

import com.sae_s6.S6.APIGestion.entity.TypeCapteurDonnee;
import com.sae_s6.S6.APIGestion.entity.TypeCapteurDonneeEmbedId;
import com.sae_s6.S6.APIGestion.service.TypeCapteurDonneeService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/type_capteur_donnee")
@RequiredArgsConstructor
@Validated
public class TypeCapteurDonneeController {

    private final TypeCapteurDonneeService typeCapteurDonneeService;

    /**
     * Cette méthode est appelée lors d’une requête GET.
     * URL: localhost:8080/api/type_capteur_donnee/
     * But: Récupère toutes les Donnees dans la table Donnees.
     * @return Liste des Donnees.
     */
    @GetMapping("/")
    public ResponseEntity<List<TypeCapteurDonnee>> getAllDonnees() {
        List<TypeCapteurDonnee> Donnees = typeCapteurDonneeService.getAllTypeCapteurDonnee();
        if (Donnees == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(Donnees);
    }

    /**
     * Cette méthode est appelée lors d’une requête GET.
     * URL: localhost:8080/api/type_capteur_donnee/{donneeId}/{typeCapteurId}
     * But: Récupère la Donnee avec l’id composite associé.
     * @param donneeId - ID de la Donnee.
     * @param typeCapteurId - ID du TypeCapteur.
     * @return Donnee avec l’id composite associé.
     */
    @GetMapping("/{donneeId}/{typeCapteurId}")
    public ResponseEntity<TypeCapteurDonnee> getDonneeById(
            @PathVariable("donneeId") Integer donneeId,
            @PathVariable("typeCapteurId") Integer typeCapteurId) {
        TypeCapteurDonneeEmbedId id = new TypeCapteurDonneeEmbedId(donneeId, typeCapteurId);
        TypeCapteurDonnee Donnee = typeCapteurDonneeService.getTypeCapteurDonneeById(id);
        if (Donnee == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(Donnee);
    }

    /**
     * Cette méthode est appelée lors d’une requête POST.
     * URL: localhost:8080/api/type_capteur_donnee/
     * Purpose: Création d’une entité Donnee.
     * @param Donnee – le body de la requête est une entité Donnee.
     * @return Entité Donnee créée.
     */
    @PostMapping("/")
    public ResponseEntity<TypeCapteurDonnee> saveDonnee(@RequestBody TypeCapteurDonnee Donnee) {
        TypeCapteurDonnee savedDonnee = typeCapteurDonneeService.saveTypeCapteurDonnee(Donnee);
        if (savedDonnee == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(savedDonnee);
    }

    /**
     * Cette méthode est appelée lors d’une requête PUT.
     * URL: localhost:8080/api/type_capteur_donnee/
     * Purpose: Met à jour une entité Donnee.
     * @param Donnee - Entité Donnee à mettre à jour.
     * @return Entité Donnee mise à jour.
     */
    @PutMapping("/")
    public ResponseEntity<TypeCapteurDonnee> updateDonnee(@RequestBody TypeCapteurDonnee Donnee) {
        TypeCapteurDonnee updatedDonnee = typeCapteurDonneeService.updateTypeCapteurDonnee(Donnee);
        if (updatedDonnee == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(updatedDonnee);
    }

    /**
     * Cette méthode est appelée lors d’une requête DELETE.
     * URL: localhost:8080/api/type_capteur_donnee/{donneeId}/{typeCapteurId}
     * Purpose: Supprime une entité Donnee.
     * @param donneeId - ID de la Donnee à supprimer.
     * @param typeCapteurId - ID du TypeCapteur à supprimer.
     * @return Un message indiquant que l’enregistrement a été supprimé avec succès.
     */
    @DeleteMapping("/{donneeId}/{typeCapteurId}")
    public ResponseEntity<String> deleteDonneeById(
            @PathVariable("donneeId") Integer donneeId,
            @PathVariable("typeCapteurId") Integer typeCapteurId) {
        TypeCapteurDonneeEmbedId id = new TypeCapteurDonneeEmbedId(donneeId, typeCapteurId);
        TypeCapteurDonnee Donnee = typeCapteurDonneeService.getTypeCapteurDonneeById(id);
        if (Donnee == null) {
            return ResponseEntity.badRequest().build();
        }
        typeCapteurDonneeService.deleteTypeCapteurDonneeById(id);
        return ResponseEntity.ok("Donnee supprimée avec succès");
    }
}