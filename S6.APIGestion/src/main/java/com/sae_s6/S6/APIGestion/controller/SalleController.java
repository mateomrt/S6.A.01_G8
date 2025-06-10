package com.sae_s6.S6.APIGestion.controller;




import com.sae_s6.S6.APIGestion.entity.Salle;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.sae_s6.S6.APIGestion.service.SalleService;

import java.util.List;

@RestController
@RequestMapping("/api/salle")
@RequiredArgsConstructor
@Validated
public class SalleController {

    private final SalleService salleService;

     /**
  * Cette méthode est appelée lors d’une requête GET.
  * URL: localhost:8080/api/salle/
  * but: Récupère toute les salles dans la table salles
  * @return List des salles 
  */

    @GetMapping("/")
    public ResponseEntity<List<Salle>> getAllSalles(){
        List<Salle> salles = salleService.getAllSalles();  
        if (salles == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(salles);
    }



            /**
     * Cette méthode est appelée lors d’une requête GET.
    * URL: localhost:8080/api/salle/1 (1 ou tout autre id)
    * But: Récupère la salle avec l’id associé.
    * @param id - salle id
    * @return salle avec l’id associé.
    */
    @GetMapping("/{id}")
    public ResponseEntity<Salle> getSalleById(@PathVariable("id") Integer id) {
        Salle salle = salleService.getSalleById(id);   
        if (salle == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(salle);
    }


            /**
     * Cette méthode est appelée lors d’une requête POST.
    * URL: localhost:8080/api/salle/
    * Purpose: Création d’une entité salle
    * @param Salle – le body de la requête est une entité Salle
    * @return entité salle créée
    */
    @PostMapping("/")
    public ResponseEntity<Salle> saveSalle(@RequestBody Salle salle) {
        Salle savedSalle = salleService.saveSalle(salle);
        if (savedSalle == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(savedSalle);
    }



          /**
     * Cette méthode est appelée lors d’une requête PUT.
    * URL: localhost:8080/api/salle/
    * Purpose: Met à jour une entité Salle
    * @param salle - entité Salle à mettre à jour.
    * @return  entité Salle à mise à jour
    */
    @PutMapping("/")
    public ResponseEntity<Salle> updateSalle(@RequestBody Salle salle)
    {
        Salle updatedSalle = salleService.updateSalle(salle);
        if (updatedSalle == null) {
            return ResponseEntity.badRequest().build();
        }
        
        return ResponseEntity.ok(updatedSalle);
    }


        /**
     * Cette méthode est appelée lors d’une requête DELETE.
    * URL: localhost:8080/biblio/api/salle/ (1 ou tout autre id)
    * Purpose: Supprime une entité Salle
    * @param id - l’id du salle à supprimer
    * @return un message String indiquant que l’enregistrement a été supprimé avec succès.
    */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSalleById(@PathVariable("id") Integer id)
    {
        Salle salle = salleService.getSalleById(id);
        if (salle == null) {
            return ResponseEntity.badRequest().build();
        }
    
        salleService.deleteSalleById(id);
        return ResponseEntity.ok("Salle supprimée avec succès");
    }


}
