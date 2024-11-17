package com.tracabilite.tracabilite.service;

import com.tracabilite.tracabilite.persistence.model.Machine;

import java.util.Optional;

public interface IMachineService extends IGenericService<Machine, Long>{
    Optional<Machine> findByName(String name);
}
