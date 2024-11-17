package com.tracabilite.tracabilite.service.impl;

import com.tracabilite.tracabilite.persistence.dao.IMachineDao;
import com.tracabilite.tracabilite.persistence.model.Machine;
import com.tracabilite.tracabilite.service.IMachineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MachineService extends GenericService<Machine, Long> implements IMachineService {
    @Autowired
    private IMachineDao machineDao;
    @Override
    public Optional<Machine> findByName(String name) {
        return machineDao.findByName(name);
    }
}
