package com.shopee.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.shopee.model.Produto;
import com.shopee.util.DatabaseConnection;

public class ProdutoDAO implements DAO<Produto> {
    private Connection connection = DatabaseConnection.getInstance().getConnection();

    private Produto mapper(ResultSet rs) {
        try {
            return new Produto(
                rs.getInt("id"),
                rs.getInt("vendedor_id"),
                rs.getString("nome"),
                rs.getString("descricao"),
                rs.getString("categoria"),
                rs.getBigDecimal("preco"),
                rs.getInt("quantidade_estoque"),
                rs.getString("imagem_url"),
                rs.getObject("data_cadastro", OffsetDateTime.class),
                rs.getBoolean("ativo")
            );
        } catch (SQLException sqlException) {
            throw new RuntimeException("Erro ao mapear dados para entidade produto", sqlException);
        }
    }

    @Override
    public Produto salvar(Produto produto) {
        String sql = """
        INSERT INTO produto (
            vendedor_id,
            nome,
            descricao,
            categoria,
            preco,
            quantidade_estoque,
            imagem_url,
            ativo
        ) 
        VALUES (?, ?, ?, ?, ?, ?, ?, ?) RETURNING id, data_cadastro
        """;;
        try (PreparedStatement pstm = connection.prepareStatement(sql)) {
            pstm.setInt(1, produto.getVendedorId());
            pstm.setString(2, produto.getNome());
            pstm.setString(3, produto.getDescricao());
            pstm.setString(4, produto.getCategoria());
            pstm.setBigDecimal(5, produto.getPreco());
            pstm.setInt(6, produto.getQuantidadeEstoque());
            pstm.setString(7, produto.getImagemUrl());
            pstm.setBoolean(8, produto.getAtivo());

            ResultSet resultSet = pstm.executeQuery();
            if (resultSet.next()) {
                produto.setId(resultSet.getInt("id"));
                produto.setDataCadastro(resultSet.getObject("data_cadastro", OffsetDateTime.class));
            }
            return produto;
        } catch (SQLException sqlException) {
            throw new RuntimeException("Erro ao inserir em produto", sqlException);
        }
    }

    @Override
    public Optional<Produto> buscarPorId(int id) {
        String sql = "SELECT * FROM produto WHERE id = ?";
        try (PreparedStatement pstm = connection.prepareStatement(sql)) {
            pstm.setInt(1, id);
            ResultSet rs = pstm.executeQuery();
            if (rs.next()) {
                Produto produto = mapper(rs);
                return Optional.of(produto);
            }
            return Optional.empty();
        } catch (SQLException sqlException) {
            throw new RuntimeException("Erro ao buscar em produto id=" + id, sqlException);
        }
    }

    @Override
    public List<Produto> buscarTodos() {
        List<Produto> produtos = new ArrayList<>();
        String sql = "SELECT * FROM produto";
        try (PreparedStatement pstm = connection.prepareStatement(sql)) {
            ResultSet resultSet = pstm.executeQuery();

            while (resultSet.next()) {
                produtos.add(mapper(resultSet));
            }
            return produtos;
        } catch (SQLException sqlException) {
            throw new RuntimeException("Erro ao buscar dados de produto", sqlException);
        }
        
    }

    public List<Produto> buscarPorVendedorId(int vendedorId) {
        List<Produto> produtos = new ArrayList<>();
        String sql = "SELECT * FROM produto WHERE vendedor_id = ? ORDER BY data_cadastro DESC";
        try (PreparedStatement pstm = connection.prepareStatement(sql)) {
            pstm.setInt(1, vendedorId);
            ResultSet resultSet = pstm.executeQuery();
            while (resultSet.next()) {
                produtos.add(mapper(resultSet));
            }
            return produtos;
        } catch (SQLException sqlException) {
            throw new RuntimeException("Erro ao buscar produtos do vendedor id=" + vendedorId, sqlException);
        }
    }

    @Override
    public void atualizar(Produto produto) {
        String sql = """
            UPDATE produto SET 
                nome = ?, 
                descricao = ?, 
                categoria = ?,
                preco = ?,
                quantidade_estoque = ?,
                imagem_url = ?,
                ativo = ? 
            WHERE id = ?
        """;

        try(PreparedStatement pstm = connection.prepareStatement(sql)) {
            pstm.setString(1, produto.getNome());
            pstm.setString(2, produto.getDescricao());
            pstm.setString(3, produto.getCategoria());
            pstm.setBigDecimal(4, produto.getPreco());
            pstm.setInt(5, produto.getQuantidadeEstoque());
            pstm.setString(6, produto.getImagemUrl());
            pstm.setBoolean(7, produto.getAtivo());
            pstm.setInt(8, produto.getId());
            pstm.executeUpdate();
        } catch (SQLException sqlException) {
            throw new RuntimeException("Erro ao ao atualizar produto id=" + produto.getId(), sqlException);
        };
    }

    @Override
    public void deletar(int id) {
        String sql = "DELETE FROM produto WHERE id = ?";
        try (PreparedStatement pstm = connection.prepareStatement(sql);) {
            pstm.setInt(1, id);
            pstm.executeUpdate();
        } catch (SQLException sqlException) {
            throw new RuntimeException("Erro ao deletar produto id=" + id, sqlException);
        }
    }

    @Override
    public long contar() {
        String sql = "SELECT COUNT(*) as contagem_produtos FROM produto";
        try (PreparedStatement pstm = connection.prepareStatement(sql)) {
            ResultSet resultSet = pstm.executeQuery();
            resultSet.next();
            long contagemProdutos = resultSet.getLong("contagem_produtos");
            return contagemProdutos;
        } catch (SQLException sqlException) {
            throw new RuntimeException("Erro ao contar tabela produto", sqlException);
        }
    }
    
}
