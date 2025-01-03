package com.Banking.transation.service;

import java.util.UUID;

import com.Banking.transation.domain.Cartao;
import com.Banking.transation.domain.Cliente;
import com.Banking.transation.domain.Conta;
import com.Banking.transation.dto.request.CartaoRequest;
import com.Banking.transation.dto.response.CartaoResponse;
import com.Banking.transation.exception.CampoObrigatorioException;
import com.Banking.transation.exception.ErroGenericoException;
import com.Banking.transation.repository.CartaoRepository;
import com.Banking.transation.repository.ClienteRepository;
import com.Banking.transation.repository.ContaRepository;
import com.Banking.transation.utils.CartaoUtil;
import com.Banking.transation.utils.mappers.CartaoMapper;
import com.Banking.transation.utils.mask.CartaoMaskUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Serviço para operações relacionadas a Cartão.
 */
@Service
public class CartaoService {

    @Autowired
    private CartaoRepository cartaoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ContaRepository contaRepository;

    @Autowired
    private CartaoMapper cartaoMapper;

    /**
     * Cria um novo cartão para um cliente e conta específicos.
     *
     * @param clienteId O ID do cliente.
     * @param contaId O ID da conta.
     * @param cartaoRequest A solicitação de criação do cartão.
     * @return A resposta contendo os detalhes do cartão criado.
     * @throws CampoObrigatorioException se houver algum erro de validação.
     * @throws ErroGenericoException se ocorrer um erro genérico durante a criação do cartão.
     */
    public CartaoResponse criarCartao(UUID clienteId, UUID contaId, CartaoRequest cartaoRequest) {
        try {
            // Recupera o cliente pelo ID
            Cliente cliente = CartaoUtil.getCliente(clienteId, clienteRepository);
            // Recupera a conta pelo ID e ID do cliente
            Conta conta = CartaoUtil.getConta(contaId, clienteId, contaRepository);

            // Valida a solicitação de criação do cartão
            CartaoUtil.validateCartaoRequest(cartaoRequest, cliente, cartaoRepository);

            // Cria o cartão com base na solicitação, cliente e conta
            Cartao cartao = CartaoUtil.createCartao(cartaoRequest, cliente, conta, cartaoMapper, cartaoRepository);

            // Salva o cartão no repositório
            Cartao savedCartao = cartaoRepository.save(cartao);
            // Mascarar o número do cartão para segurança
            String maskedNumeroCartao = CartaoMaskUtil.maskNumeroCartao(savedCartao.getNumeroCartao());
            // Retorna a resposta com os detalhes do cartão criado
            return new CartaoResponse(
                    savedCartao.getId(),
                    savedCartao.getSegmento(),
                    maskedNumeroCartao,
                    savedCartao.getCVV(),
                    savedCartao.getDataDeExpiracao(),
                    savedCartao.getDataDeEmissao(),
                    savedCartao.getNomeEmpresso(),
                    savedCartao.isStatus(),
                    savedCartao.getLimite()
            );
        } catch (CampoObrigatorioException e) {
            throw e;
        } catch (Exception e) {
            throw new ErroGenericoException("Erro ao criar cartão: " + e.getMessage());
        }
    }
}