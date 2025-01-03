package com.Banking.transation.controller;

import com.Banking.transation.dto.request.CartaoRequest;
import com.Banking.transation.dto.response.CartaoResponse;
import com.Banking.transation.service.CartaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@RestController
@RequestMapping("/banking/cartao")
public class CartaoController {

    @Autowired
    private CartaoService cartaoService;

    @PostMapping("/criar/{clienteId}/{contaId}")
    @ResponseStatus(HttpStatus.CREATED)
    public CartaoResponse criarCartao(@PathVariable("clienteId") UUID clienteId, @PathVariable("contaId") UUID contaId, @RequestBody CartaoRequest cartaoRequest) {
        try {
            return cartaoService.criarCartao(clienteId, contaId, cartaoRequest);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cliente não encontrado", e);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao criar cartão", e);
        }
    }
}