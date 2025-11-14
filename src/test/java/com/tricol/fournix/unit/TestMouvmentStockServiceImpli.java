package com.tricol.fournix.unit;

import com.tricol.fournix.dto.MovmentStockDTO;
import com.tricol.fournix.mapper.MovmentStockMapper;
import com.tricol.fournix.model.Commande;
import com.tricol.fournix.model.MovmentStock;
import com.tricol.fournix.model.Produit;
import com.tricol.fournix.model.enums.TypeMovment;
import com.tricol.fournix.repository.MovmentStockRepository;
import com.tricol.fournix.repository.ProduitRepository;
import com.tricol.fournix.service.Implimentation.MouvementStockServiceImpli;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TestMouvmentStockServiceImpli {

    @Mock
    private MovmentStockRepository movmentStockRepository;

    @Mock
    private ProduitRepository produitRepository;

    @Mock
    private MovmentStockMapper movmentStockMapper;

    @InjectMocks
    private MouvementStockServiceImpli service;

    @Captor
    private ArgumentCaptor<Produit> produitCaptor;

    @Captor
    private ArgumentCaptor<MovmentStock> mouvementCaptor;

    @Test
    public void testEnregistrerEntree_updatesProductAndSavesMovement() {

        Produit p = new Produit();
        p.setNom("ProduitA");
        p.setStockActuel(10);
        p.setCoutMoyen(5.0);
        p.setPrixUnit(6.0);

        Commande cmd = new Commande();
        int quantite = 5;
        double prixUnitaire = 8.0;

        when(produitRepository.save(any(Produit.class))).thenAnswer(i -> i.getArgument(0));
        when(movmentStockRepository.save(any(MovmentStock.class))).thenAnswer(i -> i.getArgument(0));

        service.enregistrerEntree(p, cmd, quantite, prixUnitaire);

        verify(produitRepository).save(produitCaptor.capture());
        Produit saved = produitCaptor.getValue();
        assertEquals(15, saved.getStockActuel());
        // nouveau cout moyen = ((5*10)+(8*5))/15 = (50+40)/15 = 90/15 = 6.0
        assertEquals(6.0, saved.getCoutMoyen());

        verify(movmentStockRepository).save(mouvementCaptor.capture());
        MovmentStock mv = mouvementCaptor.getValue();
        assertEquals(TypeMovment.ENTREE, mv.getTypeMovment());
        assertEquals(quantite, mv.getQuantite());
        assertEquals(p, mv.getProduit());
        assertEquals(cmd, mv.getCommande());
        assertNotNull(mv.getDateMovment());
    }

    @Test
    public void testEnregistrerEntree_invalidQuantity_throws() {
        Produit p = new Produit();
        assertThrows(IllegalArgumentException.class, () -> service.enregistrerEntree(p, null, 0, 5.0));
        assertThrows(IllegalArgumentException.class, () -> service.enregistrerEntree(p, null, -1, 5.0));
    }

    @Test
    public void testEnregistrerEntree_invalidPrice_throws() {
        Produit p = new Produit();
        assertThrows(IllegalArgumentException.class, () -> service.enregistrerEntree(p, null, 1, 0.0));
        assertThrows(IllegalArgumentException.class, () -> service.enregistrerEntree(p, null, 1, -1.0));
    }

    @Test
    public void testEnregistrerSortie_updatesProductAndSavesMovement() {
        Produit p = new Produit();
        p.setNom("ProduitB");
        p.setStockActuel(10);
        p.setCoutMoyen(4.0);
        p.setPrixUnit(5.0);

        Commande cmd = new Commande();
        int quantite = 4;

        when(produitRepository.save(any(Produit.class))).thenAnswer(i -> i.getArgument(0));
        when(movmentStockRepository.save(any(MovmentStock.class))).thenAnswer(i -> i.getArgument(0));

        service.enregistrerSortie(p, cmd, quantite);

        verify(produitRepository).save(produitCaptor.capture());
        Produit saved = produitCaptor.getValue();
        assertEquals(6, saved.getStockActuel());

        verify(movmentStockRepository).save(mouvementCaptor.capture());
        MovmentStock mv = mouvementCaptor.getValue();
        assertEquals(TypeMovment.SORTIE, mv.getTypeMovment());
        assertEquals(quantite, mv.getQuantite());
        assertEquals(p, mv.getProduit());
        assertEquals(cmd, mv.getCommande());
        assertNotNull(mv.getDateMovment());
    }

    @Test
    public void testEnregistrerSortie_insufficientStock_throws() {
        Produit p = new Produit();
        p.setNom("ProduitC");
        p.setStockActuel(2);

        assertThrows(IllegalArgumentException.class, () -> service.enregistrerSortie(p, null, 3));
    }

    @Test
    public void testEnregistrerSortie_invalidQuantity_throws() {
        Produit p = new Produit();
        p.setStockActuel(5);
        assertThrows(IllegalArgumentException.class, () -> service.enregistrerSortie(p, null, 0));
        assertThrows(IllegalArgumentException.class, () -> service.enregistrerSortie(p, null, -2));
    }

    @Test
    public void testEnregistrerAjustement_updatesProductAndSavesMovement() {
        Produit p = new Produit();
        p.setStockActuel(7);

        when(produitRepository.save(any(Produit.class))).thenAnswer(i -> i.getArgument(0));
        when(movmentStockRepository.save(any(MovmentStock.class))).thenAnswer(i -> i.getArgument(0));

        service.enregistrerAjustement(p, 10);

        verify(produitRepository).save(produitCaptor.capture());
        Produit saved = produitCaptor.getValue();
        assertEquals(10, saved.getStockActuel());

        verify(movmentStockRepository).save(mouvementCaptor.capture());
        MovmentStock mv = mouvementCaptor.getValue();
        assertEquals(TypeMovment.AJUSTEMENT, mv.getTypeMovment());
        assertEquals(3, mv.getQuantite()); // difference absolue
        assertEquals(p, mv.getProduit());
        assertNotNull(mv.getDateMovment());
    }

    @Test
    public void testEnregistrerAjustement_invalid_throws() {
        Produit p = new Produit();
        assertThrows(IllegalArgumentException.class, () -> service.enregistrerAjustement(p, -1));
    }

    @Test
    public void testGetAllMouvements_delegatesToRepositoryAndMapper() {
        MovmentStock m1 = new MovmentStock();
        MovmentStock m2 = new MovmentStock();
        MovmentStockDTO dto1 = new MovmentStockDTO();
        MovmentStockDTO dto2 = new MovmentStockDTO();

        when(movmentStockRepository.findAll(PageRequest.of(0, 10))).thenReturn(new PageImpl<>(List.of(m1, m2)));
        when(movmentStockMapper.toDTO(m1)).thenReturn(dto1);
        when(movmentStockMapper.toDTO(m2)).thenReturn(dto2);

        Page<MovmentStockDTO> page = service.getAllMouvements(PageRequest.of(0, 10));
        assertEquals(2, page.getContent().size());
        assertTrue(page.getContent().contains(dto1));
        assertTrue(page.getContent().contains(dto2));
    }

    @Test
    public void testFindByProduitId_delegatesToRepositoryAndMapper() {
        MovmentStock m = new MovmentStock();
        MovmentStockDTO dto = new MovmentStockDTO();
        when(movmentStockRepository.findByProduitId(5L, PageRequest.of(0, 5))).thenReturn(new PageImpl<>(List.of(m)));
        when(movmentStockMapper.toDTO(m)).thenReturn(dto);

        Page<MovmentStockDTO> page = service.findByProduitId(5L, PageRequest.of(0, 5));
        assertEquals(1, page.getTotalElements());
        assertEquals(dto, page.getContent().get(0));
    }

    @Test
    public void testFindByCommandeId_andByTypeMovment() {
        MovmentStock m = new MovmentStock();
        MovmentStockDTO dto = new MovmentStockDTO();

        when(movmentStockRepository.findByCommandeId(2L, PageRequest.of(0, 5))).thenReturn(new PageImpl<>(List.of(m)));
        when(movmentStockMapper.toDTO(m)).thenReturn(dto);
        Page<MovmentStockDTO> p1 = service.findByCommandeId(2L, PageRequest.of(0, 5));
        assertEquals(1, p1.getTotalElements());

        when(movmentStockRepository.findByTypeMovment(TypeMovment.ENTREE, PageRequest.of(0, 5))).thenReturn(new PageImpl<>(List.of(m)));
        Page<MovmentStockDTO> p2 = service.findByTypeMovment(TypeMovment.ENTREE, PageRequest.of(0, 5));
        assertEquals(1, p2.getTotalElements());
    }

}
