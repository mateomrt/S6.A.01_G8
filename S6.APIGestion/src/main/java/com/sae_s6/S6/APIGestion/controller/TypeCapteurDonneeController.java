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
import com.sae_s6.S6.APIGestion.service.TypeCapteurDonneeService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/type_capteur_donnee")
@RequiredArgsConstructor
@Validated
public class TypeCapteurDonneeController {

    private final TypeCapteurDonneeService typeCapteurDonneeService;

     /**
  * Cette méthode est appelée lors d’une requête GET.
  * URL: localhost:8080/api/type_capteur_donnee/
  * but: Récupère toute les Donnees dans la table Donnees
  * @return List des Donnees 
  */

    @GetMapping("/")
    public ResponseEntity<List<TypeCapteurDonnee>> getAllDonnees(){
        List<TypeCapteurDonnee> Donnees = typeCapteurDonneeService.getAllTypeCapteurDonnee();  
        if (Donnees == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(Donnees);
    }



            /**
     * Cette méthode est appelée lors d’une requête GET.
    * URL: localhost:8080/api/type_capteur_donnee/1 (1 ou tout autre id)
    * But: Récupère la Donnee avec l’id associé.
    * @param id - Donnee id
    * @return Donnee avec l’id associé.
    */
    @GetMapping("/{id}")
    public ResponseEntity<TypeCapteurDonnee> getDonneeById(@PathVariable("id") Integer id) {
        TypeCapteurDonnee Donnee = typeCapteurDonneeService.getTypeCapteurDonneeById(id);   
        if (Donnee == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(Donnee);
    }


            /**
     * Cette méthode est appelée lors d’une requête POST.
    * URL: localhost:8080/api/type_capteur_donnee/
    * Purpose: Création d’une entité Donnee
    * @param Donnee – le body de la requête est une entité Donnee
    * @return entité Donnee créée
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
    * Purpose: Met à jour une entité Donnee
    * @param Donnee - entité Donnee à mettre à jour.
    * @return  entité Donnee à mise à jour
    */
    @PutMapping("/")
    public ResponseEntity<TypeCapteurDonnee> updateDonnee(@RequestBody TypeCapteurDonnee Donnee)
    {
        TypeCapteurDonnee updatedDonnee = typeCapteurDonneeService.updateTypeCapteurDonnee(Donnee);
        if (updatedDonnee == null) {
            return ResponseEntity.badRequest().build();
        }
        
        return ResponseEntity.ok(updatedDonnee);
    }


        /**
     * Cette méthode est appelée lors d’une requête DELETE.
    * URL: localhost:8080/biblio/api/type_capteur_donnee/ (1 ou tout autre id)
    * Purpose: Supprime une entité Donnee
    * @param id - l’id du Donnee à supprimer
    * @return un message String indiquant que l’enregistrement a été supprimé avec succès.
    */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDonneeById(@PathVariable("id") Integer id)
    {
        TypeCapteurDonnee Donnee = typeCapteurDonneeService.getTypeCapteurDonneeById(id);
        if (Donnee == null) {
            return ResponseEntity.badRequest().build();
        }
    
        typeCapteurDonneeService.deleteTypeCapteurDonneeById(id);
        return ResponseEntity.ok("Donnee supprimée avec succès");
    }


}
