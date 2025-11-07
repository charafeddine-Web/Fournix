package com.tricol.fournix.service.Implimentation;

import com.tricol.fournix.model.Produit;
import com.tricol.fournix.repository.ProduitRepository;
import com.tricol.fournix.service.MouvementStockService;
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
    private final MouvementStockService movmentStockService;

    @Autowired
    public ProduitServiceImpl(ProduitRepository produitRepository,MouvementStockService movmentStockService) {
        this.produitRepository = produitRepository;
        this.movmentStockService = movmentStockService;
    }

    @Override
    public Produit save(Produit produit) {
        Produit saved = produitRepository.save(produit);

        if (produit.getStockActuel() != null && produit.getStockActuel() > 0) {
            movmentStockService.enregistrerEntree(saved, null, produit.getStockActuel(), produit.getPrixUnit());
        }
        return saved;
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
        Produit pr = produitRepository.save(produit);
        movmentStockService.enregistrerAjustement(pr,pr.getStockActuel());
        return pr;
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
