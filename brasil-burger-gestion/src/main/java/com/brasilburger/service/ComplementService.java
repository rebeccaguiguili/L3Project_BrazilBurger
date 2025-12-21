package com.brasilburger.service;

import com.brasilburger.entity.Complement;
import java.util.List;

public interface ComplementService {
    Complement ajouterComplement(Complement complement);
    Complement modifierComplement(Complement complement);
    void archiverComplement(Long id);
    List<Complement> listerComplements();
}
