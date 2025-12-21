package com.brasilburger.service.implementation;

import com.brasilburger.entity.Menu;
import com.brasilburger.repository.MenuRepository;
import com.brasilburger.service.MenuService;
import java.util.List;
import java.util.Optional;

public class MenuServiceImpl implements MenuService {
    private final MenuRepository menuRepository;

    public MenuServiceImpl(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    @Override
    public Menu ajouterMenu(Menu menu) {
        return menuRepository.save(menu);
    }

    @Override
    public Menu modifierMenu(Menu menu) {
        return menuRepository.update(menu);
    }

    @Override
    public void archiverMenu(Long id) {
        menuRepository.archiver(id);
    }

    @Override
    public List<Menu> listerMenus() {
        return menuRepository.findAll();
    }
}
