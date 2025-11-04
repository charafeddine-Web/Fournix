package com.tricol.fournix.service.Implimentation;

import com.tricol.fournix.model.Commande;
import com.tricol.fournix.model.ProduitCommande;
import com.tricol.fournix.repository.CommandeRepository;
import com.tricol.fournix.service.CommandeService;
import com.tricol.fournix.service.ProduitCommandeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;

@Service
public class CommandeServiceImpli implements CommandeService {

    private final CommandeRepository commandeRepository;
    private final ProduitCommandeServiceImpli produitCommandeServiceImpli;

    @Autowired
    public CommandeServiceImpli(CommandeRepository commandeRepository, ProduitCommandeServiceImpli produitCommandeServiceImpli) {
        this.commandeRepository = commandeRepository;
        this.produitCommandeServiceImpli = produitCommandeServiceImpli;
    }


    @Override
    public Commande save(Commande commande, List<ProduitCommande> produits) {
        Commande commSave =  commandeRepository.save(commande);

        for(ProduitCommande pr: produits){
            pr.setCommande(commSave);
            produitCommandeServiceImpli.save(pr);
        }
        return commSave;
    }

    @Override
    public Optional<Commande> findById(int id) {
        return commandeRepository.findById(id);
    }

    @Override
    public List<Commande> findAll(Pageable pageable) {
        return commandeRepository.findAll();
    }

    @Override
    public void delete(int id) {
        commandeRepository.deleteById(id);
    }

    @Override
    public Commande update(Commande commande) {
        return commandeRepository.save(commande);
    }
}
