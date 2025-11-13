package com.tricol.fournix.service;

import com.tricol.fournix.mapper.ProduitMapper;
import com.tricol.fournix.model.Produit;
import com.tricol.fournix.repository.ProduitRepository;
import com.tricol.fournix.service.Implimentation.ProduitServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TestProduitServiceImpl {
    @Mock
    ProduitRepository produitRepository;

    @Mock
    ProduitMapper produitMapper;

    @InjectMocks
    ProduitServiceImpl produitService;

    @Test
    void  TestSaveProduit(){
        Produit produit =  new Produit();
        produit.setId(1L);
        produit.setNom("Pc gemaing");

        when(produitRepository.save(produit)).thenReturn(produit);

        Produit saved =  produitService.save(produit);

        assertThat(saved).isNotNull();
        verify(produitRepository).save(produit);
    }

    @Test
    void  TestUpdateProduit(){
        Long id = 1L;
        Produit pr= new Produit();
        pr.setId(id);
        pr.setNom("mouse gaming");

        Produit prUpdate= new Produit();
        prUpdate.setId(id);
        prUpdate.setNom("Mouse normal");

        when(produitRepository.save(pr)).thenReturn(prUpdate);
        Produit saved= produitService.save(prUpdate) ;

        verify(produitRepository).save(prUpdate);

    }

    @Test
    void  TestDeleteProduit(){

        Produit prUpdate= new Produit();
        prUpdate.setId(1L);
        prUpdate.setNom("Mouse normal");
        produitService.delete(1);

        verify(produitRepository, times(1)).deleteById(1);

    }

    @Test
    void  TestFindProduit(){
        Produit produit = new Produit();
        produit.setId(1L);
        produit.setNom("Test");

        when(produitRepository.findById(1)).thenReturn(Optional.of(produit));

        Produit rslt = produitService.findById(1).get();

        assertEquals("Test", rslt.getNom());
        verify(produitRepository).findById(1);
    }

//    @Test
//    void TestFindAllProduit() {
//        Produit produit1 = new Produit();
//        produit1.setId(1L);
//        produit1.setNom("Produit A");
//
//        Produit produit2 = new Produit();
//        produit2.setId(2L);
//        produit2.setNom("Produit B");
//
//        List<Produit> produits = Arrays.asList(produit1, produit2);
//
//        when(produitRepository.findAll()).thenReturn(produits);
//
//        List<Produit> result = produitService.findAll();
//
//        assertEquals(2, result.size());
//        assertEquals("Produit A", result.get(0).getNom());
//        verify(produitRepository, times(1)).findAll();
//    }


}
