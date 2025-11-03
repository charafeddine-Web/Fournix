package com.tricol.fournix.service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.tricol.fournix.dto.ProduitDTO;
import com.tricol.fournix.model.Produit;

import java.util.List;
import java.util.Optional;

public interface ProduitService {

    public Produit save(Produit produit);
    public List<Produit> findAll();
    public Optional<Produit> findById(int id);
    public void delete(int id);
    public Produit update(Produit produit);
    Page<Produit> findAll(Pageable pageable);

}
