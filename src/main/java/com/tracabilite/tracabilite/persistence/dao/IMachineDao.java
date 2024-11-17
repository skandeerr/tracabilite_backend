package com.tracabilite.tracabilite.persistence.dao;

import com.tracabilite.tracabilite.persistence.model.Machine;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IMachineDao extends IGenericDao<Machine, Long>{
    Optional<Machine> findByName(String name);
}
