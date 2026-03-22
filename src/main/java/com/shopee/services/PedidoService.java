package com.shopee.services;

import java.sql.SQLException;
import java.util.Optional;

import com.shopee.dao.PedidoDAO;
import com.shopee.dao.UsuarioDAO;
import com.shopee.model.Pedido;
import com.shopee.model.Usuario;

public class PedidoService {
    public PedidoDAO pedidoDAO = new PedidoDAO();
    public UsuarioDAO usuarioDAO = new UsuarioDAO();

    public Pedido criarPedido(Pedido pedido) throws SQLException {
        Optional<Usuario> clienteExiste = usuarioDAO.buscarPorId(pedido.getClienteId());
        if (clienteExiste.isEmpty()) {
            throw new RuntimeException("Cliente para criar pedido não existe");
        }

        Pedido pedidoSalvo = pedidoDAO.salvar(pedido);
        return pedidoSalvo;
    }
}
