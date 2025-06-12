package com.sae_s6.S6.APIGestion.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.sae_s6.S6.APIGestion.entity.Mur;
import com.sae_s6.S6.APIGestion.repository.MurRepo;


@Service
public class MurService {
    private final MurRepo murRepo;

    @Autowired
    public MurService(MurRepo murRepo) {
        this.murRepo = murRepo;
    }

    public List<Mur> getAllMurs() {
        return murRepo.findAll();
    }

    public Optional<Mur> getMurById(Integer id) {
        return murRepo.findById(id);
    }

    public Mur createMur(Mur mur) {
        return murRepo.save(mur);
    }

    public Mur updateMur(Integer id, Mur updated) {
        return murRepo.findById(id).map(m -> {
            m.setTitre(updated.getTitre());
            m.setHauteur(updated.getHauteur());
            m.setLongueur(updated.getLongueur());
            m.setOrientation(updated.getOrientation());
            m.setSalle(updated.getSalle());
            return murRepo.save(m);
        }).orElse(null);
    }

    public void deleteMur(Integer id) {
        murRepo.deleteById(id);
    }
}
