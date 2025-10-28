package com.tricol.fournix.repository;

import com.tricol.fournix.model.Fournisseur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface FournisseurRepository extends JpaRepository<Fournisseur, Integer> {

        List<Fournisseur> findByNom(String nom);

        @Query("SELECT f FROM Fournisseur f WHERE f.email ILIKE %?1")
        List<Fournisseur> findByEmailEndingWith(String ending);

    }
