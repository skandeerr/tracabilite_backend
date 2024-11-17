package com.tracabilite.tracabilite.service;

import com.tracabilite.tracabilite.persistence.model.BandeLivraison;

import java.util.List;


public interface IBandeLivraisonService  extends IGenericService<BandeLivraison, Long>{
    List<BandeLivraison> findByBandeCommandeId(Long bandeCommandeId);

}
