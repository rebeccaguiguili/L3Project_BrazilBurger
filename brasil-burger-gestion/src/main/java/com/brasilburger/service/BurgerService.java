package com.brasilburger.service;

import com.brasilburger.entity.Burger;
import java.util.List;

public interface BurgerService {
    Burger ajouterBurger(Burger burger);
    Burger modifierBurger(Burger burger);
    void archiverBurger(Long id);
    List<Burger> listerBurgers();
}
