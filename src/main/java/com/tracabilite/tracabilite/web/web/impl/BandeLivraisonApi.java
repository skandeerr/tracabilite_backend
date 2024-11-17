package com.tracabilite.tracabilite.web.web.impl;

import com.tracabilite.tracabilite.persistence.enumeration.BandeCommandeStatus;
import com.tracabilite.tracabilite.persistence.enumeration.BandeLivraisonStatus;
import com.tracabilite.tracabilite.persistence.model.Article;
import com.tracabilite.tracabilite.persistence.model.BandeCommande;
import com.tracabilite.tracabilite.persistence.model.BandeLivraison;
import com.tracabilite.tracabilite.persistence.model.Matiere;
import com.tracabilite.tracabilite.service.IArticleService;
import com.tracabilite.tracabilite.service.IBandeCommandeService;
import com.tracabilite.tracabilite.service.IBandeLivraisonService;
import com.tracabilite.tracabilite.service.IMatiereService;
import com.tracabilite.tracabilite.web.common.ApiMessage;
import com.tracabilite.tracabilite.web.common.utils.HttpErrorResponse;
import com.tracabilite.tracabilite.web.dto.BandeLivraisonCreateDto;
import com.tracabilite.tracabilite.web.dto.ChangeStatuslivraisonDto;
import com.tracabilite.tracabilite.web.mapper.ArticleMapper;
import com.tracabilite.tracabilite.web.mapper.BandeLivraisonMapper;
import com.tracabilite.tracabilite.web.web.api.IBandeLivraisonApi;
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

import java.util.*;
import java.util.stream.Collectors;

import static com.tracabilite.tracabilite.web.common.ApiMessage.NOT_FOUND;
import static com.tracabilite.tracabilite.web.common.ApiMessage.SERVER_ERROR_OCCURRED;

@Slf4j
@RestController
@CrossOrigin("*")
public class BandeLivraisonApi implements IBandeLivraisonApi {
    private Object httpResponseBody;

    private HttpStatus httpStatus;

    @Autowired
    private IBandeLivraisonService bandeLivraisonService;

    @Autowired
    private IBandeCommandeService bandeCommandeService;
    @Autowired
    private IArticleService articleService;
    @Autowired
    private IMatiereService matiereService;

