package com.tricol.fournix.service;

import com.tricol.fournix.mapper.ProduitMapper;
import com.tricol.fournix.model.Produit;
import com.tricol.fournix.repository.ProduitRepository;
import com.tricol.fournix.service.Implimentation.ProduitServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

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

    @Test
    @DisplayName("Doit retourner une page de produits")
    void shouldReturnPagedProducts() {

        Produit p1 = new Produit();
        p1.setId(1L);
        p1.setNom("Produit 1");

        Produit p2 = new Produit();
        p2.setId(2L);
        p2.setNom("Produit 2");

        List<Produit> produits = Arrays.asList(p1, p2);

        Pageable pageable = PageRequest.of(0, 2);

        Page<Produit> page = new PageImpl<>(produits, pageable, produits.size());

        when(produitRepository.findAll(pageable)).thenReturn(page);

        Page<Produit> result = produitService.findAll(pageable);

        assertThat(result).isNotNull();
        assertThat(result.getContent().size()).isEqualTo(2);
        assertThat(result.getContent().get(0).getNom()).isEqualTo("Produit 1");

        verify(produitRepository, times(1)).findAll(pageable);
    }




}
