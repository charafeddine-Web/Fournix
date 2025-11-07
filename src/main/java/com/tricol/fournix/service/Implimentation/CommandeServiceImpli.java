package com.tricol.fournix.service.Implimentation;

import com.tricol.fournix.dto.CommandeDTO;
import com.tricol.fournix.mapper.CommandeMapper;
import com.tricol.fournix.model.Commande;
import com.tricol.fournix.model.Fournisseur;
import com.tricol.fournix.model.Produit;
import com.tricol.fournix.model.ProduitCommande;
import com.tricol.fournix.model.enums.StatusCommande;
import com.tricol.fournix.repository.CommandeRepository;
import com.tricol.fournix.repository.FournisseurRepository;
import com.tricol.fournix.repository.ProduitRepository;
import com.tricol.fournix.service.CommandeService;
import com.tricol.fournix.service.ProduitCommandeService;
import com.tricol.fournix.service.ProduitService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;

@Service
public class CommandeServiceImpli implements CommandeService {

    private final CommandeRepository commandeRepository;
    private final ProduitCommandeServiceImpli produitCommandeServiceImpli;
    private final ProduitRepository produitRepository;
    private final MouvementStockServiceImpli mouvementStockService;
    private final FournisseurRepository fournisseurRepository;
    private final CommandeMapper commandeMapper;


    @Autowired
    public CommandeServiceImpli(CommandeRepository commandeRepository, ProduitCommandeServiceImpli produitCommandeServiceImpli,ProduitRepository produitRepository,MouvementStockServiceImpli mouvementStockService,FournisseurRepository fournisseurRepository,CommandeMapper commandeMapper) {
        this.commandeRepository = commandeRepository;
        this.produitCommandeServiceImpli = produitCommandeServiceImpli;
        this.produitRepository = produitRepository;
        this.mouvementStockService = mouvementStockService;
        this.fournisseurRepository = fournisseurRepository;
        this.commandeMapper = commandeMapper;
    }

    @Override
    public Commande save(Commande commande, List<ProduitCommande> produits) {

        if (produits == null || produits.isEmpty()) {
            throw new IllegalArgumentException("La liste des produits ne peut pas être vide !");
        }

        double prixTotal = 0.0;
        for (ProduitCommande pr : produits) {
            if (pr.getPrixUnit() == null && pr.getProduit() != null) {
                Produit produit = produitRepository.findById(Math.toIntExact(pr.getProduit().getId()))
                        .orElseThrow(() -> new IllegalArgumentException("Produit introuvable avec id " + pr.getProduit().getId()));
            }
            prixTotal += pr.getPrixUnit() * pr.getQuantite();
        }
        commande.setPrix(prixTotal);

        if (commande.getFournisseur() != null && commande.getFournisseur().getId() != null) {
            Fournisseur fournisseur = fournisseurRepository.findById(Math.toIntExact(commande.getFournisseur().getId()))
                    .orElseThrow(() -> new IllegalArgumentException("Fournisseur introuvable"));
            commande.setFournisseur(fournisseur);
        }
        Commande commSave = commandeRepository.save(commande);

        for (ProduitCommande pr : produits) {
            Produit produit = produitRepository.findById(Math.toIntExact(pr.getProduit().getId()))
                    .orElseThrow(() -> new IllegalArgumentException("Produit introuvable avec id " + pr.getProduit().getId()));
            pr.setProduit(produit);
            pr.setCommande(commSave);
            produitCommandeServiceImpli.save(pr);

//            mouvementStockService.enregistrerSortie(produit,commSave, pr.getQuantite());
        }

        Commande fullCommande = commandeRepository.findById(Math.toIntExact(commSave.getId()))
                .orElseThrow(() -> new RuntimeException("Commande non trouvée après sauvegarde"));
        fullCommande.setProduitCommandes(produits);


        return fullCommande;
    }

    @Override
    public Optional<Commande> findById(int id) {
        return commandeRepository.findById(id);
    }

    @Override
    public Page<Commande> findAll(Pageable pageable) {
        return commandeRepository.findAll(pageable);
    }

    @Override
    public void delete(int id) {
        commandeRepository.deleteById(id);
    }

    @Override
    public Commande update(Commande commande) {
        return commandeRepository.save(commande);
    }


    public CommandeDTO validerCommande(Long id) {
        Commande commande = commandeRepository.findById(Math.toIntExact(id))
                .orElseThrow(() -> new EntityNotFoundException("Commande non trouvée avec ID : " + id));

        if (commande.getStatut_commande() == StatusCommande.LIVREE) {
            throw new IllegalStateException("cette commande est déjà livrée.");
        }

        if (commande.getStatut_commande() == StatusCommande.ANULLEE) {
            throw new IllegalStateException("cette commande est déjà anuulée.");
        }

        for (ProduitCommande pc : commande.getProduitCommandes()) {
            Produit produit = produitRepository.findById(Math.toIntExact(pc.getProduit().getId()))
                    .orElseThrow(() -> new EntityNotFoundException("Produit non trouvé : " + pc.getProduit().getId()));

            mouvementStockService.enregistrerSortie(
                    produit,
                    commande,
                    pc.getQuantite()
            );
        }

        commande.setStatut_commande(StatusCommande.LIVREE);


        Commande updated = commandeRepository.save(commande);
        return commandeMapper.toDTO(updated);
    }
}
