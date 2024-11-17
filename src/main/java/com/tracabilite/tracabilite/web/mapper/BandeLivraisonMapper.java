package com.tracabilite.tracabilite.web.mapper;

import com.tracabilite.tracabilite.persistence.model.Article;
import com.tracabilite.tracabilite.persistence.model.BandeLivraison;
import com.tracabilite.tracabilite.web.dto.ArticleDto;
import com.tracabilite.tracabilite.web.dto.BandeLivraisonDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = {})
public interface BandeLivraisonMapper {
    BandeLivraisonMapper MAPPER = Mappers.getMapper(BandeLivraisonMapper.class);

    BandeLivraisonDto toDto(BandeLivraison bandeLivraison);

}
