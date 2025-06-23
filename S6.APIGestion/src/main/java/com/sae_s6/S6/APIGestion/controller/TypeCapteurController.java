package com.sae_s6.S6.APIGestion.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.sae_s6.S6.APIGestion.entity.TypeCapteur;
import com.sae_s6.S6.APIGestion.service.TypeCapteurService;

import lombok.RequiredArgsConstructor;

/**
 * Contrôleur REST pour la gestion des types de capteurs.
 * Fournit des endpoints pour effectuer des opérations CRUD sur les entités TypeCapteur.
 */
@RestController
@RequestMapping("/api/typeCapteur")
@RequiredArgsConstructor
@Validated
public class TypeCapteurController {

    // Service pour gérer les opérations liées aux types de capteurs.
    private final TypeCapteurService typeCapteurService;

    /**
     * Endpoint pour récupérer tous les types de capteurs.
     * URL: localhost:8080/api/typeCapteur/
     *
     * @return Une réponse contenant la liste de tous les types de capteurs.
     */
    @GetMapping("/")
    public ResponseEntity<List<TypeCapteur>> getAllTypeCapteurs() {
        // Récupère tous les types de capteurs via le service.
        List<TypeCapteur> typeCapteurs = typeCapteurService.getAllTypeCapteurs();
        // Vérifie si la liste est nulle et retourne une réponse appropriée.
        if (typeCapteurs == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(typeCapteurs);
    }

    /**
     * Endpoint pour récupérer un type de capteur par son ID.
     * URL: localhost:8080/api/typeCapteur/{id}
     *
     * @param id L'identifiant du type de capteur.
     * @return Une réponse contenant le type de capteur correspondant ou une réponse 400 s'il n'est pas trouvé.
     */
    @GetMapping("/{id}")
    public ResponseEntity<TypeCapteur> getTypeCapteurById(@PathVariable("id") Integer id) {
        // Récupère le type de capteur par son ID via le service.
        TypeCapteur typeCapteur = typeCapteurService.getTypeCapteurById(id);
        // Vérifie si le type de capteur existe et retourne une réponse appropriée.
        if (typeCapteur == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(typeCapteur);
    }

    /**
     * Endpoint pour créer un nouveau type de capteur.
     * URL: localhost:8080/api/typeCapteur/
     *
     * @param typeCapteur L'entité TypeCapteur à créer.
     * @return Une réponse contenant le type de capteur créé.
     */
    @PostMapping("/")
    public ResponseEntity<TypeCapteur> saveTypeCapteur(@RequestBody TypeCapteur typeCapteur) {
        // Sauvegarde le type de capteur via le service.
        TypeCapteur savedTypeCapteur = typeCapteurService.saveTypeCapteur(typeCapteur);
        // Vérifie si la sauvegarde a réussi et retourne une réponse appropriée.
        if (savedTypeCapteur == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(savedTypeCapteur);
    }

    /**
     * Endpoint pour mettre à jour un type de capteur existant.
     * URL: localhost:8080/api/typeCapteur/
     *
     * @param typeCapteur L'entité TypeCapteur à mettre à jour.
     * @return Une réponse contenant le type de capteur mis à jour ou une réponse 400 s'il n'est pas trouvé.
     */
    @PutMapping("/")
    public ResponseEntity<TypeCapteur> updateTypeCapteur(@RequestBody TypeCapteur typeCapteur) {
        // Met à jour le type de capteur via le service.
        TypeCapteur updatedTypeCapteur = typeCapteurService.updateTypeCapteur(typeCapteur);
        // Vérifie si la mise à jour a réussi et retourne une réponse appropriée.
        if (updatedTypeCapteur == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(updatedTypeCapteur);
    }

    /**
     * Endpoint pour supprimer un type de capteur par son ID.
     * URL: localhost:8080/api/typeCapteur/{id}
     *
     * @param id L'identifiant du type de capteur à supprimer.
     * @return Une réponse indiquant que la suppression a été effectuée avec succès.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTypeCapteurById(@PathVariable("id") Integer id) {
        // Récupère le type de capteur par son ID via le service.
        TypeCapteur typeCapteur = typeCapteurService.getTypeCapteurById(id);
        // Vérifie si le type de capteur existe avant de le supprimer.
        if (typeCapteur == null) {
            return ResponseEntity.badRequest().build();
        }
        // Supprime le type de capteur via le service.
        typeCapteurService.deleteTypeCapteurById(id);
        return ResponseEntity.ok("Type capteur supprimé avec succès");
    }
}