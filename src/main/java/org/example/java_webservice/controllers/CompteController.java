package org.example.java_webservice.controllers;

import lombok.Data;
import org.example.java_webservice.entities.Compte;
import org.example.java_webservice.repositories.CompteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/banque")
public class CompteController {
    @Autowired
    private CompteRepository compteRepository;
    @GetMapping(value = "/comptes",produces = {"application/json","application/xml"})
    public List<Compte> getAllComptes(){
        return compteRepository.findAll();
    }
    @GetMapping("/comptes/{id}")
    public ResponseEntity<Compte> getCompteById(@PathVariable Long id){
        return compteRepository.findById(id).map(compte -> ResponseEntity.ok().body(compte)).orElse(ResponseEntity.notFound().build());
    }
    @PostMapping("/comptes")
    public Compte createCompte(@RequestBody Compte compte){
        return compteRepository.save(compte);
    }
    @PutMapping("/comptes")
    public ResponseEntity<Compte> updateCompte(@PathVariable Long id, @RequestBody Compte compteDetails){
        return compteRepository.findById(id).map(compte -> {
            compte.setSolde(compteDetails.getSolde());
            compte.setDateCreation(compteDetails.getDateCreation());
            compte.setTypeCompte(compteDetails.getTypeCompte());
            Compte updatedCompte = compteRepository.save(compte);
            return ResponseEntity.ok().body(updatedCompte);
        }).orElse(ResponseEntity.notFound().build());
    }
    @DeleteMapping("/comptes/{id}")
    public ResponseEntity<Void> deleteCompte(@PathVariable Long id){
        return compteRepository.findById(id).map(compte -> {
                compteRepository.delete(compte);
        return ResponseEntity.ok().<Void>build();
        }).orElse(ResponseEntity.notFound().build());
    }


}
