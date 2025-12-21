package com.brasilburger.controller;

import com.brasilburger.entity.Commande;
import com.brasilburger.service.CommandeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/commandes")
public class CommandeController {

    @Autowired
    private CommandeService commandeService;

    @PostMapping
    public ResponseEntity<Commande> ajouterCommande(@RequestBody Commande commande) {
        Commande nouvelleCommande = commandeService.ajouterCommande(commande);
        return ResponseEntity.ok(nouvelleCommande);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Commande> modifierCommande(@PathVariable Long id, @RequestBody Commande commande) {
        Commande commandeModifiee = commandeService.modifierCommande(id, commande);
        return ResponseEntity.ok(commandeModifiee);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> annulerCommande(@PathVariable Long id) {
        commandeService.annulerCommande(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<Commande>> listerCommandes() {
        List<Commande> commandes = commandeService.listerCommandes();
        return ResponseEntity.ok(commandes);
    }
}