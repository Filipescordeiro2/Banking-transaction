package com.Banking.transation.utils.mappers;

import com.Banking.transation.domain.Cliente;
import com.Banking.transation.dto.request.ClienteRequest;
import com.Banking.transation.dto.response.ClienteResponse;
import org.springframework.stereotype.Component;

@Component
public class ClienteMapper {

    public Cliente fromDTO(ClienteRequest request) {
        if (request == null) {
            return null;
        }
        return Cliente.builder()
                .id(request.getId())
                .nome(request.getNome())
                .sobrenome(request.getSobrenome())
                .telefone(request.getTelefone())
                .email(request.getEmail())
                .cpf(request.getCpf())
                .rg(request.getRg())
                .orgaoEmissor(request.getOrgaoEmissor())
                .dataEmissaoRG(request.getDataEmissaoRG())
                .dataNascimento(request.getDataNascimento())
                .idade(request.getIdade())
                .dataCriacaoUsuario(request.getDataCriacaoUsuario())
                .pessoalPoliticamenteExposta(request.isPessoalPoliticamenteExposta())
                .status(request.isStatus())
                .categoriaCliente(request.getCategoriaCliente())
                .score(request.getScore())
                .cartoes(request.getCartoes())
                .conta(request.getConta())
                .desejaCredito(request.isDesejaCredito())
                .build();
    }

    public ClienteResponse toDTO(Cliente cliente) {
        if (cliente == null) {
            return null;
        }
        return new ClienteResponse(
                cliente.getId(),
                cliente.getNome(),
                cliente.getSobrenome(),
                cliente.getTelefone(),
                cliente.getEmail(),
                cliente.getCpf(),
                cliente.getRg(),
                cliente.getOrgaoEmissor(),
                cliente.getDataEmissaoRG(),
                cliente.getDataNascimento(),
                cliente.getIdade(),
                cliente.getDataCriacaoUsuario(),
                cliente.isPessoalPoliticamenteExposta(),
                cliente.isStatus(),
                cliente.getCategoriaCliente(),
                cliente.getScore(),
                cliente.isDesejaCredito()
        );
    }
}