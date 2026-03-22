package com.shopee.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

public class Pedido {
    private Integer id;
    private Integer clienteId;
    private OffsetDateTime dataPedido;
    private Status status;
    private BigDecimal valorTotal;
    private String metodoPagamento;
    private String enderecoEntrega;
    private List<ItemPedido> itensPedido;


    public enum Status {
        aguardando,
        pago,
        enviado,
        entregue,
        cancelado
    }

    public Pedido(
        Integer id,
        Integer clienteId,
        OffsetDateTime dataPedido,
        Status status,
        BigDecimal valorTotal,
        String metodoPagamento,
        String enderecoEntrega,
        List<ItemPedido> itensPedido
    ) {
        setId(id);
        setClienteId(clienteId);
        setDataPedido(dataPedido);
        setStatus(status);
        setValorTotal(valorTotal);
        setMetodoPagamento(metodoPagamento);
        setEnderecoEntrega(enderecoEntrega);
        setItensPedido(itensPedido);
    }

    public void adicionarItemAoPedido(ItemPedido itemPedido) {
        this.itensPedido.add(itemPedido);
    }

    //getters
    public Integer getId() {
        return id;
    }

    public Integer getClienteId() {
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

    public List<ItemPedido> getItensPedidos() {
        return this.itensPedido;
    }

    // setters
    public void setId(Integer id) {
        this.id = id;
    }
    
    public void setClienteId(Integer clienteId) {
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

    public void setItensPedido(List<ItemPedido> itensPedido) {
        this.itensPedido = itensPedido;
    }
}
