package com.sae_s6.S6.APIGestion.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.sae_s6.S6.APIGestion.entity.Donnee;
import com.sae_s6.S6.APIGestion.entity.TypeCapteur;
import com.sae_s6.S6.APIGestion.entity.TypeCapteurDonnee;
import com.sae_s6.S6.APIGestion.entity.TypeCapteurDonneeEmbedId;
import com.sae_s6.S6.APIGestion.repository.TypeCapteurDonneeRepo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Service pour la gestion des associations entre types de capteurs et données.
 * Fournit des méthodes pour effectuer des opérations CRUD sur les entités TypeCapteurDonnee.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class TypeCapteurDonneeService {

    private final TypeCapteurDonneeRepo typeCapteurDonneeRepo;

    /**
     * Récupère toutes les associations entre types de capteurs et données.
     *
     * @return Une liste de toutes les associations TypeCapteurDonnee.
     */
    public List<TypeCapteurDonnee> getAllTypeCapteurDonnee() {
        List<TypeCapteurDonnee> donnees = typeCapteurDonneeRepo.findAll();
        log.debug("Liste des associations TypeCapteurDonnee récupérées: {}", donnees);
        return donnees;
    }

    /**
     * Récupère une association TypeCapteurDonnee par sa clé composite.
     *
     * @param id La clé composite (TypeCapteurDonneeEmbedId).
     * @return L'association TypeCapteurDonnee ou null si elle n'est pas trouvée.
     */
    public TypeCapteurDonnee getTypeCapteurDonneeById(TypeCapteurDonneeEmbedId id) {
        Optional<TypeCapteurDonnee> optionalDonnee = typeCapteurDonneeRepo.findById(id);
        if (optionalDonnee.isPresent()) {
            log.debug("Détails de l'association TypeCapteurDonnee trouvée: {}", optionalDonnee.get());
            return optionalDonnee.get();
        }
        log.warn("Aucune association TypeCapteurDonnee trouvée avec l'id composite: {}", id);
        return null;
    }

    /**
     * Sauvegarde une nouvelle association TypeCapteurDonnee.
     *
     * @param donnee L'entité TypeCapteurDonnee à sauvegarder.
     * @return L'association TypeCapteurDonnee sauvegardée.
     */
    public TypeCapteurDonnee saveTypeCapteurDonnee(TypeCapteurDonnee donnee) {
        // Crée une donnee fictive
        // Donnee donnee = new Donnee();
        //donnee.setId(donnee_id);

        

        

        //TypeCapteurDonneeEmbedId id = new TypeCapteurDonneeEmbedId(donnee.getId().getIdDonnee(), donnee.getId().getIdTypeCapteur());

        // Crée un type capteur donnee fictif
        // TypeCapteurDonnee typeCapteurDonnee = new TypeCapteurDonnee();
        // typeCapteurDonnee.setDonneeNavigation(donnee);
        // typeCapteurDonnee.setTypeCapteurNavigation(typeCapteur);
        //donnee.setId(id);
        
        TypeCapteurDonnee savedDonnee = typeCapteurDonneeRepo.save(donnee);
        log.info("Association TypeCapteurDonnee sauvegardée avec succès avec l'id composite: {}", savedDonnee.getId());
        log.debug("Détails de l'association TypeCapteurDonnee sauvegardée: {}", savedDonnee);
        return savedDonnee;
    }

    /**
     * Met à jour une association TypeCapteurDonnee existante.
     * Supprime l'association existante et en crée une nouvelle avec les nouvelles données.
     *
     * @param donnee L'entité TypeCapteurDonnee avec les nouvelles données.
     * @return La nouvelle association TypeCapteurDonnee créée.
     */
    public TypeCapteurDonnee updateTypeCapteurDonnee(TypeCapteurDonnee donnee) {
        // Supprimer l'association existante
        TypeCapteurDonneeEmbedId id = donnee.getId();
        typeCapteurDonneeRepo.deleteById(id);
        log.info("Association TypeCapteurDonnee avec l'id composite: {} supprimée avec succès", id);

        // Créer une nouvelle association
        TypeCapteurDonnee newDonnee = typeCapteurDonneeRepo.save(donnee);
        log.info("Nouvelle association TypeCapteurDonnee créée avec succès avec l'id composite: {}", newDonnee.getId());
        log.debug("Détails de la nouvelle association TypeCapteurDonnee: {}", newDonnee);

        return newDonnee;
    }

    /**
     * Supprime une association TypeCapteurDonnee par sa clé composite.
     *
     * @param id La clé composite (TypeCapteurDonneeEmbedId).
     */
    public void deleteTypeCapteurDonneeById(TypeCapteurDonneeEmbedId id) {
        typeCapteurDonneeRepo.deleteById(id);
        log.debug("Association TypeCapteurDonnee avec l'id composite: {} supprimée avec succès", id);
    }
}