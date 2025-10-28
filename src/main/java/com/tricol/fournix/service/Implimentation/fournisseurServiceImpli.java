package com.tricol.fournix.service.Implimentation;

import com.tricol.fournix.dto.FournisseurDTO;
import com.tricol.fournix.mapper.FournisseurMapper;
import com.tricol.fournix.model.Fournisseur;
import com.tricol.fournix.repository.FournisseurRepository;
import com.tricol.fournix.service.FournisseurService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class fournisseurServiceImpli implements FournisseurService {

    private final  FournisseurRepository fournisseurRepository;
    private final FournisseurMapper mapper;

    public fournisseurServiceImpli(FournisseurRepository fournisseurRepository, FournisseurMapper mapper) {
        this.mapper = mapper;
        this.fournisseurRepository = fournisseurRepository;
    }

    @Override
    public void  createFournisseur(Fournisseur fournisseur){
        fournisseurRepository.save(fournisseur);
    }

    @Override
    public void  updateFournisseur(Fournisseur fournisseur){
        fournisseurRepository.save(fournisseur);
    }

    @Override
    public void  deleteFournisseur(Fournisseur fournisseur ){
        fournisseurRepository.delete(fournisseur);
    }

    @Override
    public Optional<FournisseurDTO> getFournisseur(Integer id ){
        Optional<Fournisseur> f=  fournisseurRepository.findById(id);
        return Optional.ofNullable(mapper.toDTO(f));
    }

    @Override
    public List<Fournisseur> getFournisseurs(){
        return fournisseurRepository.findAll();
    }



}
