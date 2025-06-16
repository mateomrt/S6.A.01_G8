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

import com.sae_s6.S6.APIGestion.entity.Mur;
import com.sae_s6.S6.APIGestion.service.MurService;

import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/murs")
@RequiredArgsConstructor
@Validated
public class MurController {
    
    private final MurService murService;


    @GetMapping
    public ResponseEntity<List<Mur>> getAllMurs() {
        return ResponseEntity.ok(murService.getAllMurs());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Mur> getMurById(@PathVariable Integer id) {
        return murService.getMurById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Mur> createMur(@RequestBody Mur mur) {
        return ResponseEntity.ok(murService.createMur(mur));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Mur> updateMur(@PathVariable Integer id, @RequestBody Mur updated) {
        Mur updatedMur = murService.updateMur(id, updated);
        if (updatedMur != null) {
            return ResponseEntity.ok(updatedMur);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMur(@PathVariable Integer id) {
        murService.deleteMur(id);
        return ResponseEntity.noContent().build();
    }
}
