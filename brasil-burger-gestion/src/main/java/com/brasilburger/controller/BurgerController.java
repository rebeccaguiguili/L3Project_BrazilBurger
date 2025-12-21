package com.brasilburger.controller;

import com.brasilburger.entity.Burger;
import com.brasilburger.service.BurgerService;
import java.util.List;

public class BurgerController {
    private final BurgerService burgerService;

    public BurgerController(BurgerService burgerService) {
        this.burgerService = burgerService;
    }

    public Burger ajouterBurger(Burger burger) {
        return burgerService.ajouterBurger(burger);
    }

    public Burger modifierBurger(Burger burger) {
        return burgerService.modifierBurger(burger);
    }

    public void archiverBurger(Long id) {
        burgerService.archiverBurger(id);
    }

    public List<Burger> listerBurgers() {
        return burgerService.listerBurgers();
    }
}
