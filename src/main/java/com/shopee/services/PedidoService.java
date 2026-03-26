package com.shopee.services;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import com.shopee.dao.PedidoDAO;
import com.shopee.dao.UsuarioDAO;
import com.shopee.model.Pedido;
import com.shopee.model.Usuario;
import com.shopee.util.Logger;

public class PedidoService {
    public PedidoDAO pedidoDAO = new PedidoDAO();
    public UsuarioDAO usuarioDAO = new UsuarioDAO();
    private Logger logger = Logger.getInstance();

    public Pedido criarPedido(Pedido pedido) throws SQLException {
        Optional<Usuario> clienteExiste = usuarioDAO.buscarPorId(pedido.getClienteId());
        if (clienteExiste.isEmpty()) {
            throw new RuntimeException("Cliente para criar pedido não existe");
        }

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
