package com.sae_s6.S6.APIGestion.controller;

import com.sae_s6.S6.APIGestion.entity.Batiment;
import com.sae_s6.S6.APIGestion.service.BatimentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/batiments")
public class BatimentController {

    private final BatimentService batimentService;

    @Autowired
    public BatimentController(BatimentService batimentService) {
        this.batimentService = batimentService;
    }

    @GetMapping
    public ResponseEntity<List<Batiment>> getAllBatiments() {
        return ResponseEntity.ok(batimentService.getAllBatiments());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Batiment> getBatimentById(@PathVariable Integer id) {
        return batimentService.getBatimentById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Batiment> createBatiment(@RequestBody Batiment batiment) {
        return ResponseEntity.ok(batimentService.createBatiment(batiment));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Batiment> updateBatiment(@PathVariable Integer id, @RequestBody Batiment updated) {
        Batiment result = batimentService.updateBatiment(id, updated);
        if (result != null) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBatiment(@PathVariable Integer id) {
        batimentService.deleteBatiment(id);
        return ResponseEntity.ok().build();
    }
}
