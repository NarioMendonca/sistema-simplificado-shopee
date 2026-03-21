package com.shopee.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.shopee.model.Pedido;
import com.shopee.util.DatabaseConnection;

public class PedidoDAO implements DAO<Pedido> {
    private Connection connection = DatabaseConnection.getInstance().getConnection();

    private Pedido mapper(ResultSet rs) throws SQLException {
        return new Pedido(
            rs.getInt("id"),
            rs.getInt("cliente_id"),
            rs.getObject("data_pedido", OffsetDateTime.class),
            Pedido.Status.valueOf(rs.getString("status")),
            rs.getBigDecimal("valor_total"),
            rs.getString("metodo_pagamento"),
            rs.getString("endereco_entrega")
        );
    }

    @Override
    public Pedido salvar(Pedido pedido) throws SQLException {
        String sql = """
        INSERT INTO pedido (
            cliente_id,
            status,
            valor_total,
            metodo_pagamento,
            endereco_entrega,
        ) 
        VALUES (?, ?, ?, ?, ?) RETURNING id, data_pedido
        """;
        try (PreparedStatement pstm = connection.prepareStatement(sql)) {
            pstm.setInt(1, pedido.getClienteId());
            pstm.setObject(2, pedido.getStatus(), Types.OTHER);
            pstm.setBigDecimal(3, pedido.getValorTotal());
            pstm.setString(4, pedido.getMetodoPagamento());
            pstm.setString(5, pedido.getEnderecoEntrega());

            ResultSet resultSet = pstm.executeQuery();
            if (resultSet.next()) {
                pedido.setId(resultSet.getInt("id"));
                pedido.setDataPedido(resultSet.getObject("data_pedido", OffsetDateTime.class));
            }
            return pedido;
        }
    }

    @Override
    public Optional<Pedido> buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM pedido WHERE id = ?";
        try (PreparedStatement pstm = connection.prepareStatement(sql)) {
            pstm.setInt(1, id);
            ResultSet rs = pstm.executeQuery();
            if (rs.next()) {
                Pedido pedido = mapper(rs);
                return Optional.of(pedido);
            }
            
        };
        return Optional.empty();
    }

    @Override
    public List<Pedido> buscarTodos() throws SQLException {
        List<Pedido> pedidos = new ArrayList<>();
        String sql = "SELECT * FROM pedido";
        try (PreparedStatement pstm = connection.prepareStatement(sql)) {
            ResultSet resultSet = pstm.executeQuery();

            while (resultSet.next()) {
                pedidos.add(mapper(resultSet));
            }
        };
        
        return pedidos;
    }

    // id
    // cliente_id
    // data_pedido
    // status
    // valor_total
    // metodo_pagamento
    // endereco_entrega

    @Override
    public void atualizar(Pedido pedido) throws SQLException {
        String sql = """
            UPDATE pedido SET 
                status = ?, 
                metodo_pagamento = ?, 
                endereco_entrega = ?,
            WHERE id = ?
        """;
        try (PreparedStatement pstm = connection.prepareStatement(sql)) {
            pstm.setObject(1, pedido.getStatus(), Types.OTHER);
            pstm.setString(2, pedido.getMetodoPagamento());
            pstm.setString(3, pedido.getEnderecoEntrega());
            pstm.executeUpdate();
        }
    }

    @Override
    public void deletar(int id) throws SQLException {
        String sql = "DELETE FROM pedido WHERE id = ?";
        try (PreparedStatement pstm = connection.prepareStatement(sql);) {
            pstm.setInt(1, id);
            pstm.executeUpdate();
        }
    }

    @Override
    public long contar() throws SQLException {
        String sql = "SELECT COUNT(*) as contagem_pedidos FROM pedido";
        try (PreparedStatement pstm = connection.prepareStatement(sql)) {
            ResultSet resultSet = pstm.executeQuery();
            long contagemPedidos = resultSet.getLong("contagem_pedidos");
            return contagemPedidos;
        }
    }
    
}
