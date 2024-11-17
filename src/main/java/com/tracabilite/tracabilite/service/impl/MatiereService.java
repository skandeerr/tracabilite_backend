package com.tracabilite.tracabilite.service.impl;

import com.tracabilite.tracabilite.persistence.dao.IMatiereDao;
import com.tracabilite.tracabilite.persistence.model.Matiere;
import com.tracabilite.tracabilite.service.IMatiereService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MatiereService extends GenericService<Matiere, Long> implements IMatiereService {
    @Autowired
    private IMatiereDao matiereDao;
    @Override
    public Optional<Matiere> findByRef(String ref) {
        return matiereDao.findByRef(ref);
    }
}
