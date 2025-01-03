package com.Banking.transation.dto.response;

import java.util.UUID;

public record ContaResponse(
    UUID id,
    String numeroConta,
    String agencia
) {}