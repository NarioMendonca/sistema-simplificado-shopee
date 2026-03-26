package com.shopee.services;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.shopee.controller.CarrinhoController.ProdutoCheckout;
import com.shopee.dao.PedidoDAO;
import com.shopee.dao.UsuarioDAO;
import com.shopee.model.ItemPedido;
import com.shopee.model.Pedido;
import com.shopee.model.Produto;
import com.shopee.model.Usuario;
import com.shopee.model.Pedido.Status;
import com.shopee.util.Logger;

public class PedidoService {
    private PedidoDAO pedidoDAO = new PedidoDAO();
    private UsuarioDAO usuarioDAO = new UsuarioDAO();
    private Logger logger = Logger.getInstance();

    public Pedido criarPedido(Integer clienteId, List<ProdutoCheckout> produtos, BigDecimal valorTotal, String metodoPagamento, String enderecoEntrega) {
        Optional<Usuario> clienteExiste = usuarioDAO.buscarPorId(clienteId);
        if (clienteExiste.isEmpty()) {
            throw new RuntimeException("Cliente para criar pedido não existe");
        }

        // não é possível saber qual será o PedidoId no momento de criar os itens do pedido
        // Então isso deve ser adicionado no momento de inserir no banco de dados 
        List<ItemPedido> itensPedidos = new ArrayList<>();
        for (ProdutoCheckout produtoCheckout : produtos) {
            ItemPedido itemPedido = new ItemPedido(
                null, 
                null, 
                produtoCheckout.getProduto().getId(), 
                produtoCheckout.getQuantidade(), 
                produtoCheckout.getProduto().getPreco()
            );
            itensPedidos.add(itemPedido);
        }

        Pedido pedido = new Pedido(
            null, 
            clienteId, 
            null, 
            Status.aguardando, 
            valorTotal, 
            metodoPagamento, 
            enderecoEntrega, 
            itensPedidos
        );

        Pedido pedidoSalvo = pedidoDAO.salvar(pedido);
        logger.logInfo("Pedido criado com sucesso id=" + pedidoSalvo.getId());
        return pedidoSalvo;
    }

    public List<Pedido> listarPedidosCliente(Integer clienteId) {
        logger.logInfo("Buscando pedidos do cliente id=" + clienteId);
        return pedidoDAO.buscarPorClienteId(clienteId);
    }

    public List<Pedido> listarPedidosVendedor(Integer vendedorId) {
        logger.logInfo("Buscando pedidos do vendedor id=" + vendedorId);
        return pedidoDAO.buscarPorVendedorId(vendedorId);
    }
}
