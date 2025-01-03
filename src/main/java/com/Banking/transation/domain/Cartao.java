package com.Banking.transation.domain;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="CARTOES")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Cartao {

    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    @NotNull
    private String segmento;
    @NotNull
    private String senha;

    private String numeroCartao;
    private String CVV;
    private LocalDate dataDeExpiracao;
    private LocalDateTime dataDeEmissao;
    private String nomeEmpresso;
    private boolean status;

    private BigDecimal limite;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    @JsonIgnore
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "conta_id")
    @JsonIgnore
    private Conta conta;

}
