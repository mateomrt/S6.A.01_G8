package com.sae_s6.S6.APIGestion.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import com.sae_s6.S6.APIGestion.repository.TypeSalleRepo;
import com.sae_s6.S6.APIGestion.entity.TypeSalle;

@Service
@RequiredArgsConstructor
@Slf4j
public class TypeSalleService {

    private final TypeSalleRepo typeSalleRepo; 

    public List<TypeSalle> getAllTypeSalles() {
        List<TypeSalle> typeSalles = typeSalleRepo.findAll();
        log.debug("Liste des Type de salles récupérées: {}", typeSalles);
        return typeSalles;
    }


     /**
     * 
     * @param id
     * @return
     */

     public TypeSalle getTypeSalleById(Integer id) {
        Optional<TypeSalle> optionalTypeSalle = typeSalleRepo.findById(id);
        if (optionalTypeSalle.isPresent()) {
            log.debug("Détails de la salle trouvée: {}", optionalTypeSalle.get());
            return optionalTypeSalle.get();
        }
        log.warn("Aucune salle trouvée avec l'id: {}", id);
        return null;
    }

       /**
         * 
         * @param typeSalle
         * @return
         */

    public TypeSalle saveTypeSalle(TypeSalle typeSalle) {
        TypeSalle savedtypeSalle = typeSalleRepo.save(typeSalle);
        log.info("Salle sauvegardée avec succès avec l'id: {}", savedtypeSalle.getIdTypeSalle());
        log.debug("Détails de la salle sauvegardée: {}", savedtypeSalle);
        return savedtypeSalle;
    }

    
    /**
     * 
     * @param typeSalle
     * @return
     */

    public TypeSalle updateTypeSalle(TypeSalle typeSalle) {
        TypeSalle updatedtypeSalle = typeSalleRepo.save(typeSalle);
        log.info("Salle mise à jour avec succès avec l'id: {}", updatedtypeSalle.getIdTypeSalle());
        log.debug("Détails de la salle après mise à jour: {}", updatedtypeSalle);
        return updatedtypeSalle;
    }

     /**
     * 
     * @param id
     * @return
     */

    public void deleteTypeSalleById(Integer id) {
        typeSalleRepo.deleteById(id);
        log.debug("Salle with id: {} deleted successfully", id);
    }






}
