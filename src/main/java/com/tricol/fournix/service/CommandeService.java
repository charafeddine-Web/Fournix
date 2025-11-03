package com.tricol.fournix.service;

import com.tricol.fournix.model.Commande;

import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;

public interface CommandeService {

    Commande save(Commande commande);
    Optional<Commande> findById(int id);
    List<Commande> findAll(Pageable pageable);
    void delete(int id);
    Commande update(Commande commande);
}
