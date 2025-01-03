package com.Banking.transation.utils.mappers;

import com.Banking.transation.domain.Cartao;
import com.Banking.transation.dto.request.CartaoRequest;
import com.Banking.transation.dto.response.CartaoResponse;
import org.springframework.stereotype.Component;

@Component
public class CartaoMapper {

    public static Cartao fromDTO(CartaoRequest request) {
        if (request == null) {
            return null;
        }
        return Cartao.builder()
                .id(request.getId())
                .segmento(request.getSegmento())
                .senha(request.getSenha())
                .numeroCartao(request.getNumeroCartao())
                .CVV(request.getCVV())
                .dataDeExpiracao(request.getDataDeExpiracao())
                .dataDeEmissao(request.getDataDeEmissao())
                .nomeEmpresso(request.getNomeEmpresso())
                .status(request.isStatus())
                .limite(request.getLimite())
                .cliente(request.getCliente())
                .conta(request.getConta())
                .build();
    }

    public static CartaoResponse toDTO(Cartao cartao) {
        if (cartao == null) {
            return null;
        }
        return new CartaoResponse(
                cartao.getId(),
                cartao.getSegmento(),
                cartao.getNumeroCartao(),
                cartao.getCVV(),
                cartao.getDataDeExpiracao(),
                cartao.getDataDeEmissao(),
                cartao.getNomeEmpresso(),
                cartao.isStatus(),
                cartao.getLimite()
        );
    }
}