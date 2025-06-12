package com.sae_s6.S6.APIGestion.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.sae_s6.S6.APIGestion.entity.Mur;
import com.sae_s6.S6.APIGestion.service.MurService;


@RestController
@RequestMapping("/murs")
public class MurController {
    
    private final MurService murService;

    @Autowired
    public MurController(MurService murService) {
        this.murService = murService;
    }


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
