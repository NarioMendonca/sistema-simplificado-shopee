package com.shopee.model;

import java.math.BigDecimal;

public class ItemPedido {
    int id;
    int pedidoId;
    int produtoId;
    int quantidade;
    BigDecimal precoUnitario;

    public ItemPedido(
        int id,
        int pedidoId,
        int produtoId,
        int quantidade,
        BigDecimal precoUnitario
    ) {
        setId(quantidade);
        setPedidoId(pedidoId);
        setProdutoId(produtoId);
        setQuantidade(quantidade);
        setPrecoUnitario(precoUnitario);
    }

    public int getId() {
        return id;
    }

    public int getPedidoId() {
        return pedidoId;
    }

    public int getProdutoId() {
        return produtoId;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public BigDecimal getPrecoUnitario() {
        return precoUnitario;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPedidoId(int pedidoId) {
        this.pedidoId = pedidoId;
    }

    public void setProdutoId(int produtoId) {
        this.produtoId = produtoId;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public void setPrecoUnitario(BigDecimal precoUnitario) {
        this.precoUnitario = precoUnitario;
    }
}
