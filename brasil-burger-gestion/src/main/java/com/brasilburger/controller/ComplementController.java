package com.brasilburger.controller;

import com.brasilburger.entity.Complement;
import com.brasilburger.service.ComplementService;
import java.util.List;

public class ComplementController {
    private final ComplementService complementService;

    public ComplementController(ComplementService complementService) {
        this.complementService = complementService;
    }

    public Complement ajouterComplement(Complement complement) {
        return complementService.ajouterComplement(complement);
    }

    public Complement modifierComplement(Complement complement) {
        return complementService.modifierComplement(complement);
    }

    public void archiverComplement(Long id) {
        complementService.archiverComplement(id);
    }

    public List<Complement> listerComplements() {
        return complementService.listerComplements();
    }
}
