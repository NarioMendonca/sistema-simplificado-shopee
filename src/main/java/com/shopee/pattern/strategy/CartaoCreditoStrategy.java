package com.shopee.pattern.strategy;

import java.math.BigDecimal;

public class CartaoCreditoStrategy implements PagamentoStrategy {

    @Override
    public String realizarPagamento(BigDecimal valor) {
        return "PAGAMENTO_APROVADO";
    }

    @Override
    public String gerarPagamento(BigDecimal valor, String clienteId) {
        return null;
    }
    
}
