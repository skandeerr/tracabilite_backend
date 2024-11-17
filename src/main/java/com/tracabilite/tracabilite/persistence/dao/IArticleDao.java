package com.tracabilite.tracabilite.persistence.dao;

import com.tracabilite.tracabilite.persistence.model.Article;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IArticleDao extends IGenericDao<Article, Long>{
    Optional<Article> findByCode(String code);
}
