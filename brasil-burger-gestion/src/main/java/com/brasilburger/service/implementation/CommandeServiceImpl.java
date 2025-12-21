package com.brasilburger.service.implementation;

import com.brasilburger.entity.Commande;
import com.brasilburger.repository.CommandeRepository;
import com.brasilburger.service.CommandeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CommandeServiceImpl implements CommandeService {

    private final CommandeRepository commandeRepository;

    public CommandeServiceImpl(CommandeRepository commandeRepository) {
        this.commandeRepository = commandeRepository;
    }

    @Override
    public Commande ajouterCommande(Commande commande) {
        return commandeRepository.save(commande);
    }

    @Override
    public Commande modifierCommande(Long id, Commande commande) {
        Optional<Commande> existing = commandeRepository.findById(id);
        if (existing.isPresent()) {
            commande.setId(id);
            return commandeRepository.save(commande);
        }
        return null;
    }

    @Override
    public boolean annulerCommande(Long id) {
        if (commandeRepository.existsById(id)) {
            commandeRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public List<Commande> listerCommandes() {
        return commandeRepository.findAll();
    }

    @Override
    public Optional<Commande> trouverCommandeParId(Long id) {
        return commandeRepository.findById(id);
    }

}
