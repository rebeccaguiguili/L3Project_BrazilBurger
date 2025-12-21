package com.brasilburger.controller;

import com.brasilburger.entity.Menu;
import com.brasilburger.service.MenuService;
import java.util.List;

public class MenuController {
    private final MenuService menuService;

    public MenuController(MenuService menuService) {
        this.menuService = menuService;
    }

    public Menu ajouterMenu(Menu menu) {
        return menuService.ajouterMenu(menu);
    }

    public Menu modifierMenu(Menu menu) {
        return menuService.modifierMenu(menu);
    }

    public void archiverMenu(Long id) {
        menuService.archiverMenu(id);
    }

    public List<Menu> listerMenus() {
        return menuService.listerMenus();
    }
}
