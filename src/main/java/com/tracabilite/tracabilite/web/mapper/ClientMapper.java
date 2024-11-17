package com.tracabilite.tracabilite.web.mapper;

import com.tracabilite.tracabilite.persistence.model.Article;
import com.tracabilite.tracabilite.persistence.model.Client;
import com.tracabilite.tracabilite.web.dto.ArticleCreateDto;
import com.tracabilite.tracabilite.web.dto.ArticleDto;
import com.tracabilite.tracabilite.web.dto.ClientCreateDto;
import com.tracabilite.tracabilite.web.dto.ClientDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = {})
public interface ClientMapper {
    ClientMapper MAPPER = Mappers.getMapper(ClientMapper.class);

    ClientDto toDto(Client client);
    Client toClient(ClientCreateDto clientCreateDto);
}
