package com.Banking.transation.dto.request;

import com.Banking.transation.domain.Cartao;
import com.Banking.transation.domain.Conta;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClienteRequest {

    private UUID id;

    @NotBlank
    private String nome;

    @NotBlank
    private String sobrenome;

    @NotBlank
    @Column(unique = true)
    private String telefone;

    @NotBlank
    @Email
    @Column(unique = true)
    private String email;

    @NotBlank
    @CPF
    @Column(unique = true)
    private String cpf;

    @NotBlank
    @Column(unique = true)
    private String rg;

    @NotBlank
    private String orgaoEmissor;

    @NotNull
    private LocalDate dataEmissaoRG;

    @NotNull
    @Past
    private LocalDate dataNascimento;

    private int idade;

    private LocalDateTime dataCriacaoUsuario;

    private boolean pessoalPoliticamenteExposta;

    private boolean status;

    @NotNull
    private boolean desejaCredito;

    private String categoriaCliente;

    private int score;

    private List<Cartao> cartoes = new ArrayList<>(); // Inicializa a lista de cartões

    private Conta conta; // A conta associada ao cliente
}
