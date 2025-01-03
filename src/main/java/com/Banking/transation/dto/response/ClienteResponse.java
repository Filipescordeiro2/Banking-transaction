package com.Banking.transation.dto.response;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public record ClienteResponse(
        UUID id,
        String nome,
        String sobrenome,
        String telefone,
        String email,
        String cpf,
        String rg,
        String orgaoEmissor,
        LocalDate dataEmissaoRG,
        LocalDate dataNascimento,
        int idade,
        LocalDateTime dataCriacaoUsuario,
        boolean pessoalPoliticamenteExposta,
        boolean status,
        String categoriaCliente,
        int score,
        boolean desejaCredito
) {}