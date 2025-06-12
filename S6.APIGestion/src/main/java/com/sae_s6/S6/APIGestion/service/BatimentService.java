package com.sae_s6.S6.APIGestion.service;

import com.sae_s6.S6.APIGestion.entity.Batiment;
import com.sae_s6.S6.APIGestion.repository.BatimentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BatimentService {
	private final BatimentRepo batimentRepo;

	@Autowired
	public BatimentService(BatimentRepo batimentRepo) {
		this.batimentRepo = batimentRepo;
	}

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
		if (batiment.getTitre() == null || batiment.getTitre().isEmpty()) {
			throw new IllegalArgumentException("Le titre du bâtiment est obligatoire.");
		}
		return batimentRepo.save(batiment);
	}

	// Mettre à jour un bâtiment existant
	public Batiment updateBatiment(Integer id, Batiment updated) {
		return batimentRepo.findById(id).map(existingBatiment -> {
			if (updated.getTitre() != null && !updated.getTitre().isEmpty()) {
				existingBatiment.setTitre(updated.getTitre());
			}
			if (updated.getSalles() != null) {
				existingBatiment.setSalles(updated.getSalles());
			}
			return batimentRepo.save(existingBatiment);
		}).orElseThrow(() -> new IllegalArgumentException("Bâtiment avec l'ID " + id + " non trouvé."));
	}

	// Supprimer un bâtiment par son ID
	public void deleteBatiment(Integer id) {
		if (!batimentRepo.existsById(id)) {
			throw new IllegalArgumentException("Bâtiment avec l'ID " + id + " non trouvé.");
		}
		batimentRepo.deleteById(id);
	}
}