package com.brasilburger.repository;

import com.brasilburger.entity.Complement;
import java.util.List;
import java.util.Optional;

public interface ComplementRepository {
    Complement save(Complement complement);
    Complement update(Complement complement);
    void archiver(Long id);
    Optional<Complement> findById(Long id);
    List<Complement> findAll();
}
