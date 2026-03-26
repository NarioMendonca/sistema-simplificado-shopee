package com.shopee.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.shopee.model.Produto;

public class CarrinhoController {
    Map<Integer, List<ProdutoCheckout>> carrinhos = new HashMap<>();

    public Optional<List<ProdutoCheckout>> buscarCarrinho(Integer usuarioId) {
        if (carrinhos.containsKey(usuarioId)) {
            return Optional.of(carrinhos.get(usuarioId));
        }
        return Optional.empty();
    }

    public List<ProdutoCheckout> adicionarAoCarrinho(Integer usuarioId, Produto produto) {
        if (!carrinhos.containsKey(usuarioId)) {
            carrinhos.put(usuarioId, new ArrayList<>());
        }
        List<ProdutoCheckout> carrinho = carrinhos.get(usuarioId);
        for (ProdutoCheckout produtoCheckout : carrinho) {
            if (produtoCheckout.getProduto().getId() == produto.getId()) {
                produtoCheckout.adicionarUnidade();
                return carrinho;
            }
        }
        carrinho.add(new ProdutoCheckout(produto, 1));
        carrinhos.put(usuarioId, carrinho);
        return carrinho;
    }

    
    public void removerDoCarrinho(Integer usuarioId, Integer produtoId) {
        List<ProdutoCheckout> carrinho = carrinhos.get(usuarioId);
        for (ProdutoCheckout produtoCheckout : carrinho) {
            if (produtoCheckout.getProduto().getId() == produtoId) {
                carrinho.remove(produtoCheckout);
                return;
            }
        }
    }

    public void apagarCarrinho(Integer usuarioId) {
        carrinhos.remove(usuarioId);
    }

    public Integer countCarrinho(Integer usuarioId) {
        Integer total = 0;
        List<ProdutoCheckout> carrinho = carrinhos.get(usuarioId);
        if (carrinho == null) {
            return 0;
        }
        for (ProdutoCheckout produtoCheckout : carrinho) {
            total += produtoCheckout.quantidade;
        }
        return total;
    }

    public BigDecimal valorTotal(Integer usuarioId) {
        BigDecimal total = new BigDecimal(0);
        List<ProdutoCheckout> carrinho = carrinhos.get(usuarioId);
        if (carrinho == null) {
            return new BigDecimal(0);
        }
        for (ProdutoCheckout produtoCheckout : carrinho) {
            BigDecimal totalProduto = produtoCheckout.getProduto().getPreco()
                .multiply(new BigDecimal(produtoCheckout.getQuantidade()));
            total = total.add(totalProduto);
        }
        return total;
    }

    public ProdutoCheckout buscarProdutoCheckout(Integer usuarioId, Integer produtoId) {
        List<ProdutoCheckout> carrinho = carrinhos.get(usuarioId);
        for (ProdutoCheckout produtoCheckout : carrinho) {
            if (produtoCheckout.getProduto().getId().equals(produtoId)) {
                return produtoCheckout;
            }
        }
        return null;
    }

    public class ProdutoCheckout {
        private Produto produto;
        private Integer quantidade;

        public ProdutoCheckout(Produto produto, Integer quantidade) {
            this.produto = produto;
            this.quantidade = quantidade;
        }

        public BigDecimal calcularTotal() {
            BigDecimal total = this.getProduto().getPreco().multiply(new BigDecimal(this.getQuantidade()));
            return total;
        }

        public Produto getProduto() {
            return produto;
        }

        public Integer getQuantidade() {
            return quantidade;
        }

        public void adicionarUnidade() {
            this.quantidade = quantidade + 1;
        }

        public void removerUnidade() {
            this.quantidade = quantidade - 1;
        }
    }
}
