package com.tracabilite.tracabilite.persistence.dao;

import com.tracabilite.tracabilite.persistence.model.Client;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IClientDao extends IGenericDao<Client, Long>{
    Optional<Client> findByEmail(String email);
}
