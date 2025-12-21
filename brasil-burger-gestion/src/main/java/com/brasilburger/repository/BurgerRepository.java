package com.brasilburger.repository;

import com.brasilburger.entity.Burger;
import java.util.List;
import java.util.Optional;

public interface BurgerRepository {
    Burger save(Burger burger);
    Burger update(Burger burger);
    void archiver(Long id);
    Optional<Burger> findById(Long id);
    List<Burger> findAll();
}
