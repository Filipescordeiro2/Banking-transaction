package com.Banking.transation.dto.response;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public record CartaoResponse(
        UUID id,
        String segmento,
        String numeroCartao,
        String CVV,
        LocalDate dataDeExpiracao,
        LocalDateTime dataDeEmissao,
        String nomeEmpresso,
        boolean status,
        BigDecimal limite
) {}