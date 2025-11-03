package com.tricol.fournix.service.Implimentation;

import com.tricol.fournix.model.Produit;
import com.tricol.fournix.repository.ProduitRepository;
import com.tricol.fournix.service.ProduitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProduitServiceImpl implements ProduitService {

    private final ProduitRepository produitRepository;

    @Autowired
    public ProduitServiceImpl(ProduitRepository produitRepository) {
        this.produitRepository = produitRepository;
    }

    @Override
    public Produit save(Produit produit) {
        return produitRepository.save(produit);
    }

    @Override
    public Optional<Produit> findById(int id) {
        return produitRepository.findById(id);
    }

    @Override
    public void delete(int id) {
        produitRepository.deleteById(id);
    }

    @Override
    public Produit update(Produit produit) {
        return produitRepository.save(produit);
    }

    @Override
    public List<Produit> findAll() {
        return produitRepository.findAll();
    }

    @Override
    public Page<Produit> findAll(Pageable pageable) {
        return produitRepository.findAll(pageable);
    }
}
