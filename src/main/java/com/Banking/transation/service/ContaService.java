package com.Banking.transation.service;

import com.Banking.transation.domain.Cliente;
import com.Banking.transation.domain.Conta;
import com.Banking.transation.dto.request.ContaRequest;
import com.Banking.transation.dto.response.ContaResponse;
import com.Banking.transation.exception.ErroGenericoException;
import com.Banking.transation.repository.ClienteRepository;
import com.Banking.transation.repository.ContaRepository;
import com.Banking.transation.utils.mappers.ContaMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.UUID;

@Service
public class ContaService {

    @Autowired
    private ContaRepository contaRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ContaMapper contaMapper;

    public ContaResponse criarConta(ContaRequest contaRequest) {
        try {
            UUID clienteId = contaRequest.getClienteId();
            Cliente cliente = clienteRepository.findById(clienteId)
                    .orElseThrow(() -> new ErroGenericoException("Cliente não encontrado"));

            Conta conta = contaMapper.fromDTO(contaRequest);
            conta.setCliente(cliente); // Set the cliente in the Conta entity
            conta.setNumeroConta(gerarNumeroConta());
            conta.setAgencia("001");

            Conta contaSalva = contaRepository.save(conta);
            return contaMapper.toDTO(contaSalva);
        } catch (Exception e) {
            throw new ErroGenericoException("Erro ao criar conta: " + e.getMessage());
        }
    }

    private String gerarNumeroConta() {
        SecureRandom random = new SecureRandom();
        StringBuilder numeroConta = new StringBuilder();

        for (int i = 0; i < 8; i++) {
            numeroConta.append(random.nextInt(10));
        }

        int digitoVerificador = 1 + random.nextInt(9);
        numeroConta.append("-").append(digitoVerificador);

        return numeroConta.toString();
    }
}