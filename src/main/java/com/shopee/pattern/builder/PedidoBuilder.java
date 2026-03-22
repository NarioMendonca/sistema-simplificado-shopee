package com.shopee.pattern.builder;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import com.shopee.model.ItemPedido;
import com.shopee.model.Pedido;
import com.shopee.model.Pedido.Status;

public class PedidoBuilder {
    private Integer id;
    private Integer clienteId;
    private OffsetDateTime dataPedido;
    private Pedido.Status status = Status.aguardando;
    private BigDecimal valorTotal;
    private String metodoPagamento;
    private String enderecoEntrega;
    private List<ItemPedido> itensPedido = new ArrayList<>();

    public PedidoBuilder adicionarItem(ItemPedido item) {
        this.itensPedido.add(item);
        return this;
    }

    public PedidoBuilder id(Integer id) {
        this.id = id;
        return this;
    }

    public PedidoBuilder clienteId(Integer clienteId) {
        this.clienteId = clienteId;
        return this;
    }

    public PedidoBuilder dataPedido(OffsetDateTime dataPedido) {
        this.dataPedido = dataPedido;
        return this;
    }

    public PedidoBuilder status(Status status) {
        this.status = status;
        return this;
    }

    public PedidoBuilder valorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
        return this;
    }

    public PedidoBuilder metodoPagamento(String pagamento) {
        this.metodoPagamento = pagamento;
        return this;
    }

    public PedidoBuilder enderecoEntrega(String endereco) {
        this.enderecoEntrega = endereco;
        return this;
    }

    public Pedido build() {
        if (clienteId == null) {
            throw new IllegalStateException("Cliente é obrigatório");
        }

        if (valorTotal == null) {
            throw new IllegalStateException("Valor do pedido é obrigatório");
        }

        if (metodoPagamento == null || metodoPagamento.isEmpty()) {
            throw new IllegalStateException("Metodo de pagamento é obrigatório");
        }

        if (enderecoEntrega == null || enderecoEntrega.isEmpty()) {
            throw new IllegalStateException("Metodo de pagamento é obrigatório");
        }

        if (itensPedido.isEmpty()) {
            throw new IllegalStateException("Um pedido deve ter pelo menos um item");
        }

        return new Pedido(
            id,
            clienteId,
            dataPedido,
            status,
            valorTotal,
            metodoPagamento,
            enderecoEntrega,
            itensPedido
        );
    }
}
