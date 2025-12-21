package com.brasilburger.repository;

import com.brasilburger.entity.Menu;
import java.util.List;
import java.util.Optional;

public interface MenuRepository {
    Menu save(Menu menu);
    Menu update(Menu menu);
    void archiver(Long id);
    Optional<Menu> findById(Long id);
    List<Menu> findAll();
}
