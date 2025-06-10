// package com.sae_s6.S6.APIGestion.controller;

// import java.util.List;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.ResponseEntity;
// import org.springframework.validation.annotation.Validated;
// import org.springframework.web.bind.annotation.DeleteMapping;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.PathVariable;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RestController;

// import com.sae_s6.S6.APIGestion.entity.Batiment;
// import com.sae_s6.S6.APIGestion.service.BatimentService;

// import lombok.RequiredArgsConstructor;

// import org.springframework.web.bind.annotation.RequestParam;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.PutMapping;
// import org.springframework.web.bind.annotation.RequestBody;



// @RestController
// @RequestMapping("/api/batiments")
// @RequiredArgsConstructor
// @Validated
// public class BatimentController {

//     @Autowired
//     private BatimentService service;

//     @GetMapping
//     public List<Batiment> getAll(){
//         return service.findAll();
//     }

//     @GetMapping("/{id}")
//     public ResponseEntity<Batiment> getById(@PathVariable Integer id) {
//         return service.findById(id)
//             .map(ResponseEntity::ok)
//             .orElse(ResponseEntity.notFound().build());
//     }

//     @PostMapping
//     public Batiment create(@RequestBody Batiment batiment) {
//         return service.save(batiment);
//     }

//     @PutMapping("/{id}")
//     public ResponseEntity<Batiment> update(@PathVariable Integer id, @RequestBody Batiment newBatiment) {
//         return service.findById(id).map(b -> {
//             b.setNom(newBatiment.getNom());
//             return ResponseEntity.ok(service.save(b));
//         }).orElse(ResponseEntity.notFound().build());
//     }

//     @DeleteMapping("/{id}")
//     public ResponseEntity<Void> delete(@PathVariable Integer id) {
//         if (service.findById(id).isPresent()) {
//             service.deleteById(id);
//             return ResponseEntity.ok().build();
//         }
//         return ResponseEntity.notFound().build();
//     }
// }
