package com.tricol.fournix.service;

import com.tricol.fournix.model.Fournisseur;
import com.tricol.fournix.repository.FournisseurRepository;
import com.tricol.fournix.service.Implimentation.fournisseurServiceImpli;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
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
    @DisplayName("Doit créer un fournisseur correctement")
    public  void TestCreateFournisseur(){

        Fournisseur fr = new Fournisseur();
        fr.setNom("Charaf");
        fr.setEmail("charaf@example.com");

        when(fournisseurRepository.save(any(Fournisseur.class))).thenReturn(fr);

        Fournisseur saved =  fournisseurService.createFournisseur(fr);

        assertThat(saved).isNotNull();
        assertEquals("charaf",saved.getNom());

        verify(fournisseurRepository, times(1)).save(fr);
    }


    @Test
    public  void updateFournisseurTest(){
        Fournisseur fr = new Fournisseur();
        fr.setId(1L);
        fr.setNom("rayan");

        Fournisseur frUpdate = new Fournisseur();
        frUpdate.setId(1L);
        frUpdate.setNom("moncif");

        when(fournisseurRepository.save(frUpdate)).thenReturn(frUpdate);

        Fournisseur resulta= fournisseurService.updateFournisseur((frUpdate));

        assertThat(resulta.getNom()).isEqualTo("moncif");
        verify(fournisseurRepository).save(frUpdate);

    }

    @Test
    public  void deleteFournisseurTest(){

    }
    @Test
    public  void getFournisseurTest(){

    }




}
