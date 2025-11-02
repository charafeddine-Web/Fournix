package com.tricol.fournix.repository;

import com.tricol.fournix.model.ProduitCommande;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProduitCommandeRepository  extends JpaRepository<ProduitCommande, Integer> {
}
