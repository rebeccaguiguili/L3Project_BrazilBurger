package com.brasilburger.controller;

import com.brasilburger.service.StatistiqueService;
import java.util.Date;

public class StatistiqueController {
    private final StatistiqueService statistiqueService;

    public StatistiqueController(StatistiqueService statistiqueService) {
        this.statistiqueService = statistiqueService;
    }

    public int commandesEnCoursJour(Date date) {
        return statistiqueService.commandesEnCoursJour(date);
    }

    public int commandesValideesJour(Date date) {
        return statistiqueService.commandesValideesJour(date);
    }

    public double recettesJournalieres(Date date) {
        return statistiqueService.recettesJournalieres(date);
    }

    public String burgerMenuLePlusVenduJour(Date date) {
        return statistiqueService.burgerMenuLePlusVenduJour(date);
    }

    public int commandesAnnuleesJour(Date date) {
        return statistiqueService.commandesAnnuleesJour(date);
    }
}
