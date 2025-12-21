package com.brasilburger.service.implementation;

import com.brasilburger.entity.Livraison;
import com.brasilburger.repository.LivraisonRepository;
import com.brasilburger.service.LivraisonService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LivraisonServiceImpl implements LivraisonService {

    private final LivraisonRepository livraisonRepository;

    public LivraisonServiceImpl(LivraisonRepository livraisonRepository) {
        this.livraisonRepository = livraisonRepository;
    }

    @Override
    public Livraison ajouterLivraison(Livraison livraison) {
        return livraisonRepository.save(livraison);
    }

    @Override
    public Livraison modifierLivraison(Long id, Livraison livraison) {
        livraison.setId(id);
        return livraisonRepository.save(livraison);
    }

    @Override
    public void supprimerLivraison(Long id) {
        livraisonRepository.deleteById(id);
    }

    @Override
    public List<Livraison> listerLivraisons() {
        return livraisonRepository.findAll();
    }
}
