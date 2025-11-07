package com.tricol.fournix.repository;

import com.tricol.fournix.model.Fournisseur;
import com.tricol.fournix.model.MovmentStock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface FournisseurRepository extends JpaRepository<Fournisseur, Integer> {

         Page<Fournisseur> findAll(Pageable pageable);
        List<Fournisseur> findByNom(String nom);

        @Query("SELECT f FROM Fournisseur f WHERE f.email ILIKE %?1")
        List<Fournisseur> findByEmailEndingWith(String ending);

    }
