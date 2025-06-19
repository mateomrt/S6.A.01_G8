package com.sae_s6.S6.APIGestion.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.sae_s6.S6.APIGestion.entity.Batiment;
import com.sae_s6.S6.APIGestion.repository.BatimentRepo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class BatimentService {
	private final BatimentRepo batimentRepo;

	

	// Récupérer tous les bâtiments
	public List<Batiment> getAllBatiments() {
		return batimentRepo.findAll();
	}

	// Récupérer un bâtiment par son ID
	public Optional<Batiment> getBatimentById(Integer id) {
		return batimentRepo.findById(id);
	}

	// Créer un nouveau bâtiment
	public Batiment createBatiment(Batiment batiment) {
		if (batiment.getLibelleBatiment() == null || batiment.getLibelleBatiment().isEmpty()) {
			throw new IllegalArgumentException("Le titre du bâtiment est obligatoire.");
		}
		return batimentRepo.save(batiment);
	}

	// Mettre à jour un bâtiment existant
	public Batiment updateBatiment(Batiment updated) {
		// return batimentRepo.findById(id).map(existingBatiment -> {
		// 	if (updated.getLibelleBatiment() != null && !updated.getLibelleBatiment().isEmpty()) {
		// 		existingBatiment.setLibelleBatiment(updated.getLibelleBatiment());
		// 	}
		// 	// if (updated.getSalles() != null) {
		// 	// 	existingBatiment.setSalles(updated.getSalles());
		// 	// }
		// 	return batimentRepo.save(updated);
		// }).orElseThrow(() -> new IllegalArgumentException("Bâtiment avec l'ID " + id + " non trouvé."));
		
		Batiment updatedBatiment = batimentRepo.save(updated);
        log.info("Bâtiment mis à jour avec succès avec l'id: {}", updatedBatiment.getId());
        log.debug("Détails du Bâtiment après mise à jour: {}", updatedBatiment);
        return updatedBatiment;

	
	}

	// Supprimer un bâtiment par son ID
	public void deleteBatiment(Integer id) {
		if (!batimentRepo.existsById(id)) {
			throw new IllegalArgumentException("Bâtiment avec l'ID " + id + " non trouvé.");
		}
		batimentRepo.deleteById(id);
	}
}