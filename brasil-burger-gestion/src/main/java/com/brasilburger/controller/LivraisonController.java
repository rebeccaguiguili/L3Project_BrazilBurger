package com.brasilburger.controller;

import com.brasilburger.entity.Livraison;
import com.brasilburger.service.LivraisonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/livraisons")
public class LivraisonController {

    @Autowired
    private LivraisonService livraisonService;

    @PostMapping
    public ResponseEntity<Livraison> ajouterLivraison(@RequestBody Livraison livraison) {
        Livraison nouvelleLivraison = livraisonService.ajouterLivraison(livraison);
        return ResponseEntity.ok(nouvelleLivraison);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Livraison> modifierLivraison(@PathVariable Long id, @RequestBody Livraison livraison) {
        Livraison livraisonModifiee = livraisonService.modifierLivraison(id, livraison);
        return ResponseEntity.ok(livraisonModifiee);
    }

    @GetMapping
    public ResponseEntity<List<Livraison>> listerLivraisons() {
        List<Livraison> livraisons = livraisonService.listerLivraisons();
        return ResponseEntity.ok(livraisons);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimerLivraison(@PathVariable Long id) {
        livraisonService.supprimerLivraison(id);
        return ResponseEntity.noContent().build();
    }
}