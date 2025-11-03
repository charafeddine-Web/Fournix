package com.tricol.fournix.service.Implimentation;

import com.tricol.fournix.model.Commande;
import com.tricol.fournix.repository.CommandeRepository;
import com.tricol.fournix.service.CommandeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;

@Service
public class CommandeServiceImpli implements CommandeService {

    private final CommandeRepository commandeRepository;

    @Autowired
    public CommandeServiceImpli(CommandeRepository commandeRepository) {
        this.commandeRepository = commandeRepository;
    }


    @Override
    public Commande save(Commande commande) {
        return commandeRepository.save(commande);
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
