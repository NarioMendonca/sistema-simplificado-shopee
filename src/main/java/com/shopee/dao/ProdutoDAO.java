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

    private Produto mapper(ResultSet rs) throws SQLException {
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
    }

    @Override
    public Produto salvar(Produto produto) throws SQLException {
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
        }
    }

    @Override
    public Optional<Produto> buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM produto WHERE id = ?";
        try (PreparedStatement pstm = connection.prepareStatement(sql)) {
            pstm.setInt(1, id);
            ResultSet rs = pstm.executeQuery();
            if (rs.next()) {
                Produto produto = mapper(rs);
                return Optional.of(produto);
            }
            
        };
        return Optional.empty();
    }

    @Override
    public List<Produto> buscarTodos() throws SQLException {
        List<Produto> produtos = new ArrayList<>();
        String sql = "SELECT * FROM produto";
        try (PreparedStatement pstm = connection.prepareStatement(sql)) {
            ResultSet resultSet = pstm.executeQuery();

            while (resultSet.next()) {
                produtos.add(mapper(resultSet));
            }
        };
        
        return produtos;
    }

    @Override
    public void atualizar(Produto produto) throws SQLException {
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
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, produto.getNome());
        pstm.setString(2, produto.getDescricao());
        pstm.setString(3, produto.getCategoria());
        pstm.setBigDecimal(4, produto.getPreco());
        pstm.setInt(5, produto.getQuantidadeEstoque());
        pstm.setString(6, produto.getImagemUrl());
        pstm.setBoolean(7, produto.getAtivo());
        pstm.setInt(8, produto.getId());
        pstm.executeUpdate();
    }

    @Override
    public void deletar(int id) throws SQLException {
        String sql = "DELETE FROM produto WHERE id = ?";
        try (PreparedStatement pstm = connection.prepareStatement(sql);) {
            pstm.setInt(1, id);
            pstm.executeUpdate();
        }
    }

    @Override
    public long contar() throws SQLException {
        String sql = "SELECT COUNT(*) as contagem_produtos FROM produto";
        try (PreparedStatement pstm = connection.prepareStatement(sql)) {
            ResultSet resultSet = pstm.executeQuery();
            resultSet.next();
            long contagemProdutos = resultSet.getLong("contagem_produtos");
            return contagemProdutos;
        }
    }
    
}
