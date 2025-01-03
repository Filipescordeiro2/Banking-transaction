package com.Banking.transation.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "CONTAS")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Conta {

    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    @OneToOne
    @JoinColumn(name = "cliente_id",unique = true)
    private Cliente cliente;


    @OneToMany(mappedBy = "conta", cascade = CascadeType.ALL)
    private List<Cartao> cartoes = new ArrayList<>();

    private String numeroConta;

    private String agencia;
}

