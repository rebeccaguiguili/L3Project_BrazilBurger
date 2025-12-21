package com.brasilburger.service;

import com.brasilburger.entity.Menu;
import java.util.List;

public interface MenuService {
    Menu ajouterMenu(Menu menu);
    Menu modifierMenu(Menu menu);
    void archiverMenu(Long id);
    List<Menu> listerMenus();
}
