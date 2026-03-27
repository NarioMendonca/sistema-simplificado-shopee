package com.shopee.pattern.factory;

import com.shopee.pattern.strategy.BoletoStrategy;
import com.shopee.pattern.strategy.CartaoCreditoStrategy;
import com.shopee.pattern.strategy.PagamentoStrategy;
import com.shopee.pattern.strategy.PixStrategy;

public class PagamentoFactory {
    public static PagamentoStrategy criarPagamento(String tipo) {
        if (tipo == null || tipo.isEmpty()) {
            throw new RuntimeException("pagamento não declarado");
        }

        switch(tipo) {
            case "pix":
                return new PixStrategy();
            case "cartao":
                return new CartaoCreditoStrategy();
            case "boleto":
                return new BoletoStrategy();
            default:
                throw new RuntimeException("Tipo de pagamento não conhecido");
        }

    }
}
