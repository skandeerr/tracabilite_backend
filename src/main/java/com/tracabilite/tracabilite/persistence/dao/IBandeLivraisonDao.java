package com.tracabilite.tracabilite.persistence.dao;

import com.tracabilite.tracabilite.persistence.model.BandeLivraison;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IBandeLivraisonDao extends IGenericDao<BandeLivraison, Long>{
    List<BandeLivraison> findByBandeCommandeId(Long bandeCommandeId);
}
