package com.tricol.fournix.service.Implimentation;

import com.tricol.fournix.model.Produit;
import com.tricol.fournix.model.ProduitCommande;
import com.tricol.fournix.repository.ProduitCommandeRepository;
import com.tricol.fournix.service.ProduitCommandeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProduitCommandeServiceImpli implements ProduitCommandeService {


    private final ProduitCommandeRepository produitCommandeRepository;

    @Autowired
    public ProduitCommandeServiceImpli(ProduitCommandeRepository produitCommandeRepository){
        this.produitCommandeRepository = produitCommandeRepository;
    }


    public ProduitCommande save(ProduitCommande produitCommande){
        return produitCommandeRepository.save(produitCommande);
    }

    public Optional<ProduitCommande> findById(Integer id){
        return produitCommandeRepository.findById(id);
    }


    public ProduitCommande update(ProduitCommande produitCommande){
        return produitCommandeRepository.save(produitCommande);
    }

    public void Delete(Integer id){
         produitCommandeRepository.deleteById(id);
    }


}
