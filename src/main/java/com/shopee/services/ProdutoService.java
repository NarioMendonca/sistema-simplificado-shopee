package com.shopee.services;

import java.util.List;
import java.util.Optional;

import com.shopee.dao.ProdutoDAO;
import com.shopee.model.Produto;
import com.shopee.util.Logger;

public class ProdutoService {
    private ProdutoDAO produtoDAO = new ProdutoDAO();
    private Logger logger = Logger.getInstance();

    //sugestão: implementar builder de produto futuramente 
    // ainda não está implementado pois não está no escopo dessa trabalho
    public Produto cadastrar(Produto produto) {
        //TODO - verify if a product with same name already exists

        Produto produtoSalvo = produtoDAO.salvar(produto);
        logger.logInfo("Produto cadastrado com sucesso id=" + produtoSalvo.getId());        
        return produtoSalvo;
    }

    public Produto atualizar(Produto produto) {
        Optional<Produto> produtoJaExiste = produtoDAO.buscarPorId(produto.getId());
        if (produtoJaExiste.isEmpty()) {
            throw new RuntimeException("Produto não encontrado");
        }

        produtoDAO.atualizar(produto);
        logger.logInfo("Produto atualizado id=" + produto.getId());
        return produto;
    }

    public List<Produto> buscarTodos() {
        return produtoDAO.buscarTodos();
    }

    public List<Produto> buscarPorVendedor(Integer vendedorId) {
        return produtoDAO.buscarPorVendedorId(vendedorId);
    }

    public Optional<Produto> buscarPorId(Integer produtoId) {
        return produtoDAO.buscarPorId(produtoId);
    }

    public void removerProduto(Integer produtoId, Integer vendedorId) {
        Optional<Produto> produto = produtoDAO.buscarPorId(produtoId);
        if (produto.isEmpty()) {
            throw new RuntimeException("Produto nao encontrado");
        }
        if (!produto.get().getVendedorId().equals(vendedorId)) {
            throw new RuntimeException("Produto nao pertence a este vendedor");
        }
        produtoDAO.deletar(produtoId);
        logger.logInfo("Produto removido id=" + produtoId + " vendedorId=" + vendedorId);
    }
}
