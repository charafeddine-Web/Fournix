package com.tricol.fournix.service.Implimentation;

import com.tricol.fournix.dto.FournisseurDTO;
import com.tricol.fournix.mapper.FournisseurMapper;
import com.tricol.fournix.model.Fournisseur;
import com.tricol.fournix.repository.FournisseurRepository;
import com.tricol.fournix.service.FournisseurService;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    public Fournisseur  createFournisseur(Fournisseur fournisseur){
        return fournisseurRepository.save(fournisseur);
    }

    @Override
    public Fournisseur   updateFournisseur(Fournisseur fournisseur){
        return fournisseurRepository.save(fournisseur);
    }

    @Override
    public void  deleteFournisseur(Integer id ){
        fournisseurRepository.deleteById(id);
    }

    @Override
    public Optional<FournisseurDTO> getFournisseur(Integer id) {
        return fournisseurRepository.findById(id)
                .map(mapper::toDTO);
    }


    @Override
    public Page<Fournisseur> getFournisseurs(Pageable pageable){
        return fournisseurRepository.findAll(pageable);
    }

    @Override
    public List<Fournisseur> findFournisseurByNom(String nom) {
        return fournisseurRepository.findByNom(nom);
    }

    @Override
    public List<Fournisseur> findFournisseurByNomEndingWith(String ending) {
        return fournisseurRepository.findByEmailEndingWith(ending);
    }



}
