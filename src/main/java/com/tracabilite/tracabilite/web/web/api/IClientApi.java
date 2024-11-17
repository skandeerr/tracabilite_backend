package com.tracabilite.tracabilite.web.web.api;

import com.tracabilite.tracabilite.web.common.ApiMessage;
import com.tracabilite.tracabilite.web.common.ApiStatus;
import com.tracabilite.tracabilite.web.common.utils.HttpErrorResponse;
import com.tracabilite.tracabilite.web.dto.ClientCreateDto;
import com.tracabilite.tracabilite.web.dto.ClientDto;
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

@RequestMapping(value = "/api/client")
public interface IClientApi {
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "API_GET_CLIENTS_SUCCESS",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ClientDto.class)) }),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content) })
    @ApiOperation(value = "get all clients", response = List.class )
    @GetMapping(value = "/clients")
    ResponseEntity<?> getClients();

    @io.swagger.annotations.ApiResponses(value = {@io.swagger.annotations.ApiResponse(code = ApiStatus.STATUS_OK, message = ApiMessage.SUCCESSFUL_OPERATION, response = ClientDto.class),
            @io.swagger.annotations.ApiResponse(code = ApiStatus.STATUS_BAD_REQUEST, message = ApiMessage.NO_DATA, response = HttpErrorResponse.class)})
    @ApiOperation(value = "find contact by id", response = ClientDto.class, authorizations = {
            @Authorization(value = ApiMessage.OAUTH2SCHEMA, scopes = {@AuthorizationScope(scope = "read", description = "")})})
    @GetMapping(value = "{clientId}")
    ResponseEntity<?> getClientById(Long clientId);


    @io.swagger.annotations.ApiResponses(value = {@io.swagger.annotations.ApiResponse(code = ApiStatus.STATUS_ACCEPTED, message = ApiMessage.CLIENT_CREATED_SUCCESSFULLY, response = ClientDto.class),
            @io.swagger.annotations.ApiResponse(code = ApiStatus.STATUS_BAD_REQUEST, message = ApiMessage.INVALID_INPUT, response = HttpErrorResponse.class)})
    @ApiOperation(value = "Add a new contact", response = ClientDto.class, authorizations = {
            @Authorization(value = ApiMessage.OAUTH2SCHEMA, scopes = {@AuthorizationScope(scope = "read", description = "")})})
    @PostMapping(value = "")
    ResponseEntity<?> saveClient(ClientCreateDto clientCreateDto, BindingResult bindingResult);

    @io.swagger.annotations.ApiResponses(value = {@io.swagger.annotations.ApiResponse(code = ApiStatus.STATUS_ACCEPTED, message = ApiMessage.CLIENT_UPDATED_SUCCESSFULLY, response = ClientDto.class),
            @io.swagger.annotations.ApiResponse(code = ApiStatus.STATUS_BAD_REQUEST, message = ApiMessage.INVALID_INPUT, response = HttpErrorResponse.class)})
    @ApiOperation(value = "edit an existant contact", response = ClientDto.class, authorizations = {
            @Authorization(value = ApiMessage.OAUTH2SCHEMA, scopes = {@AuthorizationScope(scope = "read", description = "")})})
    @PutMapping(value = "/{clientId}")
    ResponseEntity<?> editClient(Long clientId, ClientCreateDto clientCreateDto, BindingResult bindingResult);

    @io.swagger.annotations.ApiResponses(value = {@io.swagger.annotations.ApiResponse(code = ApiStatus.STATUS_ACCEPTED, message = ApiMessage.CLIENT_DELETED_SUCCESSFULLY),
            @io.swagger.annotations.ApiResponse(code = ApiStatus.STATUS_BAD_REQUEST, message = ApiMessage.INVALID_INPUT, response = HttpErrorResponse.class)})
    @ApiOperation(value = "delete an existing contact", authorizations = {
            @Authorization(value = ApiMessage.OAUTH2SCHEMA, scopes = {@AuthorizationScope(scope = "read", description = "")})})
    @DeleteMapping(value = "/{clientId}")
    ResponseEntity<?> deleteClient(Long clientId);
}
