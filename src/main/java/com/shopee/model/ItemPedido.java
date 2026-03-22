package com.shopee.model;

import java.math.BigDecimal;

public class ItemPedido {
    Integer id;
    Integer pedidoId;
    Integer produtoId;
    Integer quantidade;
    BigDecimal precoUnitario;

    public ItemPedido(
        Integer id,
        Integer pedidoId,
        Integer produtoId,
        Integer quantidade,
        BigDecimal precoUnitario
    ) {
        setId(quantidade);
        setPedidoId(pedidoId);
        setProdutoId(produtoId);
        setQuantidade(quantidade);
        setPrecoUnitario(precoUnitario);
    }

    public Integer getId() {
        return id;
    }

    public Integer getPedidoId() {
        return pedidoId;
    }

    public Integer getProdutoId() {
        return produtoId;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public BigDecimal getPrecoUnitario() {
        return precoUnitario;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setPedidoId(Integer pedidoId) {
        this.pedidoId = pedidoId;
    }

    public void setProdutoId(Integer produtoId) {
        this.produtoId = produtoId;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public void setPrecoUnitario(BigDecimal precoUnitario) {
        this.precoUnitario = precoUnitario;
    }
}
