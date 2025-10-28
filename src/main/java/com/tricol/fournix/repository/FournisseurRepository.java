package com.tricol.fournix.repository;

import com.tricol.fournix.model.Fournisseur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

    public interface FournisseurRepository extends JpaRepository<Fournisseur, Integer> { }
