package com.tricol.fournix.service;

import com.tricol.fournix.model.Fournisseur;
import com.tricol.fournix.repository.FournisseurRepository;
import com.tricol.fournix.service.Implimentation.fournisseurServiceImpli;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TestFournisseurService {

    @Mock
    FournisseurRepository fournisseurRepository;

    @InjectMocks
   private fournisseurServiceImpli fournisseurService;


    @Test
    public  void TestCreateFournisseur(){

        Fournisseur fr = new Fournisseur();
        fr.setId(1L);
        fr.setNom("charaf");

        when(fournisseurRepository.save(any(Fournisseur.class))).thenReturn(fr);

        Fournisseur result =  fournisseurService.createFournisseur(fr);

        assertEquals("charaf",result.getNom());

        verify(fournisseurRepository, times(1)).save(fr);
    }


    @Test
    public  void updateFournisseurTest(){

    }
    @Test
    public  void deleteFournisseurTest(){

    }
    @Test
    public  void getFournisseurTest(){

    }




}
