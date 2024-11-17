package com.tracabilite.tracabilite.web.web.api;

import com.tracabilite.tracabilite.web.common.ApiMessage;
import com.tracabilite.tracabilite.web.common.ApiStatus;
import com.tracabilite.tracabilite.web.common.utils.HttpErrorResponse;
import com.tracabilite.tracabilite.web.dto.BandeCommandeCreateDto;
import com.tracabilite.tracabilite.web.dto.BandeCommandeDto;
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

@RequestMapping(value = "/api/bandeCommande")
public interface IBandeCommandeApi {
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "API_GET_BANDE_COMMANDE_SUCCESS",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BandeCommandeDto.class)) }),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content) })
    @ApiOperation(value = "get all bande commandes", response = List.class )
    @GetMapping(value = "/bandeCommandes")
    ResponseEntity<?> getBandeCommandes();

    @io.swagger.annotations.ApiResponses(value = {@io.swagger.annotations.ApiResponse(code = ApiStatus.STATUS_OK, message = ApiMessage.SUCCESSFUL_OPERATION, response = BandeCommandeDto.class),
            @io.swagger.annotations.ApiResponse(code = ApiStatus.STATUS_BAD_REQUEST, message = ApiMessage.NO_DATA, response = HttpErrorResponse.class)})
    @ApiOperation(value = "find bande commande by id", response = BandeCommandeDto.class, authorizations = {
            @Authorization(value = ApiMessage.OAUTH2SCHEMA, scopes = {@AuthorizationScope(scope = "read", description = "")})})
    @GetMapping(value = "{bandeCommandeId}")
    ResponseEntity<?> getBandeCommandeById(Long bandeCommandeId);


    @io.swagger.annotations.ApiResponses(value = {@io.swagger.annotations.ApiResponse(code = ApiStatus.STATUS_ACCEPTED, message = ApiMessage.BANDE_COMMANDE_CREATED_SUCCESSFULLY, response = BandeCommandeDto.class),
            @io.swagger.annotations.ApiResponse(code = ApiStatus.STATUS_BAD_REQUEST, message = ApiMessage.INVALID_INPUT, response = HttpErrorResponse.class)})
    @ApiOperation(value = "Add a new bande commande", response = BandeCommandeDto.class, authorizations = {
            @Authorization(value = ApiMessage.OAUTH2SCHEMA, scopes = {@AuthorizationScope(scope = "read", description = "")})})
    @PostMapping(value = "")
    ResponseEntity<?> saveBandeCommande(BandeCommandeCreateDto bandeCommandeCreateDto, BindingResult bindingResult);

    @io.swagger.annotations.ApiResponses(value = {@io.swagger.annotations.ApiResponse(code = ApiStatus.STATUS_ACCEPTED, message = ApiMessage.BANDE_COMMANDE_UPDATED_SUCCESSFULLY, response = BandeCommandeDto.class),
            @io.swagger.annotations.ApiResponse(code = ApiStatus.STATUS_BAD_REQUEST, message = ApiMessage.INVALID_INPUT, response = HttpErrorResponse.class)})
    @ApiOperation(value = "edit an existant bande commande", response = BandeCommandeDto.class, authorizations = {
            @Authorization(value = ApiMessage.OAUTH2SCHEMA, scopes = {@AuthorizationScope(scope = "read", description = "")})})
    @PutMapping(value = "/{bandeCommandeId}")
    ResponseEntity<?> editBandeCommande(Long bandeCommandeId, BandeCommandeCreateDto bandeCommandeCreateDto, BindingResult bindingResult);

    @io.swagger.annotations.ApiResponses(value = {@io.swagger.annotations.ApiResponse(code = ApiStatus.STATUS_ACCEPTED, message = ApiMessage.BANDE_COMMANDE_DELETED_SUCCESSFULLY),
            @io.swagger.annotations.ApiResponse(code = ApiStatus.STATUS_BAD_REQUEST, message = ApiMessage.INVALID_INPUT, response = HttpErrorResponse.class)})
    @ApiOperation(value = "delete an existing bandeCommande", authorizations = {
            @Authorization(value = ApiMessage.OAUTH2SCHEMA, scopes = {@AuthorizationScope(scope = "read", description = "")})})
    @DeleteMapping(value = "/{bandeCommandeId}")
    ResponseEntity<?> deleteBandeCommande(Long bandeCommandeId);
}
