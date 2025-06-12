package com.sae_s6.S6.APIGestion.controller;

import com.sae_s6.S6.APIGestion.entity.Equipement;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.sae_s6.S6.APIGestion.service.EquipementService;

import java.util.List;

@RestController
@RequestMapping("/api/equipement")
@RequiredArgsConstructor
@Validated
public class EquipementController {
    private final EquipementService equipementService;

     /**
  * Cette méthode est appelée lors d’une requête GET.
  * URL: localhost:8080/api/equipement/
  * but: Récupère toute les équipements dans la table equipement
  * @return List des équipements 
  */

    @GetMapping("/")
    public ResponseEntity<List<Equipement>> getAllEquipements(){
        List<Equipement> equipements = equipementService.getAllEquipements();  
        if (equipements == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(equipements);
    }



            /**
     * Cette méthode est appelée lors d’une requête GET.
    * URL: localhost:8080/api/equipement/1 (1 ou tout autre id)
    * But: Récupère l'équipement avec l’id associé.
    * @param id - id de l'équipement
    * @return équipement avec l’id associé.
    */
    @GetMapping("/{id}")
    public ResponseEntity<Equipement> getEquipementById(@PathVariable("id") Integer id) {
        Equipement equipement = equipementService.getEquipementById(id);   
        if (equipement == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(equipement);
    }


            /**
     * Cette méthode est appelée lors d’une requête POST.
    * URL: localhost:8080/api/equipement/
    * Purpose: Création d’une entité equipement
    * @param equipement – le body de la requête est une entité équipement
    * @return entité équipement créé
    */
    @PostMapping("/")
    public ResponseEntity<Equipement> saveEquipement(@RequestBody Equipement equipement) {
        Equipement savedEquipement = equipementService.saveEquipement(equipement);
        if (savedEquipement == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(savedEquipement);
    }



          /**
     * Cette méthode est appelée lors d’une requête PUT.
    * URL: localhost:8080/api/equipement/
    * Purpose: Met à jour une entité Salle
    * @param equipement - entité équipement à mettre à jour.
    * @return  entité équipement à mise à jour
    */
    @PutMapping("/")
    public ResponseEntity<Equipement> updateEquipement(@RequestBody Equipement equipement)
    {
        Equipement updatedEquipement = equipementService.updateEquipement(equipement);
        if (updatedEquipement == null) {
            return ResponseEntity.badRequest().build();
        }
        
        return ResponseEntity.ok(updatedEquipement);
    }


        /**
     * Cette méthode est appelée lors d’une requête DELETE.
    * URL: localhost:8080/biblio/api/equipement/ (1 ou tout autre id)
    * Purpose: Supprime une entité équipement
    * @param id - l’id de l'équipement à supprimer
    * @return un message String indiquant que l’enregistrement a été supprimé avec succès.
    */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEquipementById(@PathVariable("id") Integer id)
    {
        Equipement equipement = equipementService.getEquipementById(id);
        if (equipement == null) {
            return ResponseEntity.badRequest().build();
        }
    
        equipementService.deleteEquipementById(id);
        return ResponseEntity.ok("Equipement supprimé avec succès");
    }
}
