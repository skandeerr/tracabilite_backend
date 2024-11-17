package com.tracabilite.tracabilite.service;

import com.tracabilite.tracabilite.persistence.model.Article;

import java.util.Optional;

public interface IArticleService extends IGenericService<Article, Long>{
    Optional<Article> findByCode(String code);
}
