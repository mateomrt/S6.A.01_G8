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

import com.sae_s6.S6.APIGestion.entity.TypeEquipement;
import com.sae_s6.S6.APIGestion.entity.TypeSalle;
import com.sae_s6.S6.APIGestion.service.TypeEquipementService;
import com.sae_s6.S6.APIGestion.service.TypeSalleService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/typeequipement")
@RequiredArgsConstructor
@Validated
public class TypeEquipementController {
    private final TypeEquipementService typeEquipementService;

     /**
  * Cette méthode est appelée lors d’une requête GET.
  * URL: localhost:8080/api/typeequipement/
  * but: Récupère toute les TypeEquipement dans la table TypeEquipement
  * @return List des TypeEquipements 
  */

    @GetMapping("/")
    public ResponseEntity<List<TypeEquipement>> getAllTypeEquipements(){
        List<TypeEquipement> typeEquipements = typeEquipementService.getAllTypeEquipements();  
        if (typeEquipements == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(typeEquipements);
    }


             /**
     * Cette méthode est appelée lors d’une requête GET.
    * URL: localhost:8080/api/typeequipement/1 (1 ou tout autre id)
    * But: Récupère la typeequipement avec l’id associé.
    * @param id - typeequipement id
    * @return typeequipement avec l’id associé.
    */
    @GetMapping("/{id}")
    public ResponseEntity<TypeEquipement> getTypeEquipementById(@PathVariable("id") Integer id) {
        TypeEquipement typeEquipement = typeEquipementService.getTypeEquipementById(id);   
        if (typeEquipement == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(typeEquipement);
    }


    
            /**
     * Cette méthode est appelée lors d’une requête POST.
    * URL: localhost:8080/api/typeequipement/
    * Purpose: Création d’une entité typeEquipement
    * @param typeEquipement – le body de la requête est une entité typeEquipement
    * @return entité typeEquipement créée
    */
    @PostMapping("/")
    public ResponseEntity<TypeEquipement> saveTypeEquipement(@RequestBody TypeEquipement typeEquipemennt) {
        TypeEquipement savedTypeEquipement = typeEquipementService.saveTypeEquipement(typeEquipemennt);
        if (savedTypeEquipement == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(savedTypeEquipement);
    }



             /**
     * Cette méthode est appelée lors d’une requête PUT.
    * URL: localhost:8080/api/typeequipement/
    * Purpose: Met à jour une entité typeEquipement
    * @param typeEquipement - entité typeEquipement à mettre à jour.
    * @return  entité typeEquipement à mise à jour
    */
    @PutMapping("/")
    public ResponseEntity<TypeEquipement> updateTypeEquipement(@RequestBody TypeEquipement typeEquipement)
    {
        TypeEquipement updatedTypeEquipement = typeEquipementService.updateTypeEquipement(typeEquipement);
        if (updatedTypeEquipement == null) {
            return ResponseEntity.badRequest().build();
        }
        
        return ResponseEntity.ok(updatedTypeEquipement);
    }


        /**
     * Cette méthode est appelée lors d’une requête DELETE.
    * URL: localhost:8080/biblio/api/typeequipement/1 (1 ou tout autre id)
    * Purpose: Supprime une entité typeEquipement
    * @param id - l’id du typeEquipement à supprimer
    * @return un message String indiquant que l’enregistrement a été supprimé avec succès.
    */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTypeEquipementById(@PathVariable("id") Integer id)
    {
        TypeEquipement typeEquipement = typeEquipementService.getTypeEquipementById(id);
        if (typeEquipement == null) {
            return ResponseEntity.badRequest().build();
        }
    
        typeEquipementService.deleteTypeEquipementById(id);
        return ResponseEntity.ok("Type équipement supprimé avec succès");
    }
}
