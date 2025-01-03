package com.Banking.transation.dto.request;

import com.Banking.transation.domain.Cliente;
import com.Banking.transation.domain.Conta;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartaoRequest {


    private UUID id;

    @NotNull
    private String segmento;
    @NotNull
    private String senha;
    @NotNull
    private String numeroCartao;
    @NotNull
    private String CVV;
    @NotNull
    private LocalDate dataDeExpiracao;
    @NotNull
    private LocalDateTime dataDeEmissao;
    @NotNull
    private String nomeEmpresso;
    @NotNull
    private boolean status;
    @NotNull
    private BigDecimal limite;
    @NotNull
    private Cliente cliente;
    @NotNull
    private Conta conta;


}
