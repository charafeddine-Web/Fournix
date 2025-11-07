package com.tricol.fournix.repository;

import com.tricol.fournix.model.MovmentStock;
import com.tricol.fournix.model.enums.TypeMovment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MovmentStockRepository extends JpaRepository<MovmentStock,Long> {

    Page<MovmentStock> findByProduitId(Long produitId,Pageable pageable);
    Page<MovmentStock> findByCommandeId(Long commandeId,Pageable pageable);
    Page<MovmentStock> findByTypeMovment(TypeMovment typeMovment,Pageable pageable);

    Page<MovmentStock> findAll(Pageable pageable);
}
