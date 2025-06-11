package com.sae_s6.S6.APIGestion.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.sae_s6.S6.APIGestion.entity.Salle;
import com.sae_s6.S6.APIGestion.entity.TypeSalle;
import com.sae_s6.S6.APIGestion.service.TypeSalleService;



@RestController
@RequestMapping("/api/typesalle")
@RequiredArgsConstructor
@Validated
public class TypeSalleController {


    private final TypeSalleService typeSalleService;

     /**
  * Cette méthode est appelée lors d’une requête GET.
  * URL: localhost:8080/api/typesalle/
  * but: Récupère toute les Typesalles dans la table Typesalles
  * @return List des Typesalles 
  */

    @GetMapping("/")
    public ResponseEntity<List<TypeSalle>> getAllSalles(){
        List<TypeSalle> typeSalles = typeSalleService.getAllTypeSalles();  
        if (typeSalles == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(typeSalles);
    }


             /**
     * Cette méthode est appelée lors d’une requête GET.
    * URL: localhost:8080/api/typesalle/1 (1 ou tout autre id)
    * But: Récupère la typesalle avec l’id associé.
    * @param id - typesalle id
    * @return typesalle avec l’id associé.
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
     * Cette méthode est appelée lors d’une requête POST.
    * URL: localhost:8080/api/typeSalle/
    * Purpose: Création d’une entité typeSalle
    * @param typeSalle – le body de la requête est une entité typeSalle
    * @return entité typeSalle créée
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
     * Cette méthode est appelée lors d’une requête PUT.
    * URL: localhost:8080/api/typesalle/
    * Purpose: Met à jour une entité typeSalle
    * @param typeSalle - entité typeSalle à mettre à jour.
    * @return  entité typeSalle à mise à jour
    */
    @PutMapping("/")
    public ResponseEntity<TypeSalle> updateSalle(@RequestBody TypeSalle typeSalle)
    {
        TypeSalle updatedTypeSalle = typeSalleService.updateTypeSalle(typeSalle);
        if (updatedTypeSalle == null) {
            return ResponseEntity.badRequest().build();
        }
        
        return ResponseEntity.ok(updatedTypeSalle);
    }


        /**
     * Cette méthode est appelée lors d’une requête DELETE.
    * URL: localhost:8080/biblio/api/typesalle/1 (1 ou tout autre id)
    * Purpose: Supprime une entité typeSalle
    * @param id - l’id du typeSalle à supprimer
    * @return un message String indiquant que l’enregistrement a été supprimé avec succès.
    */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSalleById(@PathVariable("id") Integer id)
    {
        TypeSalle typeSalle = typeSalleService.getTypeSalleById(id);
        if (typeSalle == null) {
            return ResponseEntity.badRequest().build();
        }
    
        typeSalleService.deleteTypeSalleById(id);
        return ResponseEntity.ok("Salle supprimée avec succès");
    }


}
