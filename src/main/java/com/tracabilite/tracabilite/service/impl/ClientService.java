package com.tracabilite.tracabilite.service.impl;

import com.tracabilite.tracabilite.persistence.dao.IClientDao;
import com.tracabilite.tracabilite.persistence.model.Client;
import com.tracabilite.tracabilite.service.IClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClientService extends GenericService<Client, Long> implements IClientService {
    @Autowired
    private IClientDao clientDao;
    @Override
    public Optional<Client> findByEmail(String email) {
        return clientDao.findByEmail(email);
    }
}
