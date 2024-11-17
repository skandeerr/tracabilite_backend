package com.tracabilite.tracabilite.service.impl;

import com.tracabilite.tracabilite.persistence.dao.IArticleDao;
import com.tracabilite.tracabilite.persistence.model.Article;
import com.tracabilite.tracabilite.service.IArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ArticleService extends GenericService<Article, Long> implements IArticleService {
    @Autowired
    private IArticleDao articleDao;
    @Override
    public Optional<Article> findByCode(String code) {
        return articleDao.findByCode(code);
    }
}
