package com.tracabilite.tracabilite.web.web.api;

import com.tracabilite.tracabilite.web.common.ApiMessage;
import com.tracabilite.tracabilite.web.common.ApiStatus;
import com.tracabilite.tracabilite.web.common.utils.HttpErrorResponse;
import com.tracabilite.tracabilite.web.dto.BandeLivraisonCreateDto;
import com.tracabilite.tracabilite.web.dto.BandeLivraisonDto;
import com.tracabilite.tracabilite.web.dto.ChangeStatuslivraisonDto;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import io.swagger.annotations.AuthorizationScope;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping(value = "/api/BandeLivraison")
public interface IBandeLivraisonApi {
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "API_GET_BANDE_LIVRAISON_SUCCESS",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BandeLivraisonDto.class)) }),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content) })
    @ApiOperation(value = "get all bande livraisons", response = List.class )
    @GetMapping(value = "/bandeLivraisons/{bandeCommandeId}")
    ResponseEntity<?> getBandeLivraisonByBandeCommande(Long bandeCommandeId);


    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "API_GET_BANDE_LIVRAISON_SUCCESS",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BandeLivraisonDto.class)) }),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content) })
    @ApiOperation(value = "get all bande livraisons", response = List.class )
    @GetMapping(value = "/bandeLivraisons")
    ResponseEntity<?> getAllBandeLivraison();
    @io.swagger.annotations.ApiResponses(value = {@io.swagger.annotations.ApiResponse(code = ApiStatus.STATUS_OK, message = ApiMessage.SUCCESSFUL_OPERATION, response = BandeLivraisonDto.class),
            @io.swagger.annotations.ApiResponse(code = ApiStatus.STATUS_BAD_REQUEST, message = ApiMessage.NO_DATA, response = HttpErrorResponse.class)})
    @ApiOperation(value = "find bande livraison by id", response = BandeLivraisonDto.class, authorizations = {
            @Authorization(value = ApiMessage.OAUTH2SCHEMA, scopes = {@AuthorizationScope(scope = "read", description = "")})})
    @GetMapping(value = "{bandeLivraison}")
    ResponseEntity<?> getBandeLivraisonById(Long bandeLivraison);

    @io.swagger.annotations.ApiResponses(value = {@io.swagger.annotations.ApiResponse(code = ApiStatus.STATUS_OK, message = ApiMessage.SUCCESSFUL_OPERATION, response = BandeLivraisonDto.class),
            @io.swagger.annotations.ApiResponse(code = ApiStatus.STATUS_BAD_REQUEST, message = ApiMessage.NO_DATA, response = HttpErrorResponse.class)})
    @ApiOperation(value = "find bande livraison by id", response = BandeLivraisonDto.class, authorizations = {
            @Authorization(value = ApiMessage.OAUTH2SCHEMA, scopes = {@AuthorizationScope(scope = "read", description = "")})})
    @GetMapping(value = "/statistic")
    ResponseEntity<?> getStatisticBandeLivraison();

    @io.swagger.annotations.ApiResponses(value = {@io.swagger.annotations.ApiResponse(code = ApiStatus.STATUS_ACCEPTED, message = ApiMessage.BANDE_LIVRAISON_CREATED_SUCCESSFULLY, response = BandeLivraisonDto.class),
            @io.swagger.annotations.ApiResponse(code = ApiStatus.STATUS_BAD_REQUEST, message = ApiMessage.INVALID_INPUT, response = HttpErrorResponse.class)})
    @ApiOperation(value = "Add a new bande livraison", response = BandeLivraisonDto.class, authorizations = {
            @Authorization(value = ApiMessage.OAUTH2SCHEMA, scopes = {@AuthorizationScope(scope = "read", description = "")})})
    @PostMapping(value = "")
    ResponseEntity<?> saveBandeLivraison(List<BandeLivraisonCreateDto> bandeLivraisonCreateDto, BindingResult bindingResult);

    @io.swagger.annotations.ApiResponses(value = {@io.swagger.annotations.ApiResponse(code = ApiStatus.STATUS_ACCEPTED, message = ApiMessage.BANDE_LIVRAISON_UPDATED_SUCCESSFULLY, response = BandeLivraisonDto.class),
            @io.swagger.annotations.ApiResponse(code = ApiStatus.STATUS_BAD_REQUEST, message = ApiMessage.INVALID_INPUT, response = HttpErrorResponse.class)})
    @ApiOperation(value = "edit an existant bande livraison", response = BandeLivraisonDto.class, authorizations = {
            @Authorization(value = ApiMessage.OAUTH2SCHEMA, scopes = {@AuthorizationScope(scope = "read", description = "")})})
    @PutMapping(value = "/{bandeLivraisonId}")
    ResponseEntity<?> editBandeLivraison(Long bandeLivraisonId, ChangeStatuslivraisonDto changeStatuslivraisonDto, BindingResult bindingResult);

    @io.swagger.annotations.ApiResponses(value = {@io.swagger.annotations.ApiResponse(code = ApiStatus.STATUS_ACCEPTED, message = ApiMessage.BANDE_LIVRAISON_DELETED_SUCCESSFULLY),
            @io.swagger.annotations.ApiResponse(code = ApiStatus.STATUS_BAD_REQUEST, message = ApiMessage.INVALID_INPUT, response = HttpErrorResponse.class)})
    @ApiOperation(value = "delete an existing bande livraison", authorizations = {
            @Authorization(value = ApiMessage.OAUTH2SCHEMA, scopes = {@AuthorizationScope(scope = "read", description = "")})})
    @DeleteMapping(value = "/{bandeLivraisonId}")
    ResponseEntity<?> deleteBandeLivraison(Long bandeLivraisonId);
}
