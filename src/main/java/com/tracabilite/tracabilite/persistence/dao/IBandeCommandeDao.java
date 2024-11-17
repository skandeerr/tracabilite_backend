package com.tracabilite.tracabilite.persistence.dao;

import com.tracabilite.tracabilite.persistence.model.BandeCommande;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IBandeCommandeDao extends IGenericDao<BandeCommande, Long>{
    Optional<BandeCommande> findByDesignation(String designation);
}
