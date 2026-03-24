package com.shopee.pattern.strategy;

import java.math.BigDecimal;

public interface PagamentoStrategy {
    String gerarPagamento(BigDecimal valor, String clienteId);
    String realizarPagamento(BigDecimal valor);
}
