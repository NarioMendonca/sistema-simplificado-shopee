package com.shopee.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public class Pedido {
    int id;
    int cliente_id;
    OffsetDateTime data_pedido;
    Status status;
    BigDecimal valor_total;
    String metodo_pagamento;
    String endereco_entrega;

    public enum Status {
        aguardando,
        pago,
        enviado,
        entregue,
        cancelado
    }

    public Pedido(
        int id,
        int cliente_id,
        OffsetDateTime data_pedido,
        Status status,
        BigDecimal valor_total,
        String metodo_pagamento,
        String endereco_entrega
    ) {
        setId(id);
        setCliente_id(cliente_id);
        setData_pedido(data_pedido);
        setStatus(status);
        setValor_total(valor_total);
        setMetodo_pagamento(metodo_pagamento);
        setEndereco_entrega(endereco_entrega);
    }

    public int getId() {
        return id;
    }

    public int getCliente_id() {
        return cliente_id;
    }

    public OffsetDateTime getData_pedido() {
        return data_pedido;
    }

    public Status getStatus() {
        return status;
    }

    public BigDecimal getValor_total() {
        return valor_total;
    }

    public String getMetodo_pagamento() {
        return metodo_pagamento;
    }

    public String getEndereco_entrega() {
        return endereco_entrega;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public void setCliente_id(int cliente_id) {
        this.cliente_id = cliente_id;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setValor_total(BigDecimal valor_total) {
        this.valor_total = valor_total;
    }

    public void setData_pedido(OffsetDateTime data_pedido) {
        this.data_pedido = data_pedido;
    }

    public void setMetodo_pagamento(String metodo_pagamento) {
        this.metodo_pagamento = metodo_pagamento;
    }

    public void setEndereco_entrega(String endereco_entrega) {
        this.endereco_entrega = endereco_entrega;
    }
}
