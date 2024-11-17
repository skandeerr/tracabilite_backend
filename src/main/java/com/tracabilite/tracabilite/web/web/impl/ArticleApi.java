package com.tracabilite.tracabilite.web.web.impl;

import com.tracabilite.tracabilite.persistence.model.Article;
import com.tracabilite.tracabilite.persistence.model.Client;
import com.tracabilite.tracabilite.service.IArticleService;
import com.tracabilite.tracabilite.service.IMatiereService;
import com.tracabilite.tracabilite.web.common.ApiMessage;
import com.tracabilite.tracabilite.web.common.utils.HttpErrorResponse;
import com.tracabilite.tracabilite.web.common.utils.HttpMessageResponse;
import com.tracabilite.tracabilite.web.dto.ArticleCreateDto;
import com.tracabilite.tracabilite.web.dto.ClientCreateDto;
import com.tracabilite.tracabilite.web.mapper.ArticleMapper;
import com.tracabilite.tracabilite.web.mapper.ClientMapper;
import com.tracabilite.tracabilite.web.web.api.IArticleApi;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.tracabilite.tracabilite.web.common.ApiMessage.NOT_FOUND;
import static com.tracabilite.tracabilite.web.common.ApiMessage.SERVER_ERROR_OCCURRED;

@Slf4j
@RestController
@CrossOrigin("*")
public class ArticleApi implements IArticleApi {
    private Object httpResponseBody;

    private HttpStatus httpStatus;
    @Autowired
    private IArticleService articleService;
    @Autowired
    private IMatiereService matiereService;
    @Override
    public ResponseEntity<?> getArticles() {
        log.info("Web service getArticles is invoked");
        try {
            List<Article> articles = articleService.findAll();
            if(articles.isEmpty()){
                httpResponseBody = articles;;
            }else {
                httpResponseBody = articles.stream().map(ArticleMapper.MAPPER::toDto).collect(Collectors.toList());
            }
            httpStatus = HttpStatus.OK;
        } catch (Exception ex) {
            httpResponseBody = new HttpErrorResponse(500, SERVER_ERROR_OCCURRED);
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            log.error("ERROR - {}", ex);
        }
        return new ResponseEntity<>(httpResponseBody, httpStatus);
    }

    @Override
    public ResponseEntity<?> getArticleById(@PathVariable("articleId") Long articleId) {
        log.info("Web service getProspectById is invoked");
        try {
            Optional<Article> article = articleService.findById(articleId);
            if (article.isPresent()) {
                httpStatus = HttpStatus.OK;
                httpResponseBody = ArticleMapper.MAPPER.toDto(article.get());
            } else {
                httpStatus = HttpStatus.NOT_FOUND;
                httpResponseBody = new HttpErrorResponse(404, NOT_FOUND);
            }
        } catch (Exception ex) {
            httpResponseBody = new HttpErrorResponse(500, SERVER_ERROR_OCCURRED);
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            log.error("ERROR - {}", ex);
        }
        return new ResponseEntity<>(httpResponseBody, httpStatus);
    }

    @Override
    public ResponseEntity<?> saveArticle(@ApiParam(required = true, value = "article", name = "article")
                                             @RequestBody ArticleCreateDto articleCreateDto, BindingResult bindingResult) {
        log.info("Web service saveArticle is invoked with args clientCreateDto {}", articleCreateDto);
        if (!bindingResult.hasFieldErrors()) {
            try {
                Optional<Article> article = articleService.findByCode(articleCreateDto.getCode());
                if (!article.isEmpty()) {
                    httpStatus = HttpStatus.BAD_REQUEST;
                    httpResponseBody = new HttpErrorResponse(400, ApiMessage.CLIENT_ALREADY_EXIST);
                } else {
                    Article newArticle = ArticleMapper.MAPPER.toArticle(articleCreateDto);
                    System.out.println(newArticle);
                    newArticle.setMatiere(matiereService.findById(articleCreateDto.getMatiere()).get());
                    Article articleToBeSave =articleService.save(newArticle);
                    httpResponseBody = ArticleMapper.MAPPER.toDto(articleToBeSave);
                    httpStatus = HttpStatus.OK;
                }
            } catch (Exception ex) {
                httpResponseBody = new HttpErrorResponse(500, SERVER_ERROR_OCCURRED);
                httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
                log.error("ERROR - {}", ex);
            }
        } else {
            httpResponseBody = new HttpErrorResponse(400, ApiMessage.REQUIRED_VALIDATION_FAILED);
            httpStatus = HttpStatus.BAD_REQUEST;
        }
        return new ResponseEntity<>(httpResponseBody, httpStatus);
    }

