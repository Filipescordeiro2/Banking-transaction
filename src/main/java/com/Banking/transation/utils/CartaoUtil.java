package com.Banking.transation.utils;

import java.math.BigDecimal;
import java.security.SecureRandom;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.Banking.transation.domain.Cartao;
import com.Banking.transation.domain.Cliente;
import com.Banking.transation.domain.Conta;
import com.Banking.transation.dto.request.CartaoRequest;
import com.Banking.transation.exception.CampoObrigatorioException;
import com.Banking.transation.repository.CartaoRepository;
import com.Banking.transation.repository.ClienteRepository;
import com.Banking.transation.repository.ContaRepository;
import com.Banking.transation.utils.mappers.CartaoMapper;

/**
 * Classe utilitária para operações relacionadas a Cartão.
 */
public class CartaoUtil {

    /**
     * Gera um número de cartão aleatório.
     *
     * @return Um número de cartão de 16 dígitos como String.
     */
    public static String gerarNumeroCartao() {
        SecureRandom random = new SecureRandom();
        StringBuilder numero = new StringBuilder();
        for (int i = 0; i < 16; i++) {
            numero.append(random.nextInt(10));
        }
        return numero.toString();
    }

    /**
     * Gera a data de expiração para um cartão.
     *
     * @param dataDeEmissao A data de emissão do cartão.
     * @return A data de expiração, 4 anos após a data de emissão.
     */
    public static LocalDate gerarDataDeExpiracao(LocalDateTime dataDeEmissao) {
        return dataDeEmissao.toLocalDate().plusYears(4);
    }

    /**
     * Gera um CVV aleatório para um cartão.
     *
     * @return Um CVV de 3 dígitos como String.
     */
    public static String geradorCvv() {
        SecureRandom random = new SecureRandom();
        StringBuilder cvv = new StringBuilder();
        for (int i = 0; i < 3; i++) {
            cvv.append(random.nextInt(10));
        }
        return cvv.toString();
    }

    /**
     * Valida a senha do cartão.
     *
     * @param senha A senha a ser validada.
     * @return True se a senha tiver 6 caracteres, false caso contrário.
     */
    public static boolean validaSenha(String senha) {
        return senha.length() == 6;
    }

