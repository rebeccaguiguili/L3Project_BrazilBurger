package com.brasilburger.repository;

import com.brasilburger.entity.Livraison;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LivraisonRepository extends JpaRepository<Livraison, Long> {
    List<Livraison> findByStatut(String statut);
    List<Livraison> findByLivreur(String livreur);
}