package com.brasilburger.service.implementation;

import com.brasilburger.entity.Burger;
import com.brasilburger.repository.BurgerRepository;
import com.brasilburger.service.BurgerService;
import java.util.List;
import java.util.Optional;

public class BurgerServiceImpl implements BurgerService {
    private final BurgerRepository burgerRepository;

    public BurgerServiceImpl(BurgerRepository burgerRepository) {
        this.burgerRepository = burgerRepository;
    }

    @Override
    public Burger ajouterBurger(Burger burger) {
        return burgerRepository.save(burger);
    }

    @Override
    public Burger modifierBurger(Burger burger) {
        return burgerRepository.update(burger);
    }

    @Override
    public void archiverBurger(Long id) {
        burgerRepository.archiver(id);
    }

    @Override
    public List<Burger> listerBurgers() {
        return burgerRepository.findAll();
    }
}
