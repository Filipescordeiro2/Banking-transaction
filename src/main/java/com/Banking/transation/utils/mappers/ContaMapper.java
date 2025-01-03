package com.Banking.transation.utils.mappers;

import com.Banking.transation.domain.Conta;
import com.Banking.transation.domain.Cliente;
import com.Banking.transation.dto.request.ContaRequest;
import com.Banking.transation.dto.response.ContaResponse;
import com.Banking.transation.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ContaMapper {

    @Autowired
    private ClienteRepository clienteRepository;

    public Conta fromDTO(ContaRequest request) {
        if (request == null) {
            return null;
        }
        Conta conta = new Conta();
        Cliente cliente = clienteRepository.findById(request.getClienteId()).orElse(null);
        conta.setCliente(cliente);
        return conta;
    }

    public ContaResponse toDTO(Conta conta) {
        if (conta == null) {
            return null;
        }
        return new ContaResponse(
            conta.getId(),
            conta.getNumeroConta(),
            conta.getAgencia()
        );
    }
}