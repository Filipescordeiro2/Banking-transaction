package com.Banking.transation.utils.mask;

public class CartaoMaskUtil {

    public static String maskNumeroCartao(String numeroCartao) {
        if (numeroCartao == null || numeroCartao.length() != 16) {
            throw new IllegalArgumentException("Número do cartão inválido");
        }
        return numeroCartao.substring(0, 4) + " **** **** ****";
    }
}
