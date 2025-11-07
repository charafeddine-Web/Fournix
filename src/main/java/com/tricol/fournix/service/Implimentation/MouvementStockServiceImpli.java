package com.tricol.fournix.service.Implimentation;
import java.util.List;

import com.tricol.fournix.dto.MovmentStockDTO;
import com.tricol.fournix.mapper.MovmentStockMapper;
import com.tricol.fournix.model.Commande;
import com.tricol.fournix.model.MovmentStock;
import com.tricol.fournix.model.Produit;
import com.tricol.fournix.model.enums.TypeMovment;
import com.tricol.fournix.repository.MovmentStockRepository;
import com.tricol.fournix.repository.ProduitRepository;
import com.tricol.fournix.service.MouvementStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class MouvementStockServiceImpli  implements MouvementStockService {

    private final MovmentStockRepository movmentStockRepository;
    private final ProduitRepository produitRepository;
    private final MovmentStockMapper movmentStockMapper;


    @Autowired
    public MouvementStockServiceImpli(MovmentStockRepository movmentStockRepository,ProduitRepository produitRepository, MovmentStockMapper movmentStockMapper){
        this.movmentStockRepository= movmentStockRepository;
        this.produitRepository = produitRepository;
        this.movmentStockMapper = movmentStockMapper;
    }

    public void enregistrerEntree(Produit produit, Commande commande, Integer quantite, Double prixUnitaire) {
        if (quantite == null || quantite <= 0) {
            throw new IllegalArgumentException("Quantité invalide");
        }

        if (prixUnitaire == null || prixUnitaire <= 0) {
            throw new IllegalArgumentException("Prix unitaire invalide");
        }

        int ancienStock = (produit.getStockActuel() != null) ? produit.getStockActuel() : 0;
        double ancienCout = (produit.getCoutMoyen() != null) ? produit.getCoutMoyen() : produit.getPrixUnit();

        int nouveauStock = ancienStock + quantite;
        double nouveauCout = ((ancienCout * ancienStock) + (prixUnitaire * quantite)) / nouveauStock;

        produit.setStockActuel(nouveauStock);
        produit.setCoutMoyen(nouveauCout);
        produitRepository.save(produit);

        MovmentStock mouvement = new MovmentStock();
        mouvement.setProduit(produit);
        mouvement.setQuantite(quantite);
        mouvement.setTypeMovment(TypeMovment.ENTREE);
        mouvement.setCommande(commande);
        mouvement.setDateMovment(LocalDateTime.now());
        movmentStockRepository.save(mouvement);
    }


    public void enregistrerSortie(Produit produit, Commande commande, Integer quantite) {
        if (quantite == null || quantite <= 0) {
            throw new IllegalArgumentException("Quantité invalide !");
        }

        if (produit.getStockActuel() == null || produit.getStockActuel() < quantite) {
            throw new IllegalArgumentException("Stock insuffisant pour le produit : " + produit.getNom());
        }

        double coutUnitaire = (produit.getCoutMoyen() != null)
                ? produit.getCoutMoyen()
                : produit.getPrixUnit();

        produit.setStockActuel(produit.getStockActuel() - quantite);
        produitRepository.save(produit);

        MovmentStock mouvement = new MovmentStock();
        mouvement.setProduit(produit);
        mouvement.setCommande(commande);
        mouvement.setQuantite(quantite);
        mouvement.setTypeMovment(TypeMovment.SORTIE);
        mouvement.setDateMovment(LocalDateTime.now());

        movmentStockRepository.save(mouvement);
    }
    public void enregistrerAjustement(Produit produit, Integer nouveauStock) {
        if (nouveauStock == null || nouveauStock < 0)
            throw new IllegalArgumentException("Stock ajusté invalide");

        int ancienStock = (produit.getStockActuel() != null) ? produit.getStockActuel() : 0;
        int difference = nouveauStock - ancienStock;

        produit.setStockActuel(nouveauStock);
        produitRepository.save(produit);

        MovmentStock mvt = new MovmentStock();
        mvt.setProduit(produit);
        mvt.setQuantite(Math.abs(difference));
        mvt.setTypeMovment(TypeMovment.AJUSTEMENT);
        mvt.setDateMovment(LocalDateTime.now());
        movmentStockRepository.save(mvt);
    }

    public Page<MovmentStockDTO> getAllMouvements(Pageable pageable) {
        return movmentStockRepository.findAll(pageable).map(movmentStockMapper::toDTO);
    }

    public Page<MovmentStockDTO> findByProduitId(Long produitId, Pageable pageable) {
        return movmentStockRepository.findByProduitId(produitId, pageable).map(movmentStockMapper::toDTO);
    }

    public Page<MovmentStockDTO> findByCommandeId(Long commandeId, Pageable pageable) {
        return movmentStockRepository.findByCommandeId(commandeId, pageable).map(movmentStockMapper::toDTO);
    }

    public Page<MovmentStockDTO> findByTypeMovment(TypeMovment typeMovment, Pageable pageable) {
        return movmentStockRepository.findByTypeMovment(typeMovment,  pageable).map(movmentStockMapper::toDTO);
    }

}
