package com.Banking.transation.controller;

import com.Banking.transation.dto.request.ContaRequest;
import com.Banking.transation.dto.response.ContaResponse;
import com.Banking.transation.service.ContaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/contas")
public class ContaController {

    @Autowired
    private ContaService contaService;

    @PostMapping
    public ResponseEntity<ContaResponse> criarConta(@RequestBody ContaRequest contaRequest) {
        ContaResponse contaResponse = contaService.criarConta(contaRequest);
        return ResponseEntity.ok(contaResponse);
    }

}