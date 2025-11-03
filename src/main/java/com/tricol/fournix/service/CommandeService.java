package com.tricol.fournix.service;

import com.tricol.fournix.model.Commande;

import java.util.List;
import java.util.Optional;

public interface CommandeService {

    Commande save(Commande commande);
    Optional<Commande> findById(int id);
    List<Commande> findAll();
    void delete(int id);
    Commande update(Commande commande);
}
