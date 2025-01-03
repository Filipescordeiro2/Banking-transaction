package com.Banking.transation.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.security.SecureRandom;

import com.Banking.transation.domain.Cliente;
import com.Banking.transation.dto.request.ClienteRequest;
import com.Banking.transation.dto.response.ClienteResponse;
import com.Banking.transation.exception.CampoObrigatorioException;
import com.Banking.transation.exception.ErroGenericoException;
import com.Banking.transation.repository.ClienteRepository;
import com.Banking.transation.utils.mappers.ClienteMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ClienteMapper clienteMapper;

    public ClienteResponse criarCliente(ClienteRequest clienteRequest) {
        try {
            Cliente cliente = clienteMapper.fromDTO(clienteRequest);
            validarCamposObrigatorios(cliente);

            cliente.setDataCriacaoUsuario(LocalDateTime.now());
            cliente.setStatus(true);
            cliente.setScore(geraScore());

            Cliente clienteSalvo = clienteRepository.save(cliente);

            calcularIdade(clienteSalvo);

            return clienteMapper.toDTO(clienteSalvo);
        } catch (CampoObrigatorioException e) {
            throw e;
        } catch (Exception e) {
            throw new ErroGenericoException("Erro ao criar cliente: " + e.getMessage());
        }
    }

    private void calcularIdade(Cliente cliente) {
        if (cliente.getDataNascimento() != null) {
            LocalDate dataNascimento = cliente.getDataNascimento();
            LocalDate hoje = LocalDate.now();
            Period periodo = Period.between(dataNascimento, hoje);
            cliente.setIdade(periodo.getYears());
        } else {
            cliente.setIdade(0);
        }
    }

    private void validarCamposObrigatorios(Cliente cliente) {
        if (cliente.getNome() == null || cliente.getNome().isEmpty()) {
            throw new CampoObrigatorioException("O campo nome é obrigatório.");
        }
        if (cliente.getSobrenome() == null || cliente.getSobrenome().isEmpty()) {
            throw new CampoObrigatorioException("O campo sobrenome é obrigatório.");
        }
        if (cliente.getEmail() == null || cliente.getEmail().isEmpty()) {
            throw new CampoObrigatorioException("O campo email é obrigatório.");
        }
        if (cliente.getCpf() == null || cliente.getCpf().isEmpty()) {
            throw new CampoObrigatorioException("O campo CPF é obrigatório.");
        }
        if (cliente.getRg() == null || cliente.getRg().isEmpty()) {
            throw new CampoObrigatorioException("O campo RG é obrigatório.");
        }
        if (cliente.getOrgaoEmissor() == null || cliente.getOrgaoEmissor().isEmpty()) {
            throw new CampoObrigatorioException("O campo órgão emissor é obrigatório.");
        }
        if (cliente.getDataEmissaoRG() == null) {
            throw new CampoObrigatorioException("O campo data de emissão do RG é obrigatório.");
        }
        if (cliente.getDataNascimento() == null) {
            throw new CampoObrigatorioException("O campo data de nascimento é obrigatório.");
        }
    }

    private int geraScore() {
        SecureRandom random = new SecureRandom();
        return random.nextInt(1000) + 1;
    }
}