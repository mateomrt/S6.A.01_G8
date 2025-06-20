package com.sae_s6.S6.APIGestion.service;

import com.sae_s6.S6.APIGestion.entity.Batiment;
import com.sae_s6.S6.APIGestion.entity.Equipement;
import com.sae_s6.S6.APIGestion.entity.Mur;
import com.sae_s6.S6.APIGestion.entity.Salle;
import com.sae_s6.S6.APIGestion.entity.TypeEquipement;
import com.sae_s6.S6.APIGestion.entity.TypeSalle;
import com.sae_s6.S6.APIGestion.repository.BatimentRepo;
import com.sae_s6.S6.APIGestion.repository.EquipementRepo;
import com.sae_s6.S6.APIGestion.repository.MurRepo;
import com.sae_s6.S6.APIGestion.repository.SalleRepo;
import com.sae_s6.S6.APIGestion.repository.TypeEquipementRepo;
import com.sae_s6.S6.APIGestion.repository.TypeSalleRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Classe de test unitaire pour le service EquipementService.
 * Utilise Mockito pour simuler les repositories.
 */
public class EquipementServiceTest {

    @Mock
    private EquipementRepo equipementRepo;
    @Mock
    private SalleRepo salleRepo;
    @Mock
    private MurRepo murRepo;
    @Mock
    private TypeEquipementRepo typeEquipementRepo;
    @Mock
    private BatimentRepo batimentRepo;
    @Mock
    private TypeSalleRepo typeSalleRepo;

    @InjectMocks
    private EquipementService equipementService;

    /**
     * Initialise les mocks avant chaque test.
     */
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Teste la récupération de tous les équipements.
     */
    @Test
    void testGetAllEquipements() {
        Equipement e1 = new Equipement();
        Equipement e2 = new Equipement();
        when(equipementRepo.findAll()).thenReturn(Arrays.asList(e1, e2));

        List<Equipement> result = equipementService.getAllEquipements();

        assertEquals(2, result.size());
        verify(equipementRepo, times(1)).findAll();
    }

    /**
     * Teste la récupération d'un équipement par son identifiant (cas trouvé).
     */
    @Test
    void testGetEquipementById_Found() {
        Equipement equipement = new Equipement();
        equipement.setId(1);
        when(equipementRepo.findById(1)).thenReturn(Optional.of(equipement));

        Equipement result = equipementService.getEquipementById(1);

        assertNotNull(result);
        assertEquals(1, result.getId());
        verify(equipementRepo).findById(1);
    }

    /**
     * Teste la récupération d'un équipement par son identifiant (cas non trouvé).
     */
    @Test
    void testGetEquipementById_NotFound() {
        when(equipementRepo.findById(1)).thenReturn(Optional.empty());

        Equipement result = equipementService.getEquipementById(1);

        assertNull(result);
        verify(equipementRepo).findById(1);
    }

    /**
     * Teste la sauvegarde d'un équipement lorsque le mur n'est pas trouvé.
     */
    @Test
    void testSaveEquipement_MurNotFound_Throws() {
        Batiment batiment = new Batiment();
        batiment.setId(10);

        TypeSalle typeSalle = new TypeSalle();
        typeSalle.setId(20);

        Salle salle = new Salle();
        salle.setId(5);
        salle.setBatimentNavigation(batiment);
        salle.setTypeSalleNavigation(typeSalle);

        Equipement equipement = new Equipement();
        equipement.setSalleNavigation(salle);

        Mur mur = new Mur();
        mur.setId(15);
        equipement.setMurNavigation(mur);

        when(salleRepo.findById(5)).thenReturn(Optional.of(salle));
        when(batimentRepo.findById(10)).thenReturn(Optional.of(batiment));
        when(typeSalleRepo.findById(20)).thenReturn(Optional.of(typeSalle));
        when(murRepo.findById(15)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> equipementService.saveEquipement(equipement));
        assertEquals("Mur non trouvé", exception.getMessage());

        verify(salleRepo).findById(5);
        verify(batimentRepo).findById(10);
        verify(typeSalleRepo).findById(20);
        verify(murRepo).findById(15);
        verifyNoMoreInteractions(typeEquipementRepo, equipementRepo);
    }

    /**
     * Teste la mise à jour d'un équipement.
     */
    @Test
    void testUpdateEquipement() {
        Equipement equipement = new Equipement();
        equipement.setId(1);

        when(equipementRepo.save(equipement)).thenReturn(equipement);

        Equipement updated = equipementService.updateEquipement(equipement);

        assertNotNull(updated);
        assertEquals(1, updated.getId());
        verify(equipementRepo).save(equipement);
    }

    /**
     * Teste la suppression d'un équipement par son identifiant.
     */
    @Test
    void testDeleteEquipementById() {
        doNothing().when(equipementRepo).deleteById(1);

        equipementService.deleteEquipementById(1);

        verify(equipementRepo).deleteById(1);
    }
}