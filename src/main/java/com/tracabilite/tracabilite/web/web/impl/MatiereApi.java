package com.tracabilite.tracabilite.web.web.impl;

import com.tracabilite.tracabilite.persistence.model.Machine;
import com.tracabilite.tracabilite.persistence.model.Matiere;
import com.tracabilite.tracabilite.service.IMachineService;
import com.tracabilite.tracabilite.service.IMatiereService;
import com.tracabilite.tracabilite.web.common.ApiMessage;
import com.tracabilite.tracabilite.web.common.utils.HttpErrorResponse;
import com.tracabilite.tracabilite.web.common.utils.HttpMessageResponse;
import com.tracabilite.tracabilite.web.dto.MatiereCreateDto;
import com.tracabilite.tracabilite.web.mapper.MachineMapper;
import com.tracabilite.tracabilite.web.mapper.MatiereMapper;
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
public class MatiereApi implements IMatiereApi {
    private Object httpResponseBody;

    private HttpStatus httpStatus;
    @Autowired
    private IMatiereService matiereService;
    @Override
    public ResponseEntity<?> getMatieres() {
        log.info("Web service getArticles is invoked");
        try {
            List<Matiere> matieres = matiereService.findAll();
            if(matieres.isEmpty()){
                httpResponseBody = matieres;
            }else {
                httpResponseBody = matieres.stream().map(MatiereMapper.MAPPER::toDto).collect(Collectors.toList());
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
    public ResponseEntity<?> getMatiereById(@PathVariable("matiereId") Long matiereId) {
        log.info("Web service getProspectById is invoked");
        try {
            Optional<Matiere> matiere = matiereService.findById(matiereId);
            if (matiere.isPresent()) {
                httpStatus = HttpStatus.OK;
                httpResponseBody = MatiereMapper.MAPPER.toDto(matiere.get());
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
    public ResponseEntity<?> saveMatiere(@ApiParam(required = true, value = "matiere", name = "matiere")
                                             @RequestBody MatiereCreateDto matiereCreateDto, BindingResult bindingResult) {
        log.info("Web service saveMatiere is invoked with args matiereCreateDto {}", matiereCreateDto);
        if (!bindingResult.hasFieldErrors()) {
            try {
                Optional<Matiere> matiere = matiereService.findByRef(matiereCreateDto.getRef());
                if (!matiere.isEmpty()) {
                    httpStatus = HttpStatus.BAD_REQUEST;
                    httpResponseBody = new HttpErrorResponse(400, ApiMessage.MATIERE_ALREADY_EXIST);
                } else {
                    Matiere newMatiere = MatiereMapper.MAPPER.toMatiere(matiereCreateDto);
                    Matiere matiereToBeSave =matiereService.save(newMatiere);
                    httpResponseBody = MatiereMapper.MAPPER.toDto(matiereToBeSave);
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
    public ResponseEntity<?> editMatiere(@PathVariable("matiereId") Long matiereId, @RequestBody MatiereCreateDto matiereCreateDto, BindingResult bindingResult) {
        log.info("Web service editMatiere invoked with id {} and matiereCreateDto {}", matiereId, matiereCreateDto);
        if (!bindingResult.hasFieldErrors()) {
            try {
                Optional<Matiere> matiere = matiereService.findById(matiereId);
                if (matiere.isPresent()) {
                    Optional<Matiere> existingMachine = matiereService.findByRef(matiereCreateDto.getRef());
                    if(existingMachine.isPresent() && !existingMachine.get().getId().equals(matiereId)){
                        httpStatus = HttpStatus.BAD_REQUEST;
                        httpResponseBody = new HttpErrorResponse(400, "MATIERE_REF_ALREADY_EXIST");

                    }else {
                        matiere.get().setRef(matiereCreateDto.getRef());
                        matiere.get().setTonnage(matiereCreateDto.getTonnage());
                        matiereService.update(matiere.get());
                        httpResponseBody = MatiereMapper.MAPPER.toDto(matiere.get());
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
    public ResponseEntity<?> deleteMatiere(@ApiParam(value = "ID of matiere that will be deleted", required = true, allowableValues = "range[1,infinity]") @PathVariable("matiereId") Long matiereId) {
        log.info("Web service deleteContact invoked with id {}", matiereId);
        try {
            Optional<Matiere> matiere = matiereService.findById(matiereId);
            if (matiere.isPresent()) {
                matiereService.delete(matiere.get());
                httpResponseBody = new HttpMessageResponse(ApiMessage.MATIERE_DELETED_SUCCESSFULLY);
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
