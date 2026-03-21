package com.shopee.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public class Pedido {
    int id;
    int clienteId;
    OffsetDateTime dataPedido;
    Status status;
    BigDecimal valorTotal;
    String metodoPagamento;
    String enderecoEntrega;

    public enum Status {
        aguardando,
        pago,
        enviado,
        entregue,
        cancelado
    }

    public Pedido(
        int id,
        int clienteId,
        OffsetDateTime dataPedido,
        Status status,
        BigDecimal valorTotal,
        String metodoPagamento,
        String enderecoEntrega
    ) {
        setId(id);
        setCliente_id(clienteId);
        setDataPedido(dataPedido);
        setStatus(status);
        setValorTotal(valorTotal);
        setMetodoPagamento(metodoPagamento);
        setEnderecoEntrega(enderecoEntrega);
    }

    public int getId() {
        return id;
    }

    public int getClienteId() {
        return clienteId;
    }

    public OffsetDateTime getDataPedido() {
        return dataPedido;
    }

    public Status getStatus() {
        return status;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public String getMetodoPagamento() {
        return metodoPagamento;
    }

    public String getEnderecoEntrega() {
        return enderecoEntrega;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public void setCliente_id(int clienteId) {
        this.clienteId = clienteId;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }

    public void setDataPedido(OffsetDateTime dataPedido) {
        this.dataPedido = dataPedido;
    }

    public void setMetodoPagamento(String metodoPagamento) {
        this.metodoPagamento = metodoPagamento;
    }

    public void setEnderecoEntrega(String enderecoEntrega) {
        this.enderecoEntrega = enderecoEntrega;
    }
}
