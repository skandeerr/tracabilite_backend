package com.tracabilite.tracabilite.service;

import com.tracabilite.tracabilite.persistence.model.BandeCommande;

import java.util.Optional;

public interface IBandeCommandeService  extends IGenericService<BandeCommande, Long>{
    Optional<BandeCommande> findByDesignation(String designation);

}
