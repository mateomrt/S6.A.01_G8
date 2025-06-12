package com.sae_s6.S6.APIGestion.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.sae_s6.S6.APIGestion.entity.Batiment;
import com.sae_s6.S6.APIGestion.repository.BatimentRepo;
import java.util.List;
import java.util.Optional;

@Service
public class BatimentService {
    private final BatimentRepo batimentRepo;

    @Autowired
    public BatimentService(BatimentRepo batimentRepo) {
        this.batimentRepo = batimentRepo;
    }

    public List<Batiment> getAllBatiments() {
        return batimentRepo.findAll();
    }

    public Optional<Batiment> getBatimentById(Integer id) {
        return batimentRepo.findById(id);
    }

    public Batiment createBatiment(Batiment batiment) {
        return batimentRepo.save(batiment);
    }

    public Batiment updateBatiment(Integer id, Batiment updated) {
        return batimentRepo.findById(id).map(b -> {
            b.setNom(updated.getNom());
            return batimentRepo.save(b);
        }).orElse(null);
    }

    public void deleteBatiment(Integer id) {
        batimentRepo.deleteById(id);
    }
}
