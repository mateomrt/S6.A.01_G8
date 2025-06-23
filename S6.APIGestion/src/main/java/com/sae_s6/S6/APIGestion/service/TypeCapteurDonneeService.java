package com.sae_s6.S6.APIGestion.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.sae_s6.S6.APIGestion.entity.Donnee;
import com.sae_s6.S6.APIGestion.entity.TypeCapteur;
import com.sae_s6.S6.APIGestion.entity.TypeCapteurDonnee;
import com.sae_s6.S6.APIGestion.entity.TypeCapteurDonneeEmbedId;
import com.sae_s6.S6.APIGestion.repository.DonneeRepo;
import com.sae_s6.S6.APIGestion.repository.TypeCapteurDonneeRepo;
import com.sae_s6.S6.APIGestion.repository.TypeCapteurRepo;

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
    private final DonneeRepo donneeRepo;
    private final TypeCapteurRepo typeCapteurRepo;

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
        if (optionalDonnee.isEmpty()) {
            log.warn("Aucune association TypeCapteurDonnee trouvée avec l'id composite: {}", id);
            return null;
        }
        log.info("Association TypeCapteurDonnee récupérée avec succès pour l'id composite: {}", id);
        return optionalDonnee.get();
    }

    /**
     * Crée une nouvelle association TypeCapteurDonnee.
     *
     * @param typeCapteurDonnee L'entité TypeCapteurDonnee à créer.
     * @return L'association TypeCapteurDonnee créée.
     */
    public TypeCapteurDonnee saveTypeCapteurDonnee(TypeCapteurDonnee typeCapteurDonnee) {
        // Étape 1 : Hydrater les entités de navigation à partir des identifiants
        Donnee donnee = donneeRepo.findById(typeCapteurDonnee.getId().getIdDonnee())
                .orElseThrow(() -> new IllegalArgumentException("Donnee non trouvée avec l'id spécifié."));
        TypeCapteur typeCapteur = typeCapteurRepo.findById(typeCapteurDonnee.getId().getIdTypeCapteur())
                .orElseThrow(() -> new IllegalArgumentException("TypeCapteur non trouvé avec l'id spécifié."));
    
        // Étape 2 : Associer les entités hydratées
        typeCapteurDonnee.setDonneeNavigation(donnee);
        typeCapteurDonnee.setTypeCapteurNavigation(typeCapteur);
    
        // Étape 3 : Sauvegarder l'association
        TypeCapteurDonnee savedDonnee = typeCapteurDonneeRepo.save(typeCapteurDonnee);
        log.info("Association TypeCapteurDonnee sauvegardée avec succès avec l'id composite: {}", savedDonnee.getId());
        log.debug("Détails de l'association TypeCapteurDonnee sauvegardée: {}", savedDonnee);
    
        return savedDonnee;
    }

    /**
     * Met à jour une association TypeCapteurDonnee existante.
     * Permet de mettre à jour le type de capteur, la donnée, ou les deux.
     *
     * @param id L'identifiant composite existant (TypeCapteurDonneeEmbedId).
     * @param newIdTypeCapteur L'identifiant du nouveau type de capteur (peut être null si non modifié).
     * @param newIdDonnee L'identifiant de la nouvelle donnée (peut être null si non modifié).
     * @return La nouvelle association TypeCapteurDonnee créée.
     */
    public TypeCapteurDonnee updateTypeCapteurOrDonnee(
            TypeCapteurDonneeEmbedId id,
            Integer newIdDonnee,
            Integer newIdTypeCapteur) {
        // Étape 1 : Récupérer l'association existante
        Optional<TypeCapteurDonnee> optionalDonnee = typeCapteurDonneeRepo.findById(id);

        if (!optionalDonnee.isPresent()) {
            log.warn("Aucune association TypeCapteurDonnee trouvée avec l'id composite: {}", id);
            throw new IllegalArgumentException("Association non trouvée pour l'id composite spécifié.");
        }

        // Étape 2 : Supprimer l'association existante
        log.debug("Suppression de l'association existante avec l'id composite: {}", id);
        typeCapteurDonneeRepo.deleteById(id);
        log.info("Association TypeCapteurDonnee avec l'id composite: {} supprimée avec succès", id);

        // Étape 3 : Créer une nouvelle association avec les nouveaux identifiants
        TypeCapteurDonneeEmbedId newId = new TypeCapteurDonneeEmbedId(
                newIdTypeCapteur != null ? newIdTypeCapteur : id.getIdTypeCapteur(),
                newIdDonnee != null ? newIdDonnee : id.getIdDonnee()
        );

        TypeCapteurDonnee newDonnee = new TypeCapteurDonnee();
        newDonnee.setId(newId);

        // Réutilisation de la logique d'hydratation depuis la méthode saveTypeCapteurDonnee
        return saveTypeCapteurDonnee(newDonnee);
    }

    /**
     * Supprime une association TypeCapteurDonnee par sa clé composite.
     *
     * @param id La clé composite (TypeCapteurDonneeEmbedId).
     */
    public void deleteTypeCapteurDonneeById(TypeCapteurDonneeEmbedId id) {
        log.info("Tentative de suppression de l'association avec l'id composite : {}", id);
        Optional<TypeCapteurDonnee> optionalDonnee = typeCapteurDonneeRepo.findById(id);
        if (optionalDonnee.isEmpty()) {
            log.warn("Aucune association trouvée avec l'id composite : {}", id);
            return;
        }
        typeCapteurDonneeRepo.deleteById(id);
        log.info("Association supprimée avec succès pour l'id composite : {}", id);
    }
}