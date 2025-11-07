package com.tricol.fournix.service.Implimentation;
import java.util.List;
import com.tricol.fournix.model.Commande;
import com.tricol.fournix.model.MovmentStock;
import com.tricol.fournix.model.Produit;
import com.tricol.fournix.model.enums.TypeMovment;
import com.tricol.fournix.repository.MovmentStockRepository;
import com.tricol.fournix.repository.ProduitRepository;
import com.tricol.fournix.service.MouvementStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class MouvementStockServiceImpli  implements MouvementStockService {

    private final MovmentStockRepository movmentStockRepository;
    private final ProduitRepository produitRepository;


    @Autowired
    public MouvementStockServiceImpli(MovmentStockRepository movmentStockRepository,ProduitRepository produitRepository){
        this.movmentStockRepository= movmentStockRepository;
        this.produitRepository = produitRepository;
    }

    public void enregistrerEntree(Produit produit,Commande commande, Integer quantite, Double prixUnitaire) {
        if (quantite <= 0) throw new IllegalArgumentException("Quantité invalide");

        int ancienStock = produit.getStockActuel() != null ? produit.getStockActuel() : 0;
        double ancienCout = produit.getCoutMoyen() != null ? produit.getCoutMoyen() : produit.getPrixUnit();

        double nouveauCout = ((ancienCout * ancienStock) + (prixUnitaire * quantite)) / (ancienStock + quantite);

        produit.setStockActuel(ancienStock + quantite);
        produit.setCoutMoyen(nouveauCout);
        produitRepository.save(produit);

        MovmentStock mvt = new MovmentStock();
        mvt.setProduit(produit);
        mvt.setQuantite(quantite);
        mvt.setTypeMovment(TypeMovment.ENTREE);
        mvt.setCommande(commande);
        mvt.setDateMovment(LocalDateTime.now());
        movmentStockRepository.save(mvt);
    }


    public void enregistrerSortie(Produit produit, Integer quantite) {
        if (quantite <= 0) throw new IllegalArgumentException("Quantité invalide");
        if (produit.getStockActuel() < quantite)
            throw new IllegalArgumentException("Stock insuffisant pour ce produit");

        produit.setStockActuel(produit.getStockActuel() - quantite);
        produitRepository.save(produit);

        MovmentStock mvt = new MovmentStock();
        mvt.setProduit(produit);
        mvt.setQuantite(quantite);
        mvt.setTypeMovment(TypeMovment.SORTIE);
        mvt.setDateMovment(LocalDateTime.now());
        movmentStockRepository.save(mvt);
    }

    public List<MovmentStock> getByProduit(Long produitId) {
        return movmentStockRepository.findByProduitId(produitId);
    }

    public List<MovmentStock> getByCommande(Long commandeId) {
        return movmentStockRepository.findByCommandeId(commandeId);
    }

    public List<MovmentStock> findByTypeMovment(TypeMovment typeMovment) {
        return movmentStockRepository.findByTypeMovment(typeMovment);
    }

}
