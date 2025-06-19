package com.sae_s6.S6.APIGestion.controller;

import com.sae_s6.S6.APIGestion.entity.Capteur;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.sae_s6.S6.APIGestion.service.CapteurService;

import java.util.List;

@RestController
@RequestMapping("/api/capteur")
@RequiredArgsConstructor
@Validated
public class CapteurController {
    private final CapteurService CapteurService;

     /**
  * Cette méthode est appelée lors d’une requête GET.
  * URL: localhost:8080/api/Capteur/
  * but: Récupère toute les équipements dans la table Capteur
  * @return List des équipements 
  */

    @GetMapping("/")
    public ResponseEntity<List<Capteur>> getAllCapteurs(){
        List<Capteur> Capteurs = CapteurService.getAllCapteurs();  
        if (Capteurs == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(Capteurs);
    }



            /**
     * Cette méthode est appelée lors d’une requête GET.
    * URL: localhost:8080/api/Capteur/1 (1 ou tout autre id)
    * But: Récupère l'équipement avec l’id associé.
    * @param id - id de l'équipement
    * @return équipement avec l’id associé.
    */
    @GetMapping("/{id}")
    public ResponseEntity<Capteur> getCapteurById(@PathVariable("id") Integer id) {
        Capteur Capteur = CapteurService.getCapteurById(id);   
        if (Capteur == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(Capteur);
    }


            /**
     * Cette méthode est appelée lors d’une requête POST.
    * URL: localhost:8080/api/Capteur/
    * Purpose: Création d’une entité Capteur
    * @param Capteur – le body de la requête est une entité équipement
    * @return entité équipement créé
    */
    @PostMapping("/")
    public ResponseEntity<Capteur> saveCapteur(@RequestBody Capteur Capteur) {
        Capteur savedCapteur = CapteurService.saveCapteur(Capteur);
        if (savedCapteur == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(savedCapteur);
    }



          /**
     * Cette méthode est appelée lors d’une requête PUT.
    * URL: localhost:8080/api/Capteur/
    * Purpose: Met à jour une entité Salle
    * @param Capteur - entité équipement à mettre à jour.
    * @return  entité équipement à mise à jour
    */
    @PutMapping("/")
    public ResponseEntity<Capteur> updateCapteur(@RequestBody Capteur capteur) {
        Capteur updatedCapteur = CapteurService.updateCapteur(capteur);
        if (updatedCapteur == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(updatedCapteur);
    }


        /**
     * Cette méthode est appelée lors d’une requête DELETE.
    * URL: localhost:8080/api/Capteur/{id}
    * Purpose: Supprime une entité équipement
    * @param id - l’id de l'équipement à supprimer
    * @return un message String indiquant que l’enregistrement a été supprimé avec succès.
    */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCapteurById(@PathVariable("id") Integer id)
    {
        Capteur Capteur = CapteurService.getCapteurById(id);
        if (Capteur == null) {
            return ResponseEntity.badRequest().build();
        }
    
        CapteurService.deleteCapteurById(id);
        return ResponseEntity.ok("Capteur supprimé avec succès");
    }
}