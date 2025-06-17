package com.sae_s6.S6.APIGestion.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.sae_s6.S6.APIGestion.entity.Mur;
import com.sae_s6.S6.APIGestion.repository.MurRepo;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class MurService {
    private final MurRepo murRepo;

    

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
            m.setLibelleMur(updated.getLibelleMur());
            m.setHauteur(updated.getHauteur());
            m.setLongueur(updated.getLongueur());
            m.setOrientation(updated.getOrientation());
            m.setSalleNavigation(updated.getSalleNavigation());
            return murRepo.save(m);
        }).orElse(null);
    }

    public void deleteMur(Integer id) {
        murRepo.deleteById(id);
    }
}
