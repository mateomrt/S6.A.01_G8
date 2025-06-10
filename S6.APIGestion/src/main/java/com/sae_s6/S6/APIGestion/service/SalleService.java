package com.sae_s6.S6.APIGestion.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sae_s6.S6.APIGestion.repository.SalleRepo;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.sae_s6.S6.APIGestion.repository.SalleRepo;
import com.sae_s6.S6.APIGestion.entity.Salle;

@Service
@RequiredArgsConstructor
@Slf4j
public class SalleService {

    private final SalleRepo salleRepo;

    public List<Salle> getAllAuteurs() {
        return salleRepo.findAll();
    }

    /**
     * 
     * @param id
     * @return
     */

    public Salle getAuteurById(Integer id) {
        Optional<Salle> optionalSalle = salleRepo.findById(id);
        if (optionalSalle.isPresent()) {
            return optionalSalle.get();
        }

        log.debug("salle with id: {} doesn't exist", id);
        return null;
    }

       /**
         * 
         * @param salle
         * @return
         */

    public Salle saveSalle(Salle salle) {
        return salleRepo.save(salle);
    }

    
    /**
     * 
     * @param salle
     * @return
     */

    public Salle updateSalle(Salle salle) {
        return salleRepo.save(salle);
    }

     /**
     * 
     * @param id
     * @return
     */

    public void deleteSalleById(Integer id) {
        salleRepo.deleteById(id);
        log.debug("Salle with id: {} deleted successfully", id);
    }

   
}
