package com.tracabilite.tracabilite.service.impl;

import com.tracabilite.tracabilite.persistence.dao.IGenericDao;
import com.tracabilite.tracabilite.service.IGenericService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class GenericService<T, P extends Serializable> implements IGenericService<T, P> {


    @Autowired
    private IGenericDao<T, P> genericDao;

    @Override
    public void deleteById(P id) {
        genericDao.deleteById(id);
    }

    @Override
    public void deleteInBatch(Iterable<T> entities) {
        genericDao.deleteInBatch(entities);
    }

    @Override
    public void deleteAll() {
        genericDao.deleteAll();
    }

    @Override
    public void deleteAllInBatch() {
        genericDao.deleteAllInBatch();
    }

    @Override
    public Optional<T> findById(P id) {
        Optional<T> returnedObject;
        returnedObject = genericDao.findById(id);
        return returnedObject;
    }

    @Override
    public T getOne(P id) {
        return genericDao.getOne(id);
    }

    @Override
    public boolean existsById(P id) {
        return genericDao.existsById(id);
    }

    @Override
    public List<T> findAll() {
        return genericDao.findAll();
    }

    @Override
    public List<T> findAllById(Iterable<P> ids) {
        return genericDao.findAllById(ids);
    }

    @Override
    public List<T> findAll(Sort sort) {
        return genericDao.findAll(sort);
    }

    @Override
    public Page<T> findAll(Pageable pageable) {
        return genericDao.findAll(pageable);
    }

    @Override
    public Optional<T> findOne(Specification<T> spec) {
        return genericDao.findOne(spec);
    }

    @Override
    public List<T> findAll(Specification<T> spec) {
        return genericDao.findAll(spec);
    }

    @Override
    public Page<T> findAll(Specification<T> spec, Pageable pageable) {
        return genericDao.findAll(spec, pageable);
    }

    @Override
    public List<T> findAll(Specification<T> spec, Sort sort) {
        return genericDao.findAll(spec, sort);
    }

    @Override
    public <S extends T> Optional<S> findOne(Example<S> example) {
        return genericDao.findOne(example);
    }

    @Override
    public <S extends T> long count(Example<S> example) {
        return genericDao.count(example);
    }

    @Override
    public <S extends T> boolean exists(Example<S> example) {
        return genericDao.exists(example);
    }

    @Override
    public <S extends T> List<S> findAll(Example<S> example) {
        return genericDao.findAll(example);
    }

    @Override
    public <S extends T> List<S> findAll(Example<S> example, Sort sort) {
        return genericDao.findAll(example, sort);
    }

    @Override
    public <S extends T> Page<S> findAll(Example<S> example, Pageable pageable) {
        return genericDao.findAll(example, pageable);
    }

    @Override
    public int countEntities() {
        return (int) genericDao.count();
    }

    @Override
    public long count(Specification<T> spec) {
        return genericDao.count(spec);
    }

    @Override
    public <S extends T> S save(S entity) {
        return genericDao.save(entity);
    }

    @Override
    public <S extends T> S saveAndFlush(S entity) {
        return genericDao.saveAndFlush(entity);
    }

    @Override
    public <S extends T> List<S> saveAll(Iterable<S> entities) {
        return genericDao.saveAll(entities);
    }

    @Override
    public void flush() {
        genericDao.flush();
    }

    @Override
    public T update(T entity) {
        return genericDao.save(entity);
    }

    @Override
    public void delete(T entity) {
        genericDao.delete(entity);
    }

    @Override
    public void deleteAll(Iterable<? extends T> entities) {
        genericDao.deleteAll(entities);

    }

/*    @Override
    public List<T> findByIdIn(Iterable<Long> ids) {
        List<T> returnedList;
        returnedList = genericDao.findByIdIn(ids);
        return returnedList;
    }*/

    @Override
    public Set<T> findByIdIn(Set<Long> set) {
        Set<T> returnedSet;
        returnedSet = (Set<T>) genericDao.findByIdIn(set);
        return returnedSet;
    }

}
