package com.sae_s6.S6.APIGestion.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import com.sae_s6.S6.APIGestion.repository.SalleRepo;
import java.util.List;
import java.util.Optional;
import com.sae_s6.S6.APIGestion.entity.Salle;

@Service
@RequiredArgsConstructor
@Slf4j
public class SalleService {

    private final SalleRepo salleRepo;

    public List<Salle> getAllSalles() {
        List<Salle> salles = salleRepo.findAll();
        log.debug("Liste des salles récupérées: {}", salles);
        return salles;
    }

    /**
     * 
     * @param id
     * @return
     */

    public Salle getSalleById(Integer id) {
        Optional<Salle> optionalSalle = salleRepo.findById(id);
        if (optionalSalle.isPresent()) {
            log.debug("Détails de la salle trouvée: {}", optionalSalle.get());
            return optionalSalle.get();
        }
        log.warn("Aucune salle trouvée avec l'id: {}", id);
        return null;
    }

       /**
         * 
         * @param salle
         * @return
         */

    public Salle saveSalle(Salle salle) {
        Salle savedSalle = salleRepo.save(salle);
        log.info("Salle sauvegardée avec succès avec l'id: {}", savedSalle.getId());
        log.debug("Détails de la salle sauvegardée: {}", savedSalle);
        return savedSalle;
    }

    
    /**
     * 
     * @param salle
     * @return
     */

    public Salle updateSalle(Salle salle) {
        Salle updatedSalle = salleRepo.save(salle);
        log.info("Salle mise à jour avec succès avec l'id: {}", updatedSalle.getId());
        log.debug("Détails de la salle après mise à jour: {}", updatedSalle);
        return updatedSalle;
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

    public List<Salle> getSallesByLibelleSalle(String libelleSalle) {
        return salleRepo.findByLibelleSalle(libelleSalle);
    }
    public List<Salle> getSallesByLibelleSalleLike(String libelleSalle) {
        return salleRepo.findByLibelleSalleLike(libelleSalle);
    }   
   
}
