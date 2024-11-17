package com.tracabilite.tracabilite.service;

import jakarta.transaction.Transactional;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Transactional
public interface IGenericService<T, P extends Serializable> {

    void deleteById(P id);

    void delete(T entity);

    void deleteAll(Iterable<? extends T> entities);

    void deleteInBatch(Iterable<T> entities);

    void deleteAll();

    void deleteAllInBatch();

    Optional<T> findById(P id);

    /*List<T> findByIdIn(Iterable<Long> ids);*/

    Set<T> findByIdIn(Set<Long> set);

    T getOne(P id);

    boolean existsById(P id);

    List<T> findAll();

    List<T> findAllById(Iterable<P> ids);

    List<T> findAll(Sort sort);

    Page<T> findAll(Pageable pageable);

    Optional<T> findOne(@Nullable Specification<T> spec);

    List<T> findAll(@Nullable Specification<T> spec);

    Page<T> findAll(@Nullable Specification<T> spec, Pageable pageable);

    List<T> findAll(@Nullable Specification<T> spec, Sort sort);

    <S extends T> Optional<S> findOne(Example<S> example);

    <S extends T> long count(Example<S> example);

    <S extends T> boolean exists(Example<S> example);

    <S extends T> List<S> findAll(Example<S> example);

    <S extends T> List<S> findAll(Example<S> example, Sort sort);

    <S extends T> Page<S> findAll(Example<S> example, Pageable pageable);

    int countEntities();

    long count(@Nullable Specification<T> spec);

    <S extends T> S save(S entity);

    <S extends T> S saveAndFlush(S entity);

    <S extends T> List<S> saveAll(Iterable<S> entities);

    void flush();

    T update(T entity);

}
