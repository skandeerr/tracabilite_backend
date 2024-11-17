package com.tracabilite.tracabilite.service.impl;

import com.tracabilite.tracabilite.persistence.dao.IBandeCommandeDao;
import com.tracabilite.tracabilite.persistence.model.BandeCommande;
import com.tracabilite.tracabilite.service.IBandeCommandeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class BandeCommandeService extends GenericService<BandeCommande, Long> implements IBandeCommandeService {
    @Autowired
    private IBandeCommandeDao bandeCommandeDao;
    @Override
    public Optional<BandeCommande> findByDesignation(String designation) {
        return bandeCommandeDao.findByDesignation(designation);
    }
}
