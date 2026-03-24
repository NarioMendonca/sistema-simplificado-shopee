package com.shopee.pattern.strategy;

import java.math.BigDecimal;
import java.util.UUID;

public class PixStrategy implements PagamentoStrategy {

    String cpf;

    public PixStrategy(String cpf) {
        this.cpf = cpf;
    }

    @Override
    public String realizarPagamento(BigDecimal valor) {
        return "PAGAMENTO_APROVADO";
    }

    @Override
    public String gerarPagamento(BigDecimal valor, String clienteId) {
        if (cpf.isEmpty() || cpf == null) {
            throw new RuntimeException("Insira um CPF para vincular ao pagamento.");
        }

        return UUID.randomUUID().toString();
    }

}