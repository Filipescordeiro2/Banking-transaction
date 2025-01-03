package com.Banking.transation.domain;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import lombok.Builder;
import org.hibernate.validator.constraints.br.CPF;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "CLIENTES")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Cliente {

    @Id
    @GeneratedValue(generator = "UUID")
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

    private String categoriaCliente;

    private int score;

    private boolean desejaCredito;


    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL) // Relacionamento com Cartão
    @JsonIgnore
    private List<Cartao> cartoes = new ArrayList<>(); // Inicializa a lista de cartões


    @OneToOne(mappedBy = "cliente", cascade = CascadeType.ALL) // Um cliente tem uma conta
    @JsonIgnore
    private Conta conta; // A conta associada ao cliente
}