    /**
     * Cria um limite de crédito com base no score do cliente.
     *
     * @param score O score do cliente.
     * @return O limite de crédito como BigDecimal.
     * @throws IllegalArgumentException se o score estiver fora do intervalo esperado.
     */
    public static BigDecimal criarLimite(int score) {
        SecureRandom random = new SecureRandom();
        BigDecimal limiteMinimo = BigDecimal.ZERO;
        BigDecimal limiteMaximo = BigDecimal.ZERO;

        if (score >= 80 && score <= 200) {
            limiteMinimo = new BigDecimal("20.00");
            limiteMaximo = new BigDecimal("80.00");
        } else if (score >= 201 && score <= 300) {
            limiteMinimo = new BigDecimal("100.00");
            limiteMaximo = new BigDecimal("200.00");
        } else if (score >= 301 && score <= 400) {
            limiteMinimo = new BigDecimal("450.00");
            limiteMaximo = new BigDecimal("600.00");
        } else if (score >= 401 && score <= 600) {
            limiteMinimo = new BigDecimal("1200.00");
            limiteMaximo = new BigDecimal("2000.00");
        } else if (score >= 601 && score <= 780) {
            limiteMinimo = new BigDecimal("4000.00");
            limiteMaximo = new BigDecimal("6000.00");
        } else if (score >= 781 && score <= 800) {
            limiteMinimo = new BigDecimal("6000.00");
            limiteMaximo = new BigDecimal("10000.00");
        } else if (score >= 801 && score <= 1000) {
            limiteMinimo = new BigDecimal("10000.00");
            limiteMaximo = new BigDecimal("20000.00");
        } else if (score >= 0 && score <= 79) {
            throw new IllegalArgumentException("Nao liberado credito");
        } else {
            throw new IllegalArgumentException("Score fora do intervalo esperado.");
        }

        double valorAleatorio = limiteMinimo.doubleValue() +
                (limiteMaximo.doubleValue() - limiteMinimo.doubleValue()) * random.nextDouble();

        return BigDecimal.valueOf(valorAleatorio).setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * Recupera um cliente pelo ID.
     *
     * @param clienteId O ID do cliente.
     * @param clienteRepository O repositório para buscar o cliente.
     * @return O cliente.
     * @throws CampoObrigatorioException se o cliente não for encontrado.
     */
    public static Cliente getCliente(UUID clienteId, ClienteRepository clienteRepository) {
        return clienteRepository.findById(clienteId)
                .orElseThrow(() -> new CampoObrigatorioException("Cliente com ID " + clienteId + " não encontrado."));
    }

    /**
     * Recupera uma conta pelo ID e ID do cliente.
     *
     * @param contaId O ID da conta.
     * @param clienteId O ID do cliente.
     * @param contaRepository O repositório para buscar a conta.
     * @return A conta.
     * @throws CampoObrigatorioException se a conta não for encontrada ou não pertencer ao cliente.
     */
    public static Conta getConta(UUID contaId, UUID clienteId, ContaRepository contaRepository) {
        Conta conta = contaRepository.findById(contaId)
                .orElseThrow(() -> new CampoObrigatorioException("Conta com ID " + contaId + " não encontrada."));
        if (!conta.getCliente().getId().equals(clienteId)) {
            throw new CampoObrigatorioException("A conta com ID " + contaId + " não pertence ao cliente com ID " + clienteId + ".");
        }
        return conta;
    }

    /**
     * Valida uma solicitação de cartão.
     *
     * @param cartaoRequest A solicitação de cartão a ser validada.
     * @param cliente O cliente que está fazendo a solicitação.
     * @param cartaoRepository O repositório para verificar cartões existentes.
     * @throws CampoObrigatorioException se qualquer validação falhar.
     */
    public static void validateCartaoRequest(CartaoRequest cartaoRequest, Cliente cliente, CartaoRepository cartaoRepository) {
        if (cartaoRequest.getSegmento() == null || cartaoRequest.getSegmento().isEmpty()) {
            throw new CampoObrigatorioException("O campo segmento é obrigatório.");
        }

        if (cartaoAtivoNoMesmoSegmento(cliente, cartaoRequest.getSegmento(), cartaoRepository)) {
            throw new CampoObrigatorioException("Já existe um cartão ativo do segmento " + cartaoRequest.getSegmento());
        }

        if (cartaoRequest.getSegmento().equalsIgnoreCase("Multiplo") && temCartaoMultiploAtivo(cliente, cartaoRepository)) {
            throw new CampoObrigatorioException("Cliente já possui um cartão múltiplo ativo.");
        }

        if (cartaoRequest.getSegmento().equalsIgnoreCase("Multiplo")) {
            bloquearCartoesDebitoCredito(cliente, cartaoRepository);
        }

        if (!validaSenha(cartaoRequest.getSenha())) {
            throw new CampoObrigatorioException("Senha está no padrão incorreto");
        }

        if (!cliente.isDesejaCredito() && (cartaoRequest.getSegmento().equalsIgnoreCase("Credito") || cartaoRequest.getSegmento().equalsIgnoreCase("Multiplo"))) {
            throw new CampoObrigatorioException("Cliente não deseja crédito, não é possível liberar cartão do segmento Crédito ou Múltiplo.");
        }

        if (cliente.isDesejaCredito() && isElegivelParaCredito(cliente.getScore())) {
            cartaoRequest.setSegmento("Multiplo");
            bloquearCartoesDebitoCredito(cliente, cartaoRepository);
        } else if (cartaoRequest.getSegmento().equalsIgnoreCase("Credito") && temCartaoDebitoAtivo(cliente, cartaoRepository)) {
            cartaoRequest.setSegmento("Multiplo");
            bloquearCartoesDebitoCredito(cliente, cartaoRepository);
        }
    }

    /**
     * Verifica se há um cartão ativo no mesmo segmento para o cliente.
     *
     * @param cliente O cliente a ser verificado.
     * @param segmento O segmento a ser verificado.
     * @param cartaoRepository O repositório para buscar cartões existentes.
     * @return True se houver um cartão ativo no mesmo segmento, false caso contrário.
     */
    public static boolean cartaoAtivoNoMesmoSegmento(Cliente cliente, String segmento, CartaoRepository cartaoRepository) {
        List<Cartao> cartoesAtivos = cartaoRepository.findByClienteAndStatus(cliente, true);
        return cartoesAtivos.stream().anyMatch(cartao -> cartao.getSegmento().equalsIgnoreCase(segmento));
    }

    /**
     * Verifica se o cliente possui um cartão múltiplo ativo.
     *
     * @param cliente O cliente a ser verificado.
     * @param cartaoRepository O repositório para buscar cartões existentes.
     * @return True se o cliente possuir um cartão múltiplo ativo, false caso contrário.
     */
    public static boolean temCartaoMultiploAtivo(Cliente cliente, CartaoRepository cartaoRepository) {
        List<Cartao> cartoesAtivos = cartaoRepository.findByClienteAndStatus(cliente, true);
        return cartoesAtivos.stream().anyMatch(cartao -> cartao.getSegmento().equalsIgnoreCase("Multiplo"));
    }

    /**
     * Verifica se o cliente possui um cartão de débito ativo.
     *
     * @param cliente O cliente a ser verificado.
     * @param cartaoRepository O repositório para buscar cartões existentes.
     * @return True se o cliente possuir um cartão de débito ativo, false caso contrário.
     */
    public static boolean temCartaoDebitoAtivo(Cliente cliente, CartaoRepository cartaoRepository) {
        List<Cartao> cartoesAtivos = cartaoRepository.findByClienteAndStatus(cliente, true);
        return cartoesAtivos.stream().anyMatch(cartao -> cartao.getSegmento().equalsIgnoreCase("Debito"));
    }

    /**
     * Bloqueia todos os cartões de débito e crédito ativos do cliente.
     *
     * @param cliente O cliente cujos cartões serão bloqueados.
     * @param cartaoRepository O repositório para atualizar o status dos cartões.
     */
    public static void bloquearCartoesDebitoCredito(Cliente cliente, CartaoRepository cartaoRepository) {
        List<Cartao> cartoesAtivos = cartaoRepository.findByClienteAndStatus(cliente, true);
        for (Cartao cartao : cartoesAtivos) {
            if (cartao.getSegmento().equalsIgnoreCase("Debito") || cartao.getSegmento().equalsIgnoreCase("Credito")) {
                cartao.setStatus(false);
                cartaoRepository.save(cartao);
            }
        }
    }

    /**
     * Recupera o limite de crédito existente para o cliente.
     *
     * @param cliente O cliente a ser verificado.
     * @param cartaoRepository O repositório para buscar cartões existentes.
     * @return O maior limite de crédito entre os cartões de crédito ativos do cliente, ou null se não houver.
     */
    public static BigDecimal obterLimiteCreditoExistente(Cliente cliente, CartaoRepository cartaoRepository) {
        List<Cartao> cartoesAtivos = cartaoRepository.findByClienteAndStatus(cliente, true);
        return cartoesAtivos.stream()
                .filter(cartao -> cartao.getSegmento().equalsIgnoreCase("Credito"))
                .map(Cartao::getLimite)
                .max(BigDecimal::compareTo)
                .orElse(null);
    }

    /**
     * Verifica se o cliente é elegível para crédito com base no seu score.
     *
     * @param score O score do cliente.
     * @return True se o score for 80 ou superior, false caso contrário.
     */
    public static boolean isElegivelParaCredito(int score) {
        return score >= 80;
    }

    /**
     * Cria um novo cartão com base na solicitação, cliente e informações da conta.
     *
     * @param cartaoRequest A solicitação de cartão.
     * @param cliente O cliente para quem o cartão está sendo criado.
     * @param conta A conta associada ao cartão.
     * @param cartaoMapper O mapper para converter DTO para entidade.
     * @param cartaoRepository O repositório para verificar cartões existentes.
     * @return O cartão criado.
     * @throws CampoObrigatorioException se qualquer validação falhar.
     */
    public static Cartao createCartao(CartaoRequest cartaoRequest, Cliente cliente, Conta conta, CartaoMapper cartaoMapper, CartaoRepository cartaoRepository) {
        Cartao cartao = cartaoMapper.fromDTO(cartaoRequest);
        cartao.setCliente(cliente);
        cartao.setConta(conta);
        cartao.setNumeroCartao(gerarNumeroCartao());
        cartao.setCVV(geradorCvv());
        cartao.setDataDeEmissao(LocalDateTime.now());
        cartao.setDataDeExpiracao(gerarDataDeExpiracao(cartao.getDataDeEmissao()));
        cartao.setStatus(true);

        if (!cliente.isDesejaCredito()) {
            cartao.setLimite(BigDecimal.ZERO);
        } else if (cartao.getSegmento().equalsIgnoreCase("Credito") || cartao.getSegmento().equalsIgnoreCase("Multiplo")) {
            try {
                BigDecimal novoLimite = criarLimite(cliente.getScore());
                if (cartaoRequest.getSegmento().equalsIgnoreCase("Multiplo")) {
                    BigDecimal limiteCreditoExistente = obterLimiteCreditoExistente(cliente, cartaoRepository);
                    if (limiteCreditoExistente != null && novoLimite.compareTo(limiteCreditoExistente) < 0) {
                        novoLimite = limiteCreditoExistente;
                    }
                }
                cartao.setLimite(novoLimite);
            } catch (IllegalArgumentException e) {
                throw new CampoObrigatorioException("Não liberado crédito: " + e.getMessage());
            }
        } else {
            cartao.setLimite(BigDecimal.ZERO);
        }
        return cartao;
    }
}