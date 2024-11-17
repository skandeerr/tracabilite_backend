package com.tracabilite.tracabilite.web.mapper;

import com.tracabilite.tracabilite.persistence.enumeration.BandeCommandeStatus;
import com.tracabilite.tracabilite.persistence.model.Article;
import com.tracabilite.tracabilite.persistence.model.BandeCommande;
import com.tracabilite.tracabilite.web.dto.ArticleCreateDto;
import com.tracabilite.tracabilite.web.dto.BandeCommandeCreateDto;
import com.tracabilite.tracabilite.web.dto.BandeCommandeDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = {})
public interface BandeCommandeMapper {
    BandeCommandeMapper MAPPER = Mappers.getMapper(BandeCommandeMapper.class);

    BandeCommandeDto toDto(BandeCommande bandeCommande);

    default BandeCommande toBandeCommande(BandeCommandeCreateDto bandeCommandeCreateDto) {
        return BandeCommande.builder()
                .bandeCommandeStatus(BandeCommandeStatus.valueOf(bandeCommandeCreateDto.getBandeCommandeStatus()))
                .designation(bandeCommandeCreateDto.getDesignation())
                .endDate(bandeCommandeCreateDto.getEndDate())
                .startDate(bandeCommandeCreateDto.getStartDate())
                .personnelId(bandeCommandeCreateDto.getPersonnelId())
                .build();
    }
}
