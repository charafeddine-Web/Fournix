package com.tricol.fournix.service;

import com.tricol.fournix.repository.ProduitRepository;
import com.tricol.fournix.service.Implimentation.ProduitServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class TestProduitServiceImpl {
    @Mock
    ProduitRepository produitRepository;

    @InjectMocks
    ProduitServiceImpl produitService;

    @Test
    void  TestSaveProduit(){

    }

    @Test
    void  TestUpdateProduit(){

    }

    @Test
    void  TestDeleteProduit(){

    }

    @Test
    void  TestFindAllProduit(){

    }



}
