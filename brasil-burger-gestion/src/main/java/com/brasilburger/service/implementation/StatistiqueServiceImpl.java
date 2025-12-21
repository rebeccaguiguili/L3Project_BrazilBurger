package com.brasilburger.service.implementation;

import com.brasilburger.service.StatistiqueService;
import com.brasilburger.repository.CommandeRepository;
import java.util.Date;

public class StatistiqueServiceImpl implements StatistiqueService {
    private final CommandeRepository commandeRepository;

    public StatistiqueServiceImpl(CommandeRepository commandeRepository) {
        this.commandeRepository = commandeRepository;
    }

    @Override
    public int commandesEnCoursJour(Date date) {
        // TODO: requête sur commandeRepository
        return 0;
    }

    @Override
    public int commandesValideesJour(Date date) {
        // TODO: requête sur commandeRepository
        return 0;
    }

    @Override
    public double recettesJournalieres(Date date) {
        // TODO: requête sur commandeRepository
        return 0.0;
    }

    @Override
    public String burgerMenuLePlusVenduJour(Date date) {
        // TODO: requête sur commandeRepository
        return null;
    }

    @Override
    public int commandesAnnuleesJour(Date date) {
        // TODO: requête sur commandeRepository
        return 0;
    }
}
