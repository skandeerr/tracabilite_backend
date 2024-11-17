package com.tracabilite.tracabilite.web.web.impl;

import com.tracabilite.tracabilite.persistence.enumeration.BandeCommandeStatus;
import com.tracabilite.tracabilite.persistence.model.BandeCommande;
import com.tracabilite.tracabilite.persistence.model.BandeLivraison;
import com.tracabilite.tracabilite.service.IBandeCommandeService;
import com.tracabilite.tracabilite.service.IBandeLivraisonService;
import com.tracabilite.tracabilite.service.IClientService;
import com.tracabilite.tracabilite.web.common.ApiMessage;
import com.tracabilite.tracabilite.web.common.utils.HttpErrorResponse;
import com.tracabilite.tracabilite.web.common.utils.HttpMessageResponse;
import com.tracabilite.tracabilite.web.dto.BandeCommandeCreateDto;
import com.tracabilite.tracabilite.web.mapper.BandeCommandeMapper;
import com.tracabilite.tracabilite.web.web.api.IBandeCommandeApi;
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
public class BandeCommandeApi implements IBandeCommandeApi {
    private Object httpResponseBody;

    private HttpStatus httpStatus;
    @Autowired
    private IBandeCommandeService bandeCommandeService;
    @Autowired
    private IClientService clientService;
    @Autowired
    private IBandeLivraisonService bandeLivraisonService;
    @Override
    public ResponseEntity<?> getBandeCommandes() {
        log.info("Web service getBandeCommandes is invoked");
        try {
            List<BandeCommande> bandeCommandes = bandeCommandeService.findAll();
            if(bandeCommandes.isEmpty()){
                httpResponseBody = bandeCommandes;;
            }else {
                httpResponseBody = bandeCommandes.stream().map(BandeCommandeMapper.MAPPER::toDto).collect(Collectors.toList());
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
    public ResponseEntity<?> getBandeCommandeById(@PathVariable("bandeCommandeId") Long bandeCommandeId) {
        log.info("Web service getBandeCommandeById is invoked");
        try {
            Optional<BandeCommande> bandeCommande = bandeCommandeService.findById(bandeCommandeId);
            if (bandeCommande.isPresent()) {
                httpStatus = HttpStatus.OK;
                httpResponseBody = BandeCommandeMapper.MAPPER.toDto(bandeCommande.get());
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
    public ResponseEntity<?> saveBandeCommande(@ApiParam(required = true, value = "bandeCommande", name = "bandeCommande") @RequestBody BandeCommandeCreateDto bandeCommandeCreateDto, BindingResult bindingResult) {
        log.info("Web service saveBandeCommande is invoked with args bandeCommandeCreateDto {}", bandeCommandeCreateDto);
        if (!bindingResult.hasFieldErrors()) {
            try {
                    BandeCommande newBandeCommande = BandeCommandeMapper.MAPPER.toBandeCommande(bandeCommandeCreateDto);
                    newBandeCommande.setClient(clientService.findById(bandeCommandeCreateDto.getClient()).get());
                    BandeCommande bandeCommandeToBeSave =bandeCommandeService.save(newBandeCommande);
                    httpResponseBody = BandeCommandeMapper.MAPPER.toDto(bandeCommandeToBeSave);
                    httpStatus = HttpStatus.OK;
                }
             catch (Exception ex) {
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
    public ResponseEntity<?> editBandeCommande(@PathVariable("bandeCommandeId") Long bandeCommandeId,@RequestBody BandeCommandeCreateDto bandeCommandeCreateDto, BindingResult bindingResult) {
        log.info("Web service editArticle invoked with id {} and bandeCommandeCreateDto {}", bandeCommandeId, bandeCommandeCreateDto);
        if (!bindingResult.hasFieldErrors()) {
            try {
                Optional<BandeCommande> bandeCommande = bandeCommandeService.findById(bandeCommandeId);
                if (bandeCommande.isPresent()) {
                    Optional<BandeCommande> existingArticle = bandeCommandeService.findByDesignation(bandeCommandeCreateDto.getDesignation());
                    if(existingArticle.isPresent() && !existingArticle.get().getId().equals(bandeCommandeId)){
                        httpStatus = HttpStatus.BAD_REQUEST;
                        httpResponseBody = new HttpErrorResponse(400, "BANDE_COMMANDE_ALREADY_EXIST");

                    }else {
                        updateExistantBandeCommande(bandeCommandeCreateDto, bandeCommande.get());
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
    public ResponseEntity<?> deleteBandeCommande(@ApiParam(value = "ID of bande commande that will be deleted", required = true, allowableValues = "range[1,infinity]") @PathVariable("bandeCommandeId") Long bandeCommandeId) {
        log.info("Web service deleteBandeCommande invoked with id {}", bandeCommandeId);
        try {
            Optional<BandeCommande> bandeCommande = bandeCommandeService.findById(bandeCommandeId);

            if (bandeCommande.isPresent()) {
                List<BandeLivraison> bandeLivraisons = bandeLivraisonService.findAll().stream().filter(bandeLivraison -> bandeLivraison.getBandeCommande().getId().equals(bandeCommande.get().getId())).collect(Collectors.toList());
                bandeCommandeService.delete(bandeCommande.get());
                bandeLivraisons.forEach(bandeLivraison -> {
                    bandeLivraisonService.delete(bandeLivraison);
                });
                httpResponseBody = new HttpMessageResponse(ApiMessage.BANDE_COMMANDE_DELETED_SUCCESSFULLY);
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

    private void updateExistantBandeCommande(BandeCommandeCreateDto bandeCommandeCreateDto, BandeCommande bandeCommande) {
        bandeCommande.setBandeCommandeStatus(BandeCommandeStatus.valueOf(bandeCommandeCreateDto.getBandeCommandeStatus()));
        bandeCommande.setDesignation(bandeCommandeCreateDto.getDesignation());
        bandeCommande.setEndDate(bandeCommandeCreateDto.getEndDate());
        bandeCommande.setStartDate(bandeCommandeCreateDto.getStartDate());
        bandeCommande.setPersonnelId(bandeCommandeCreateDto.getPersonnelId());
        bandeCommande.setClient(clientService.findById(bandeCommandeCreateDto.getClient()).get());
        bandeCommandeService.update(bandeCommande);
        httpResponseBody = BandeCommandeMapper.MAPPER.toDto(bandeCommande);
        httpStatus = HttpStatus.OK;
    }
}
