package com.shopee.services;

import java.sql.SQLException;
import java.util.Optional;

import com.shopee.dao.ProdutoDAO;
import com.shopee.model.Produto;

public class ProdutoService {
    private ProdutoDAO produtoDAO = new ProdutoDAO();

    //sugestão: implementar builder de produto futuramente 
    // ainda não está implementado pois não está no escopo dessa trabalho
    public Produto cadastrar(Produto produto) throws SQLException {
        //TODO - verify if a product with same name already exists

        Produto produtoSalvo = produtoDAO.salvar(produto);
        return produtoSalvo;
    }

    public Produto atualizar(Produto produto) throws SQLException {
        Optional<Produto> produtoJaExiste = produtoDAO.buscarPorId(produto.getId());
        if (produtoJaExiste.isEmpty()) {
            throw new RuntimeException("Produto não encontrado");
        }

        produtoDAO.atualizar(produto);
        return produto;
    }
}
