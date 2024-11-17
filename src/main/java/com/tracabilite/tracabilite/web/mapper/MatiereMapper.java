package com.tracabilite.tracabilite.web.mapper;

import com.tracabilite.tracabilite.persistence.model.Machine;
import com.tracabilite.tracabilite.persistence.model.Matiere;
import com.tracabilite.tracabilite.web.dto.MachineCreateDto;
import com.tracabilite.tracabilite.web.dto.MachineDto;
import com.tracabilite.tracabilite.web.dto.MatiereCreateDto;
import com.tracabilite.tracabilite.web.dto.MatiereDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = {})
public interface MatiereMapper {
    MatiereMapper MAPPER = Mappers.getMapper(MatiereMapper.class);

    MatiereDto toDto(Matiere machine);
    Matiere toMatiere(MatiereCreateDto matiereCreateDto);
}
