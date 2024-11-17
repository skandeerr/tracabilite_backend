package com.tracabilite.tracabilite.persistence.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;
import java.util.Set;

@NoRepositoryBean
public interface IGenericDao  <T, P extends Serializable> extends JpaRepository<T, P>, JpaSpecificationExecutor<T> {
    Set<T> findByIdIn(Set<Long> set);

}
