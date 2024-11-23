package org.example.java_webservice.controllers;

import org.example.java_webservice.entities.Compte;
import org.example.java_webservice.repositories.CompteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/banque")
@CrossOrigin(origins = "*") // Autorise les requêtes de tous les domaines, personnalisez si nécessaire
public class CompteController {

    @Autowired
    private CompteRepository compteRepository;

    @GetMapping(value = "/comptes", produces = {"application/json", "application/xml"})
    public List<Compte> getAllComptes() {
        return compteRepository.findAll();
    }

    @GetMapping(value = "/comptes/{id}", produces = {"application/json", "application/xml"})
    public ResponseEntity<Compte> getCompteById(@PathVariable Long id) {
        return compteRepository.findById(id)
                .map(compte -> ResponseEntity.ok().body(compte))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping(value = "/comptes", produces = {"application/json", "application/xml"}, consumes = {"application/json", "application/xml"})
    public Compte createCompte(@RequestBody Compte compte) {
        return compteRepository.save(compte);
    }

    @PutMapping(value = "/comptes/{id}", produces = {"application/json", "application/xml"}, consumes = {"application/json", "application/xml"})
    public ResponseEntity<Compte> updateCompte(@PathVariable Long id, @RequestBody Compte compteDetails) {
        return compteRepository.findById(id)
                .map(compte -> {
                    compte.setSolde(compteDetails.getSolde());
                    compte.setTypeCompte(compteDetails.getTypeCompte());
                    // compte.setDateCreation(compteDetails.getDateCreation()); // Ajoutez cette ligne si nécessaire
                    Compte updatedCompte = compteRepository.save(compte);
                    return ResponseEntity.ok().body(updatedCompte);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping(value = "/comptes/{id}", produces = {"application/json", "application/xml"})
    public ResponseEntity<Void> deleteCompte(@PathVariable Long id) {
        return compteRepository.findById(id)
                .map(compte -> {
                    compteRepository.delete(compte);
                    return ResponseEntity.ok().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
