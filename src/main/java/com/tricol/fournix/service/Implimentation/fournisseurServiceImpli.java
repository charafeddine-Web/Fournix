package com.tricol.fournix.service.Implimentation;

import com.tricol.fournix.model.Fournisseur;
import com.tricol.fournix.repository.FournisseurRepository;
import com.tricol.fournix.service.FournisseurService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class fournisseurServiceImpli implements FournisseurService {

    private final  FournisseurRepository fournisseurRepository;

    public fournisseurServiceImpli(FournisseurRepository fournisseurRepository) {
        this.fournisseurRepository = fournisseurRepository;
    }

    @Override
    public void  createFournisseur(Fournisseur fournisseur){
        fournisseurRepository.save(fournisseur);
    }

    @Override
    public void  updateFournisseur(Fournisseur fournisseur ){
        fournisseurRepository.save(fournisseur);
    }

    @Override
    public void  deleteFournisseur(Fournisseur fournisseur ){
        fournisseurRepository.delete(fournisseur);
    }

    @Override
    public Optional<Fournisseur> getFournisseur(Integer id ){
        return  fournisseurRepository.findById(id);
    }

    @Override
    public List<Fournisseur> getFournisseurs(){
        return fournisseurRepository.findAll();
    }



}
