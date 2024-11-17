package com.tracabilite.tracabilite.service;

import com.tracabilite.tracabilite.persistence.model.Matiere;

import java.util.Optional;

public interface IMatiereService extends IGenericService<Matiere, Long>{
    Optional<Matiere> findByRef(String ref);
}
