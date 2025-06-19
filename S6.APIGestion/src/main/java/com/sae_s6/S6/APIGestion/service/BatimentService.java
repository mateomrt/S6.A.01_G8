package com.sae_s6.S6.APIGestion.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.sae_s6.S6.APIGestion.entity.Batiment;
import com.sae_s6.S6.APIGestion.repository.BatimentRepo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Service pour la gestion des bâtiments.
 * Fournit des méthodes pour effectuer des opérations CRUD sur les entités Batiment.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class BatimentService {
    private final BatimentRepo batimentRepo;

    /**
     * Récupère tous les bâtiments.
     *
     * @return Une liste de tous les bâtiments.
     */
    public List<Batiment> getAllBatiments() {
        List<Batiment> batiments = batimentRepo.findAll();
        log.debug("Liste des bâtiments récupérés: {}", batiments);
        return batiments;
    }

    /**
     * Récupère un bâtiment par son ID.
     *
     * @param id L'identifiant du bâtiment.
     * @return Un bâtiment correspondant ou null s'il n'est pas trouvé.
     */
    public Batiment getBatimentById(Integer id) {        
       Optional<Batiment> optionalBatiment = batimentRepo.findById(id);
        if (optionalBatiment.isPresent()) {
            log.debug("Détails du bâtiment trouvé: {}", optionalBatiment.get());
            return optionalBatiment.get();
        }
        log.warn("Aucun bâtiment trouvé avec l'id: {}", id);
        return null;
    }

    /**
     * Crée un nouveau bâtiment.
     *
     * @param batiment L'entité Batiment à créer.
     * @return Le bâtiment créé.
     * @throws IllegalArgumentException Si le libellé du bâtiment est null ou vide.
     */
    public Batiment saveBatiment(Batiment batiment) {
        if (batiment.getLibelleBatiment() == null || batiment.getLibelleBatiment().isEmpty()) {
            throw new IllegalArgumentException("Le libellé du bâtiment est obligatoire.");
        }
        return batimentRepo.save(batiment);
    }

    /**
     * Met à jour un bâtiment existant.
     *
     * @param updated Les nouvelles données du bâtiment.
     * @return Le bâtiment mis à jour.
     */
	// Mettre à jour un bâtiment existant
	public Batiment updateBatiment(Batiment updated) {		
        // Sauvegarde directement les nouvelles données du bâtiment.
		Batiment updatedBatiment = batimentRepo.save(updated);
        log.info("Bâtiment mis à jour avec succès avec l'id: {}", updatedBatiment.getId());
        log.debug("Détails du bâtiment après mise à jour: {}", updatedBatiment);
        return updatedBatiment;
    }

    /**
     * Supprime un bâtiment par son ID.
     *
     * @param id L'identifiant du bâtiment à supprimer.
     * @throws IllegalArgumentException Si le bâtiment avec l'ID donné n'est pas trouvé.
     */
    public void deleteBatimentById(Integer id) {
        if (!batimentRepo.existsById(id)) {
            throw new IllegalArgumentException("Bâtiment avec l'ID " + id + " non trouvé.");
        }
        batimentRepo.deleteById(id);
        log.debug("Bâtiment avec id: {} supprimé avec succès", id);

    }

    public List<Batiment> getByLibelleBatimentContainingIgnoreCase(String libelleBatiment) {
        return batimentRepo.findByLibelleBatimentContainingIgnoreCase(libelleBatiment);
    }
}