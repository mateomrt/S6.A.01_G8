package com.sae_s6.S6.APIGestion.service;

import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.sae_s6.S6.APIGestion.entity.Mur;
import com.sae_s6.S6.APIGestion.entity.Salle;
import com.sae_s6.S6.APIGestion.entity.TypeCapteur;
import com.sae_s6.S6.APIGestion.entity.TypeSalle;
import com.sae_s6.S6.APIGestion.entity.Batiment;
import com.sae_s6.S6.APIGestion.entity.Capteur;
import com.sae_s6.S6.APIGestion.repository.MurRepo;
import com.sae_s6.S6.APIGestion.repository.SalleRepo;
import com.sae_s6.S6.APIGestion.repository.TypeCapteurRepo;
import com.sae_s6.S6.APIGestion.repository.TypeSalleRepo;
import com.sae_s6.S6.APIGestion.repository.BatimentRepo;
import com.sae_s6.S6.APIGestion.repository.CapteurRepo;

import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Optional;
import java.util.List;

/**
 * Classe de test unitaire pour le service CapteurService.
 * Utilise Mockito pour simuler les repositories.
 */
public class CapteurServiceTest {
    @Mock
    private SalleRepo salleRepo;

    @Mock
    private TypeSalleRepo typeSalleRepo;

    @Mock
    private MurRepo murRepo;

    @Mock
    private CapteurRepo capteurRepo;

    @Mock
    private BatimentRepo batimentRepo;

    @Mock
    private TypeCapteurRepo typeCapteurRepo;

    @InjectMocks
    private CapteurService capteurService;

    /**
     * Initialise les mocks avant chaque test.
     */
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Teste la récupération de tous les capteurs.
     */
    @Test
    public void testGetAllCapteurs() {
        Capteur capteur1 = new Capteur();
        Capteur capteur2 = new Capteur();
        when(capteurRepo.findAll()).thenReturn(Arrays.asList(capteur1, capteur2));

        List<Capteur> capteurs = capteurService.getAllCapteurs();

        assertEquals(2, capteurs.size());
        verify(capteurRepo, times(1)).findAll();
    }

    /**
     * Teste la récupération d'un capteur par son identifiant (cas trouvé).
     */
    @Test
    public void testGetCapteurById_Found() {
        Capteur capteur = new Capteur();
        capteur.setId(1);
        when(capteurRepo.findById(1)).thenReturn(Optional.of(capteur));

        Capteur result = capteurService.getCapteurById(1);

        assertNotNull(result);
        assertEquals(1, result.getId());
    }

    /**
     * Teste la récupération d'un capteur par son identifiant (cas non trouvé).
     */
    @Test
    public void testGetCapteurById_NotFound() {
        when(capteurRepo.findById(1)).thenReturn(Optional.empty());

        Capteur result = capteurService.getCapteurById(1);

        assertNull(result);
    }

    /**
     * Teste la sauvegarde d'un capteur.
     */
    @Test
    public void testSaveCapteur() {
        // Création des objets nécessaires
        Batiment batiment = new Batiment();
        batiment.setId(10);

        TypeSalle typeSalle = new TypeSalle();
        typeSalle.setId(20);

        Salle salle = new Salle();
        salle.setId(20);
        salle.setBatimentNavigation(batiment);
        salle.setTypeSalleNavigation(typeSalle);

        Mur mur = new Mur();
        mur.setSalleNavigation(salle);
        mur.setId(30);

        TypeCapteur typeCapteur = new TypeCapteur();
        typeCapteur.setId(20);

        
        Capteur capteur = new Capteur();        
        capteur.setMurNavigation(mur);
        capteur.setSalleNavigation(salle);
        capteur.setTypeCapteurNavigation(typeCapteur);

        Capteur savedCapteur = new Capteur();
        savedCapteur.setId(100); // ID généré automatiquement

        // Simulation des dépendances
        when(batimentRepo.findById(10)).thenReturn(Optional.of(batiment));
        when(typeSalleRepo.findById(20)).thenReturn(Optional.of(typeSalle));
        when(murRepo.findById(30)).thenReturn(Optional.of(mur));
        when(salleRepo.findById(20)).thenReturn(Optional.of(salle));
        when(typeCapteurRepo.findById(20)).thenReturn(Optional.of(typeCapteur));
        when(capteurRepo.save(any(Capteur.class))).thenReturn(savedCapteur);

        // Appel de la méthode à tester
        Capteur result = capteurService.saveCapteur(capteur);

        // Vérifications
        assertNotNull(result);
        assertEquals(100, result.getId()); // Vérifie que l'ID généré est correct
        verify(batimentRepo, times(2)).findById(10);
        verify(typeSalleRepo, times(2)).findById(20);
        verify(murRepo).findById(30);
        verify(salleRepo, times(2)).findById(20);
        verify(typeCapteurRepo).findById(20);
        verify(capteurRepo).save(capteur);
    }

    /**
     * Teste la mise à jour d'un capteur.
     */
    @Test
    public void testUpdateCapteur() {
        Capteur existingCapteur = new Capteur();
        existingCapteur.setId(10);
        existingCapteur.setLibelleCapteur("Ancien nom");

        Capteur updatedCapteur = new Capteur();
        updatedCapteur.setId(1);
        updatedCapteur.setLibelleCapteur("Nouveau nom");

        when(capteurRepo.save(updatedCapteur)).thenReturn(updatedCapteur);

        Capteur result = capteurService.updateCapteur(updatedCapteur);

        assertNotNull(result);
        assertEquals("Nouveau nom", result.getLibelleCapteur());
        verify(capteurRepo, times(1)).save(updatedCapteur);

    }

    /**
     * Teste la suppression d'un capteur par son identifiant.
     */
    @Test
    public void testDeleteCapteurById() {
        capteurService.deleteCapteurById(10);
        verify(capteurRepo, times(1)).deleteById(10);
    }

}