    @Override
    public ResponseEntity<?> getBandeLivraisonByBandeCommande(@PathVariable("bandeCommandeId") Long bandeCommandeId) {
        log.info("Web service getArticles is invoked");
        try {
            List<BandeLivraison> bandeLivraisons = bandeLivraisonService.findAll();
            if(bandeLivraisons.isEmpty()){
                httpResponseBody = bandeLivraisons;
            }else {
                httpResponseBody = bandeLivraisons.stream().filter(bandeLivraison -> bandeLivraison.getBandeCommande().getId().equals(bandeCommandeId)).map(BandeLivraisonMapper.MAPPER::toDto).collect(Collectors.toList());
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
    public ResponseEntity<?> getAllBandeLivraison() {
        log.info("Web service getArticles is invoked");
        try {
            List<BandeLivraison> bandeLivraisons = bandeLivraisonService.findAll();
            if(bandeLivraisons.isEmpty()){
                httpResponseBody = bandeLivraisons;
            }else {
                httpResponseBody = bandeLivraisons.stream().map(BandeLivraisonMapper.MAPPER::toDto).collect(Collectors.toList());
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
    public ResponseEntity<?> getBandeLivraisonById(@PathVariable("bandeLivraison") Long bandeLivraison) {
        log.info("Web service getBandeLivraisonById is invoked");
        try {
            Optional<BandeLivraison> bandeLivraisons = bandeLivraisonService.findById(bandeLivraison);
            if (bandeLivraisons.isPresent()) {
                httpStatus = HttpStatus.OK;
                httpResponseBody = BandeLivraisonMapper.MAPPER.toDto(bandeLivraisons.get());
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
    public ResponseEntity<?> getStatisticBandeLivraison() {
        List<BandeLivraison> bandeLivraisons = bandeLivraisonService.findAll();

        // Utiliser Java Streams pour compter les occurrences de chaque statut
        Map<BandeLivraisonStatus, Long> statusCount = new HashMap<>();
        statusCount.put(BandeLivraisonStatus.NEW, 0L);
        statusCount.put(BandeLivraisonStatus.PROCESS, 0L);
        statusCount.put(BandeLivraisonStatus.TERMINETED, 0L);

        // Compter les occurrences réelles et fusionner avec les valeurs par défaut
        Map<BandeLivraisonStatus, Long> actualCounts = bandeLivraisons.stream()
                .collect(Collectors.groupingBy(BandeLivraison::getBandeLivraisonStatus, Collectors.counting()));

        // Fusionner les résultats pour s'assurer que chaque statut a au moins une valeur
        actualCounts.forEach(statusCount::put);
        httpStatus = HttpStatus.OK;
        httpResponseBody=statusCount;
        return new ResponseEntity<>(httpResponseBody, httpStatus);
    }

    @Override
    public ResponseEntity<?> saveBandeLivraison(@ApiParam(required = true, value = "article", name = "article")
                                                    @RequestBody List<BandeLivraisonCreateDto> bandeLivraisonCreateDto, BindingResult bindingResult) {
        log.info("Web service saveBandeLivraison is invoked with args bandeLivraisonCreateDto {}", bandeLivraisonCreateDto);
        if (!bindingResult.hasFieldErrors()) {
            try {
                List<BandeLivraison> bandeLivraisons = new ArrayList<>();
                bandeLivraisonCreateDto.forEach(bandeLivraisonCreateDto1 -> {
                    BandeLivraison bandeLivraison = new BandeLivraison();
                    bandeLivraison.setBandeCommande(bandeCommandeService.findById(bandeLivraisonCreateDto1.getBandeCommandeId()).get());
                    bandeLivraison.setArticle(articleService.findById(bandeLivraisonCreateDto1.getArticleId()).get());
                    bandeLivraison.setQuantite(bandeLivraisonCreateDto1.getQuantite());
                    bandeLivraison.setBandeLivraisonStatus(BandeLivraisonStatus.NEW);
                    bandeLivraisons.add(bandeLivraison);
                });
                  List<BandeLivraison> newBandeLivraisons = bandeLivraisonService.saveAll(bandeLivraisons);
                  httpStatus = HttpStatus.OK;
                  httpResponseBody = newBandeLivraisons.stream().map(BandeLivraisonMapper.MAPPER::toDto).collect(Collectors.toList());
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
    public ResponseEntity<?> editBandeLivraison(@PathVariable("bandeLivraisonId") Long bandeLivraisonId,@RequestBody ChangeStatuslivraisonDto changeStatuslivraisonDto, BindingResult bindingResult) {
        Optional<BandeLivraison> article = bandeLivraisonService.findById(bandeLivraisonId);
        if (article.isPresent()) {
            if(changeStatuslivraisonDto.getBandeLivraisonStatus().equals("PROCESS")) {
                if ((article.get().getArticle().getTonnage() * article.get().getQuantite()) <= article.get().getArticle().getMatiere().getTonnage()) {
                    Optional<Matiere> matiere = matiereService.findById(article.get().getArticle().getMatiere().getId());
                    matiere.get().setTonnage(matiere.get().getTonnage() - (article.get().getArticle().getTonnage() * article.get().getQuantite()));
                    matiereService.update(matiere.get());
                    article.get().setBandeLivraisonStatus(BandeLivraisonStatus.valueOf(changeStatuslivraisonDto.getBandeLivraisonStatus()));
                    bandeLivraisonService.update(article.get());

                    List<BandeLivraison> bandeLivraisons = bandeLivraisonService.findAll().stream().filter(bandeLivraison -> bandeLivraison.getBandeCommande().getId().equals(article.get().getBandeCommande().getId())).collect(Collectors.toList());
                    boolean tousTerminer = bandeLivraisons.stream()
                            .allMatch(bandeLivraison -> bandeLivraison.getBandeLivraisonStatus().equals(BandeLivraisonStatus.TERMINETED));
                    boolean unEnCours = bandeLivraisons.stream()
                            .anyMatch(bandeLivraison -> bandeLivraison.getBandeLivraisonStatus().equals(BandeLivraisonStatus.PROCESS));
                    Optional<BandeCommande> bandeCommande = bandeCommandeService.findById(article.get().getBandeCommande().getId());

                    if (tousTerminer) {
                        bandeCommande.get().setBandeCommandeStatus(BandeCommandeStatus.TERMINETED);
                        bandeCommandeService.update(bandeCommande.get());
                    }
                    if (unEnCours) {
                        bandeCommande.get().setBandeCommandeStatus(BandeCommandeStatus.PROCESS);
                        bandeCommandeService.update(bandeCommande.get());
                    }

                    httpStatus = HttpStatus.OK;
                    httpResponseBody = BandeLivraisonMapper.MAPPER.toDto(article.get());
                } else {
                    httpStatus = HttpStatus.BAD_REQUEST;
                    httpResponseBody = new HttpErrorResponse(400, "PAS_DE_STOCK");
                }
            }else {
                article.get().setBandeLivraisonStatus(BandeLivraisonStatus.valueOf(changeStatuslivraisonDto.getBandeLivraisonStatus()));
                bandeLivraisonService.update(article.get());

                List<BandeLivraison> bandeLivraisons = bandeLivraisonService.findAll().stream().filter(bandeLivraison -> bandeLivraison.getBandeCommande().getId().equals(article.get().getBandeCommande().getId())).collect(Collectors.toList());
                boolean tousTerminer = bandeLivraisons.stream()
                        .allMatch(bandeLivraison -> bandeLivraison.getBandeLivraisonStatus().equals(BandeLivraisonStatus.TERMINETED));
                boolean unEnCours = bandeLivraisons.stream()
                        .anyMatch(bandeLivraison -> bandeLivraison.getBandeLivraisonStatus().equals(BandeLivraisonStatus.PROCESS));
                Optional<BandeCommande> bandeCommande = bandeCommandeService.findById(article.get().getBandeCommande().getId());

                if (tousTerminer) {
                    bandeCommande.get().setBandeCommandeStatus(BandeCommandeStatus.TERMINETED);
                    bandeCommandeService.update(bandeCommande.get());
                }
                if (unEnCours) {
                    bandeCommande.get().setBandeCommandeStatus(BandeCommandeStatus.PROCESS);
                    bandeCommandeService.update(bandeCommande.get());
                }
            }
        }else {
            httpStatus = HttpStatus.NOT_FOUND;
            httpResponseBody = new HttpErrorResponse(404, NOT_FOUND);
        }
        return new ResponseEntity<>(httpResponseBody, httpStatus);
    }

    @Override
    public ResponseEntity<?> deleteBandeLivraison(Long bandeLivraisonId) {
        return null;
    }
}
