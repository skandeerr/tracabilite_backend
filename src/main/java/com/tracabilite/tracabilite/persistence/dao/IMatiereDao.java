package com.tracabilite.tracabilite.persistence.dao;

import com.tracabilite.tracabilite.persistence.model.Machine;
import com.tracabilite.tracabilite.persistence.model.Matiere;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IMatiereDao extends IGenericDao<Matiere, Long>{
    Optional<Matiere> findByRef(String ref);
}