    @Override
    public ResponseEntity<?> editArticle(@PathVariable("articleId") Long articleId, @RequestBody ArticleCreateDto articleCreateDto, BindingResult bindingResult) {
        log.info("Web service editArticle invoked with id {} and articleCreateDto {}", articleId, articleCreateDto);
        if (!bindingResult.hasFieldErrors()) {
            try {
                Optional<Article> article = articleService.findById(articleId);
                if (article.isPresent()) {
                    Optional<Article> existingArticle = articleService.findByCode(articleCreateDto.getCode());
                    if(existingArticle.isPresent() && !existingArticle.get().getId().equals(articleId)){
                        httpStatus = HttpStatus.BAD_REQUEST;
                        httpResponseBody = new HttpErrorResponse(400, "ARTICLE_ALREADY_EXIST");

                    }else {
                        updateExistantArticle(articleCreateDto, article.get());
                    }
                } else {
                    httpStatus = HttpStatus.NOT_FOUND;
                    httpResponseBody = new HttpErrorResponse(404, NOT_FOUND);
                }
            } catch (Exception ex) {
                httpResponseBody = new HttpErrorResponse(500, SERVER_ERROR_OCCURRED);
                httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
                log.error("ERROR - {}", ex);
            }
        } else {
            httpResponseBody = new HttpErrorResponse(400, ApiMessage.REQUIRED_VALIDATION_FAILED);
            httpStatus = HttpStatus.BAD_REQUEST;
        }
        return new ResponseEntity<>(httpResponseBody, httpStatus);

    }

    private void updateExistantArticle(ArticleCreateDto articleCreateDto, Article article) {
        article.setCode(articleCreateDto.getCode());
        article.setDesignation(articleCreateDto.getDesignation());
        article.setEpaisseur(articleCreateDto.getEpaisseur());
        article.setLongeur(articleCreateDto.getLongeur());
        article.setLargeur(articleCreateDto.getLargeur());
        article.setTonnage(articleCreateDto.getTonnage());
        article.setMatiere(matiereService.findById(articleCreateDto.getMatiere()).get());
        articleService.update(article);
        httpResponseBody = ArticleMapper.MAPPER.toDto(article);
        httpStatus = HttpStatus.OK;
    }

    @Override
    public ResponseEntity<?> deleteArticle(@ApiParam(value = "ID of article that will be deleted", required = true, allowableValues = "range[1,infinity]") @PathVariable("articleId") Long articleId) {
        log.info("Web service deleteArticle invoked with id {}", articleId);
        try {
            Optional<Article> client = articleService.findById(articleId);
            if (client.isPresent()) {
                articleService.delete(client.get());
                httpResponseBody = new HttpMessageResponse(ApiMessage.ARTICLE_DELETED_SUCCESSFULLY);
                httpStatus = HttpStatus.OK;
            } else {
                httpResponseBody = new HttpErrorResponse(404, ApiMessage.NOT_FOUND);
                httpStatus = HttpStatus.NOT_FOUND;
            }
        } catch (Exception ex) {
            httpResponseBody = new HttpErrorResponse(500, ApiMessage.ERROR_DELETE_CONTACT);
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            log.error(ApiMessage.ERROR_LEVEL_MESSAGE, ex);
        }
        return new ResponseEntity<>(httpResponseBody, httpStatus);
    }
}
