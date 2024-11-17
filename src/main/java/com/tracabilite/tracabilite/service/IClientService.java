package com.tracabilite.tracabilite.service;

import com.tracabilite.tracabilite.persistence.model.Client;

import java.util.Optional;

public interface IClientService extends IGenericService<Client, Long>{
    Optional<Client> findByEmail(String email);
}
