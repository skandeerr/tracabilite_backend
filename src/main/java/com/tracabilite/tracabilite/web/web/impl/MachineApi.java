package com.tracabilite.tracabilite.web.web.impl;

import com.tracabilite.tracabilite.persistence.model.Article;
import com.tracabilite.tracabilite.persistence.model.Client;
import com.tracabilite.tracabilite.persistence.model.Machine;
import com.tracabilite.tracabilite.persistence.model.Matiere;
import com.tracabilite.tracabilite.service.IClientService;
import com.tracabilite.tracabilite.service.IMachineService;
import com.tracabilite.tracabilite.web.common.ApiMessage;
import com.tracabilite.tracabilite.web.common.utils.HttpErrorResponse;
import com.tracabilite.tracabilite.web.common.utils.HttpMessageResponse;
import com.tracabilite.tracabilite.web.dto.ClientCreateDto;
import com.tracabilite.tracabilite.web.dto.MachineCreateDto;
import com.tracabilite.tracabilite.web.dto.MatiereCreateDto;
import com.tracabilite.tracabilite.web.mapper.ArticleMapper;
import com.tracabilite.tracabilite.web.mapper.ClientMapper;
import com.tracabilite.tracabilite.web.mapper.MachineMapper;
import com.tracabilite.tracabilite.web.mapper.MatiereMapper;
import com.tracabilite.tracabilite.web.web.api.IMachineApi;
import com.tracabilite.tracabilite.web.web.api.IMatiereApi;
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
public class MachineApi implements IMachineApi {
    private Object httpResponseBody;

    private HttpStatus httpStatus;
    @Autowired
    private IMachineService machineService;

    @Override
    public ResponseEntity<?> getMachines() {
        log.info("Web service getArticles is invoked");
        try {
            List<Machine> machines = machineService.findAll();
            if(machines.isEmpty()){
                httpResponseBody = machines;
            }else {
                httpResponseBody = machines.stream().map(MachineMapper.MAPPER::toDto).collect(Collectors.toList());
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
    public ResponseEntity<?> getMachineById(@PathVariable("machineId") Long machineId) {
        log.info("Web service getProspectById is invoked");
        try {
            Optional<Machine> machine = machineService.findById(machineId);
            if (machine.isPresent()) {
                httpStatus = HttpStatus.OK;
                httpResponseBody = MachineMapper.MAPPER.toDto(machine.get());
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
    public ResponseEntity<?> saveMachine(@ApiParam(required = true, value = "machine", name = "machine")
                                             @RequestBody MachineCreateDto machineCreateDto, BindingResult bindingResult) {
        log.info("Web service saveMachine is invoked with args machineCreateDto {}", machineCreateDto);
        if (!bindingResult.hasFieldErrors()) {
            try {
                Optional<Machine> machine = machineService.findByName(machineCreateDto.getName());
                if (!machine.isEmpty()) {
                    httpStatus = HttpStatus.BAD_REQUEST;
                    httpResponseBody = new HttpErrorResponse(400, ApiMessage.MACHINE_ALREADY_EXIST);
                } else {
                    Machine newMachine = MachineMapper.MAPPER.toMachine(machineCreateDto);
                    Machine machineToBeSave =machineService.save(newMachine);
                    httpResponseBody = MachineMapper.MAPPER.toDto(machineToBeSave);
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
    public ResponseEntity<?> editMachine(@PathVariable("machineId") Long machineId, @RequestBody MachineCreateDto machineCreateDto, BindingResult bindingResult) {
        log.info("Web service editArticle invoked with id {} and articleCreateDto {}", machineId, machineCreateDto);
        if (!bindingResult.hasFieldErrors()) {
            try {
                Optional<Machine> machine = machineService.findById(machineId);
                if (machine.isPresent()) {
                    Optional<Machine> existingMachine = machineService.findByName(machineCreateDto.getName());
                    if(existingMachine.isPresent() && !existingMachine.get().getId().equals(machineId)){
                        httpStatus = HttpStatus.BAD_REQUEST;
                        httpResponseBody = new HttpErrorResponse(400, "MACHINE_NAME_ALREADY_EXIST");

                    }else {
                        machine.get().setName(machineCreateDto.getName());
                        machineService.update(machine.get());
                        httpResponseBody = MachineMapper.MAPPER.toDto(machine.get());
                        httpStatus = HttpStatus.OK;
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

    @Override
    public ResponseEntity<?> deleteMachine(@ApiParam(value = "ID of machine that will be deleted", required = true, allowableValues = "range[1,infinity]") @PathVariable("machineId") Long machineId) {
        log.info("Web service deleteMachine invoked with id {}", machineId);
        try {
            Optional<Machine> machine = machineService.findById(machineId);
            if (machine.isPresent()) {
                machineService.delete(machine.get());
                httpResponseBody = new HttpMessageResponse(ApiMessage.MACHINE_DELETED_SUCCESSFULLY);
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
