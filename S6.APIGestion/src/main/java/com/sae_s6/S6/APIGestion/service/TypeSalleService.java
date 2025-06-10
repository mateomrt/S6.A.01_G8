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






}
