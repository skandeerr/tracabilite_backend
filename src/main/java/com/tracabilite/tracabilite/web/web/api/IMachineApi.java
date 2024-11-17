package com.tracabilite.tracabilite.web.web.api;

import com.tracabilite.tracabilite.web.common.ApiMessage;
import com.tracabilite.tracabilite.web.common.ApiStatus;
import com.tracabilite.tracabilite.web.common.utils.HttpErrorResponse;
import com.tracabilite.tracabilite.web.dto.MachineCreateDto;
import com.tracabilite.tracabilite.web.dto.MachineDto;
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

@RequestMapping(value = "/api/machine")
public interface IMachineApi {
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "API_GET_CLIENTS_SUCCESS",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = MachineDto.class)) }),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content) })
    @ApiOperation(value = "get all clients", response = List.class )
    @GetMapping(value = "/machine")
    ResponseEntity<?> getMachines();

    @io.swagger.annotations.ApiResponses(value = {@io.swagger.annotations.ApiResponse(code = ApiStatus.STATUS_OK, message = ApiMessage.SUCCESSFUL_OPERATION, response = MachineDto.class),
            @io.swagger.annotations.ApiResponse(code = ApiStatus.STATUS_BAD_REQUEST, message = ApiMessage.NO_DATA, response = HttpErrorResponse.class)})
    @ApiOperation(value = "find machine by id", response = MachineDto.class, authorizations = {
            @Authorization(value = ApiMessage.OAUTH2SCHEMA, scopes = {@AuthorizationScope(scope = "read", description = "")})})
    @GetMapping(value = "{machineId}")
    ResponseEntity<?> getMachineById(Long machineId);


    @io.swagger.annotations.ApiResponses(value = {@io.swagger.annotations.ApiResponse(code = ApiStatus.STATUS_ACCEPTED, message = ApiMessage.MACHINE_CREATED_SUCCESSFULLY, response = MachineDto.class),
            @io.swagger.annotations.ApiResponse(code = ApiStatus.STATUS_BAD_REQUEST, message = ApiMessage.INVALID_INPUT, response = HttpErrorResponse.class)})
    @ApiOperation(value = "Add a new machine", response = MachineDto.class, authorizations = {
            @Authorization(value = ApiMessage.OAUTH2SCHEMA, scopes = {@AuthorizationScope(scope = "read", description = "")})})
    @PostMapping(value = "")
    ResponseEntity<?> saveMachine(MachineCreateDto machineCreateDto, BindingResult bindingResult);

    @io.swagger.annotations.ApiResponses(value = {@io.swagger.annotations.ApiResponse(code = ApiStatus.STATUS_ACCEPTED, message = ApiMessage.MACHINE_UPDATED_SUCCESSFULLY, response = MachineDto.class),
            @io.swagger.annotations.ApiResponse(code = ApiStatus.STATUS_BAD_REQUEST, message = ApiMessage.INVALID_INPUT, response = HttpErrorResponse.class)})
    @ApiOperation(value = "edit an existant machine", response = MachineDto.class, authorizations = {
            @Authorization(value = ApiMessage.OAUTH2SCHEMA, scopes = {@AuthorizationScope(scope = "read", description = "")})})
    @PutMapping(value = "/{machineId}")
    ResponseEntity<?> editMachine(Long machineId, MachineCreateDto machineCreateDto, BindingResult bindingResult);

    @io.swagger.annotations.ApiResponses(value = {@io.swagger.annotations.ApiResponse(code = ApiStatus.STATUS_ACCEPTED, message = ApiMessage.MACHINE_DELETED_SUCCESSFULLY),
            @io.swagger.annotations.ApiResponse(code = ApiStatus.STATUS_BAD_REQUEST, message = ApiMessage.INVALID_INPUT, response = HttpErrorResponse.class)})
    @ApiOperation(value = "delete an existing machine", authorizations = {
            @Authorization(value = ApiMessage.OAUTH2SCHEMA, scopes = {@AuthorizationScope(scope = "read", description = "")})})
    @DeleteMapping(value = "/{machineId}")
    ResponseEntity<?> deleteMachine(Long machineId);
}
