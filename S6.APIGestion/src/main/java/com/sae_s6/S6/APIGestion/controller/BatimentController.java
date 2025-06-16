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

import com.sae_s6.S6.APIGestion.entity.Batiment;
import com.sae_s6.S6.APIGestion.service.BatimentService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/batiment")
@RequiredArgsConstructor
@Validated
public class BatimentController {

    private final BatimentService batimentService;

    

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
