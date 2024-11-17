package com.tracabilite.tracabilite.web.mapper;

import com.tracabilite.tracabilite.persistence.model.Article;
import com.tracabilite.tracabilite.web.dto.ArticleCreateDto;
import com.tracabilite.tracabilite.web.dto.ArticleDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = {})
public interface ArticleMapper {
    ArticleMapper MAPPER = Mappers.getMapper(ArticleMapper.class);
    ArticleDto toDto(Article article);
    default Article toArticle(ArticleCreateDto articleCreateDto) {
        return Article.builder()
                .designation(articleCreateDto.getDesignation())
                .code(articleCreateDto.getCode())
                .epaisseur(articleCreateDto.getEpaisseur())
                .largeur(articleCreateDto.getLargeur())
                .longeur(articleCreateDto.getLongeur())
                .tonnage(articleCreateDto.getTonnage())
                .build();
    }

}
