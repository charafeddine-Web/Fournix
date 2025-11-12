package com.tricol.fournix.repository;

import com.tricol.fournix.model.Fournisseur;
import com.tricol.fournix.model.Produit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProduitRepository  extends JpaRepository<Produit, Integer> {
}
