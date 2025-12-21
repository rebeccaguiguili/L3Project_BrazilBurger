package com.brasilburger.service;

import java.util.Date;
import java.util.Map;

public interface StatistiqueService {
    int commandesEnCoursJour(Date date);
    int commandesValideesJour(Date date);
    double recettesJournalieres(Date date);
    String burgerMenuLePlusVenduJour(Date date);
    int commandesAnnuleesJour(Date date);
    // ... autres stats
}
