package com.tracabilite.tracabilite.web.web.impl;

import com.tracabilite.tracabilite.persistence.model.Article;
import com.tracabilite.tracabilite.persistence.model.Client;
import com.tracabilite.tracabilite.persistence.model.Machine;
import com.tracabilite.tracabilite.service.IArticleService;
import com.tracabilite.tracabilite.service.IClientService;
import com.tracabilite.tracabilite.web.common.ApiMessage;
import com.tracabilite.tracabilite.web.common.utils.HttpErrorResponse;
import com.tracabilite.tracabilite.web.common.utils.HttpMessageResponse;
import com.tracabilite.tracabilite.web.dto.ArticleCreateDto;
import com.tracabilite.tracabilite.web.dto.ClientCreateDto;
import com.tracabilite.tracabilite.web.mapper.ArticleMapper;
import com.tracabilite.tracabilite.web.mapper.ClientMapper;
import com.tracabilite.tracabilite.web.mapper.MachineMapper;
import com.tracabilite.tracabilite.web.web.api.IClientApi;
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
public class ClientApi implements IClientApi {
    private Object httpResponseBody;

    private HttpStatus httpStatus;
    @Autowired
    private IClientService clientService;
    @Override
    public ResponseEntity<?> getClients() {
        log.info("Web service getArticles is invoked");
        try {
            List<Client> clients = clientService.findAll();
            if(clients.isEmpty()){
                httpResponseBody = clients;;
            }else {
                httpResponseBody = clients.stream().map(ClientMapper.MAPPER::toDto).collect(Collectors.toList());
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
    public ResponseEntity<?> getClientById(@PathVariable("clientId") Long clientId) {
        log.info("Web service getProspectById is invoked");
        try {
            Optional<Client> client = clientService.findById(clientId);
            if (client.isPresent()) {
                httpStatus = HttpStatus.OK;
                httpResponseBody = ClientMapper.MAPPER.toDto(client.get());
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
    public ResponseEntity<?> saveClient(@ApiParam(required = true, value = "client", name = "client")
                                            @RequestBody ClientCreateDto clientCreateDto, BindingResult bindingResult) {
        log.info("Web service saveClient is invoked with args clientCreateDto {}", clientCreateDto);
        if (!bindingResult.hasFieldErrors()) {
            try {
                Optional<Client> client = clientService.findByEmail(clientCreateDto.getEmail());
                if (!client.isEmpty()) {
                    httpStatus = HttpStatus.BAD_REQUEST;
                    httpResponseBody = new HttpErrorResponse(400, ApiMessage.CLIENT_ALREADY_EXIST);
                } else {
                    Client newClient = ClientMapper.MAPPER.toClient(clientCreateDto);
                    Client clientToBeSave =clientService.save(newClient);
                    httpResponseBody = ClientMapper.MAPPER.toDto(clientToBeSave);
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
    public ResponseEntity<?> editClient(@PathVariable("clientId") Long clientId, @RequestBody  ClientCreateDto clientCreateDto, BindingResult bindingResult) {
        log.info("Web service editClient invoked with id {} and clientCreateDto {}", clientId, clientCreateDto);
        if (!bindingResult.hasFieldErrors()) {
            try {
                Optional<Client> client = clientService.findById(clientId);
                if (client.isPresent()) {

                        updateExistantClient(clientCreateDto, client.get());

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
    public ResponseEntity<?> deleteClient(@ApiParam(value = "ID of client that will be deleted", required = true, allowableValues = "range[1,infinity]") @PathVariable("clientId") Long clientId) {
        log.info("Web service deleteMachine invoked with id {}", clientId);
        try {
            Optional<Client> client = clientService.findById(clientId);
            if (client.isPresent()) {
                clientService.delete(client.get());
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

    private void updateExistantClient(ClientCreateDto clientCreateDto, Client client) {
        client.setNom(clientCreateDto.getNom());
        client.setPrenom(clientCreateDto.getPrenom());
        client.setEmail(clientCreateDto.getEmail());
        client.setAdresse(clientCreateDto.getAdresse());
        client.setNumero(clientCreateDto.getNumero());
        clientService.update(client);
        httpResponseBody = ClientMapper.MAPPER.toDto(client);
        httpStatus = HttpStatus.OK;
    }

}
