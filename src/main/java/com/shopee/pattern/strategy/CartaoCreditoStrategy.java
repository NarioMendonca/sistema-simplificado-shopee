package com.shopee.pattern.strategy;

import java.math.BigDecimal;

public class CartaoCreditoStrategy implements PagamentoStrategy {
    String numeroCartao;
    String cvv;
    String titular;

    @Override
    public String realizarPagamento(BigDecimal valor) {
        if (numeroCartao.isEmpty() || numeroCartao == null) {
            throw new RuntimeException("Dados inválidos para cartão");
        }

        if (cvv.isEmpty() || cvv == null) {
            throw new RuntimeException("Dados inválidos para cartão");
        }

        if (titular.isEmpty() || titular == null) {
            throw new RuntimeException("Dados inválidos para cartão");
        }

        return "PAGAMENTO_APROVADO";
    }

    @Override
    public String gerarPagamento(BigDecimal valor, String clienteId) {
        return null;
    }
    
}
