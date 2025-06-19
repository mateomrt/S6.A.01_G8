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

    private final TypeCapteurService typeCapteurService;

    /**
     * Endpoint pour récupérer tous les types de capteurs.
     * URL: localhost:8080/api/typeCapteur/
     *
     * @return Une réponse contenant la liste de tous les types de capteurs.
     */
    @GetMapping("/")
    public ResponseEntity<List<TypeCapteur>> getAllTypeCapteurs() {
        List<TypeCapteur> typeCapteurs = typeCapteurService.getAllTypeCapteurs();
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
        TypeCapteur typeCapteur = typeCapteurService.getTypeCapteurById(id);
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
        TypeCapteur savedTypeCapteur = typeCapteurService.saveTypeCapteur(typeCapteur);
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
        TypeCapteur updatedTypeCapteur = typeCapteurService.updateTypeCapteur(typeCapteur);
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
        TypeCapteur typeCapteur = typeCapteurService.getTypeCapteurById(id);
        if (typeCapteur == null) {
            return ResponseEntity.badRequest().build();
        }
        typeCapteurService.deleteTypeCapteurById(id);
        return ResponseEntity.ok("Type capteur supprimé avec succès");
    }
}