package com.tricol.fournix.repository;

import com.tricol.fournix.model.MovmentStock;
import com.tricol.fournix.model.enums.TypeMovment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MovmentStockRepository extends JpaRepository<MovmentStock,Integer> {
    List<MovmentStock> findByProduitId(Long produitId);
    List<MovmentStock> findByCommandeId(Long commandeId);
    List<MovmentStock> findByTypeMovment(TypeMovment typeMovment);
}
