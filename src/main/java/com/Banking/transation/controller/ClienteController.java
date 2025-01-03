package com.Banking.transation.controller;

import com.Banking.transation.dto.request.ClienteRequest;
import com.Banking.transation.dto.response.ClienteResponse;
import com.Banking.transation.service.ClienteService;
import com.Banking.transation.utils.mappers.ClienteMapper;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/banking/cliente")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private ClienteMapper clienteMapper;

    @PostMapping("/cadastro")
    @ResponseStatus(HttpStatus.CREATED)
    public ClienteResponse criarCliente(@Valid @RequestBody ClienteRequest clienteRequest) {
        return clienteService.criarCliente(clienteRequest);
    }

}