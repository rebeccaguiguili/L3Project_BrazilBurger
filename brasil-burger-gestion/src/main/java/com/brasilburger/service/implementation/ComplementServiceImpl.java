package com.brasilburger.service.implementation;

import com.brasilburger.entity.Complement;
import com.brasilburger.repository.ComplementRepository;
import com.brasilburger.service.ComplementService;
import java.util.List;
import java.util.Optional;

public class ComplementServiceImpl implements ComplementService {
    private final ComplementRepository complementRepository;

    public ComplementServiceImpl(ComplementRepository complementRepository) {
        this.complementRepository = complementRepository;
    }

    @Override
    public Complement ajouterComplement(Complement complement) {
        return complementRepository.save(complement);
    }

    @Override
    public Complement modifierComplement(Complement complement) {
        return complementRepository.update(complement);
    }

    @Override
    public void archiverComplement(Long id) {
        complementRepository.archiver(id);
    }

    @Override
    public List<Complement> listerComplements() {
        return complementRepository.findAll();
    }
}
