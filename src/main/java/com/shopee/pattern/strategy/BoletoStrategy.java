package com.shopee.pattern.strategy;

import java.math.BigDecimal;
import java.util.UUID;

public class BoletoStrategy implements PagamentoStrategy {

    @Override
    public String realizarPagamento(BigDecimal valor) {
        return "PAGAMENTO_APROVADO";
    }

    @Override
    public String gerarPagamento(BigDecimal valor, String clienteId) {
        return UUID.randomUUID().toString();
    }
    
}
