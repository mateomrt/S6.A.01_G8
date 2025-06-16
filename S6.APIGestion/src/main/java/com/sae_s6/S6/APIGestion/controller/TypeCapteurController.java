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

import com.sae_s6.S6.APIGestion.entity.TypeCapteur;
import com.sae_s6.S6.APIGestion.service.TypeCapteurService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/typeCapteur")
@RequiredArgsConstructor
@Validated
public class TypeCapteurController {
    private final TypeCapteurService typeCapteurService;

     /**
  * Cette méthode est appelée lors d’une requête GET.
  * URL: localhost:8080/api/typeCapteur/
  * but: Récupère toute les TypeCapteur dans la table TypeCapteur
  * @return List des TypeCapteurs 
  */

    @GetMapping("/")
    public ResponseEntity<List<TypeCapteur>> getAllTypeCapteurs(){
        List<TypeCapteur> typeCapteurs = typeCapteurService.getAllTypeCapteurs();  
        if (typeCapteurs == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(typeCapteurs);
    }


             /**
     * Cette méthode est appelée lors d’une requête GET.
    * URL: localhost:8080/api/typeCapteur/1 (1 ou tout autre id)
    * But: Récupère la typeCapteur avec l’id associé.
    * @param id - typeCapteur id
    * @return typeCapteur avec l’id associé.
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
     * Cette méthode est appelée lors d’une requête POST.
    * URL: localhost:8080/api/typeCapteur/
    * Purpose: Création d’une entité typeCapteur
    * @param typeCapteur – le body de la requête est une entité typeCapteur
    * @return entité typeCapteur créée
    */
    @PostMapping("/")
    public ResponseEntity<TypeCapteur> saveTypeCapteur(@RequestBody TypeCapteur typeEquipemennt) {
        TypeCapteur savedTypeCapteur = typeCapteurService.saveTypeCapteur(typeEquipemennt);
        if (savedTypeCapteur == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(savedTypeCapteur);
    }



             /**
     * Cette méthode est appelée lors d’une requête PUT.
    * URL: localhost:8080/api/typeCapteur/
    * Purpose: Met à jour une entité typeCapteur
    * @param typeCapteur - entité typeCapteur à mettre à jour.
    * @return  entité typeCapteur à mise à jour
    */
    @PutMapping("/")
    public ResponseEntity<TypeCapteur> updateTypeCapteur(@RequestBody TypeCapteur typeCapteur)
    {
        TypeCapteur updatedTypeCapteur = typeCapteurService.updateTypeCapteur(typeCapteur);
        if (updatedTypeCapteur == null) {
            return ResponseEntity.badRequest().build();
        }
        
        return ResponseEntity.ok(updatedTypeCapteur);
    }


        /**
     * Cette méthode est appelée lors d’une requête DELETE.
    * URL: localhost:8080/biblio/api/typeCapteur/1 (1 ou tout autre id)
    * Purpose: Supprime une entité typeCapteur
    * @param id - l’id du typeCapteur à supprimer
    * @return un message String indiquant que l’enregistrement a été supprimé avec succès.
    */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTypeCapteurById(@PathVariable("id") Integer id)
    {
        TypeCapteur typeCapteur = typeCapteurService.getTypeCapteurById(id);
        if (typeCapteur == null) {
            return ResponseEntity.badRequest().build();
        }
    
        typeCapteurService.deleteTypeCapteurById(id);
        return ResponseEntity.ok("Type équipement supprimé avec succès");
    }
}
