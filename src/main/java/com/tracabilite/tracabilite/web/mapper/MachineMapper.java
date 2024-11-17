package com.tracabilite.tracabilite.web.mapper;

import com.tracabilite.tracabilite.persistence.model.Client;
import com.tracabilite.tracabilite.persistence.model.Machine;
import com.tracabilite.tracabilite.web.dto.ClientCreateDto;
import com.tracabilite.tracabilite.web.dto.ClientDto;
import com.tracabilite.tracabilite.web.dto.MachineCreateDto;
import com.tracabilite.tracabilite.web.dto.MachineDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = {})
public interface MachineMapper {
    MachineMapper MAPPER = Mappers.getMapper(MachineMapper.class);

    MachineDto toDto(Machine machine);
    Machine toMachine(MachineCreateDto machineCreateDto);
}
