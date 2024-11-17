package com.tracabilite.tracabilite.service.impl;

import com.tracabilite.tracabilite.persistence.dao.IBandeCommandeDao;
import com.tracabilite.tracabilite.persistence.dao.IBandeLivraisonDao;
import com.tracabilite.tracabilite.persistence.model.BandeLivraison;
import com.tracabilite.tracabilite.service.IBandeLivraisonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class BandeLivraisonService extends GenericService<BandeLivraison, Long> implements IBandeLivraisonService {
    @Autowired
    private IBandeLivraisonDao bandeLivraisonDao;
    @Override
    public List<BandeLivraison> findByBandeCommandeId(Long bandeCommandeId) {
        return bandeLivraisonDao.findByBandeCommandeId(bandeCommandeId);
    }
}
