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

import com.shopee.model.ItemPedido;
import com.shopee.model.Pedido;
import com.shopee.util.DatabaseConnection;
import com.shopee.util.Logger;

public class PedidoDAO implements DAO<Pedido> {
    private Connection connection = DatabaseConnection.getInstance().getConnection();

    private Pedido mapper(ResultSet rs) {
        try {
            return new Pedido(
                rs.getInt("id"),
                rs.getInt("cliente_id"),
                rs.getObject("data_pedido", OffsetDateTime.class),
                Pedido.Status.valueOf(rs.getString("status")),
                rs.getBigDecimal("valor_total"),
                rs.getString("metodo_pagamento"),
                rs.getString("endereco_entrega"),
                new ArrayList<ItemPedido>()
            );
        } catch (SQLException sqlException) {
            throw new RuntimeException("Erro ao mapear dados para entidade pedido", sqlException);
        }
    }

    @Override
    public Pedido salvar(Pedido pedido) {
        String sql = """
        INSERT INTO pedido (
            cliente_id,
            status,
            valor_total,
            metodo_pagamento,
            endereco_entrega
        ) 
        VALUES (?, ?, ?, ?, ?) RETURNING id, data_pedido;
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

            // Isso não é uma boa prática, o ideal é utilizar transactions para garantir que 
            // um pedido não seja inserido sem seu item, 
            // além de gerar um só sql para inserir tudo dinamicamente sem precisar fazer várias requisiões ao banco
            // está assim apenas por conta da urgencia
            // precisa refatorar!!
            for (ItemPedido itemPedido : pedido.getItensPedidos()) {
                String itemPedidoSql = """
                INSERT INTO item_pedido (
                    pedido_id,
                    produto_id,
                    quantidade,
                    preco_unitario
                )  VALUES (?, ?, ?, ?) RETURNING id;
                """;;
                try (PreparedStatement pstmItens = connection.prepareStatement(itemPedidoSql)) {
                    pstmItens.setInt(1, pedido.getId());
                    pstmItens.setInt(2, itemPedido.getProdutoId());
                    pstmItens.setInt(3, itemPedido.getQuantidade());
                    pstmItens.setBigDecimal(4, itemPedido.getPrecoUnitario());

                    ResultSet resultSetItens = pstmItens.executeQuery();
                    if (resultSet.next()) {
                        itemPedido.setId(resultSetItens.getInt("id"));
                    }
                }

                String atualizarProdutoSql = "UPDATE produto SET quantidade_estoque = quantidade_estoque - ? WHERE id = ?;";
                try (PreparedStatement pstmProduto = connection.prepareStatement(atualizarProdutoSql)) {
                    pstmProduto.setInt(1, itemPedido.getQuantidade());
                    pstmProduto.setInt(2, itemPedido.getProdutoId());
                    pstmProduto.executeUpdate();
                }
            }

            return pedido;
        } catch (SQLException sqlException) {
            Logger.getInstance().logDebug("Erro ao inserir", sqlException);
            throw new RuntimeException("Erro ao inserir pedido id=" + pedido.getId(), sqlException);
        }
    }

    @Override
    public Optional<Pedido> buscarPorId(int id) {
        String sql = "SELECT * FROM pedido WHERE id = ?";
        try (PreparedStatement pstm = connection.prepareStatement(sql)) {
            pstm.setInt(1, id);
            ResultSet rs = pstm.executeQuery();
            if (rs.next()) {
                Pedido pedido = mapper(rs);
                return Optional.of(pedido);
            }
            return Optional.empty();
        } catch (SQLException sqlException) {
            throw new RuntimeException("Erro ao buscar pedido id=" + id, sqlException);
        }
    }

    @Override
    public List<Pedido> buscarTodos() {
        List<Pedido> pedidos = new ArrayList<>();
        String sql = "SELECT * FROM pedido";
        try (PreparedStatement pstm = connection.prepareStatement(sql)) {
            ResultSet resultSet = pstm.executeQuery();

            while (resultSet.next()) {
                pedidos.add(mapper(resultSet));
            }
            return pedidos;
        } catch (SQLException sqlException) {
            throw new RuntimeException("Erro ao buscar dados da tabela pedido", sqlException);
        }
    }

    public List<Pedido> buscarPorClienteId(int clienteId) {
        List<Pedido> pedidos = new ArrayList<>();
        String sql = "SELECT * FROM pedido WHERE cliente_id = ? ORDER BY data_pedido DESC";
        try (PreparedStatement pstm = connection.prepareStatement(sql)) {
            pstm.setInt(1, clienteId);
            ResultSet resultSet = pstm.executeQuery();
            while (resultSet.next()) {
                pedidos.add(mapper(resultSet));
            }
            return pedidos;
        } catch (SQLException sqlException) {
            throw new RuntimeException("Erro ao buscar pedidos do cliente id=" + clienteId, sqlException);
        }
    }

    public List<Pedido> buscarPorVendedorId(int vendedorId) {
        List<Pedido> pedidos = new ArrayList<>();
        String sql = """
            SELECT DISTINCT p.*
            FROM pedido p
            INNER JOIN item_pedido ip ON ip.pedido_id = p.id
            INNER JOIN produto pr ON pr.id = ip.produto_id
            WHERE pr.vendedor_id = ?
            ORDER BY p.data_pedido DESC
        """;

        try (PreparedStatement pstm = connection.prepareStatement(sql)) {
            pstm.setInt(1, vendedorId);
            ResultSet resultSet = pstm.executeQuery();
            while (resultSet.next()) {
                pedidos.add(mapper(resultSet));
            }
            return pedidos;
        } catch (SQLException sqlException) {
            throw new RuntimeException("Erro ao buscar pedidos do vendedor id=" + vendedorId, sqlException);
        }
    }

    @Override
    public void atualizar(Pedido pedido) {
        String sql = """
            UPDATE pedido SET 
                status = ?, 
                metodo_pagamento = ?, 
                endereco_entrega = ?
            WHERE id = ?
        """;
        try (PreparedStatement pstm = connection.prepareStatement(sql)) {
            pstm.setObject(1, pedido.getStatus(), Types.OTHER);
            pstm.setString(2, pedido.getMetodoPagamento());
            pstm.setString(3, pedido.getEnderecoEntrega());
            pstm.setInt(4, pedido.getId());
            pstm.executeUpdate();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            throw new RuntimeException("Erro ao atualizar dados de pedido id=" + pedido.getId(), sqlException);
        }
    }

    @Override
    public void deletar(int id) {
        String sql = "DELETE FROM pedido WHERE id = ?";
        try (PreparedStatement pstm = connection.prepareStatement(sql);) {
            pstm.setInt(1, id);
            pstm.executeUpdate();
        }  catch (SQLException sqlException) {
            throw new RuntimeException("Erro ao deletar pedido id=" + id, sqlException);
        }
    }

    @Override
    public long contar() {
        String sql = "SELECT COUNT(*) as contagem_pedidos FROM pedido";
        try (PreparedStatement pstm = connection.prepareStatement(sql)) {
            ResultSet resultSet = pstm.executeQuery();
            long contagemPedidos = resultSet.getLong("contagem_pedidos");
            return contagemPedidos;
        }  catch (SQLException sqlException) {
            throw new RuntimeException("Erro ao realizar count da tabela pedido", sqlException);
        }
    }
    
}
