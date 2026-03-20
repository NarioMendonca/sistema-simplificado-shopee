package com.shopee.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public class Produto {
    int id;
    int vendedorId;
    String nome;
    String descricao;
    String categoria;
    BigDecimal preco;
    int quantidadeEstoque;
    String imagemUrl;
    OffsetDateTime dataCadastro;
    boolean ativo;

    public Produto(
        int id,
        int vendedorId,
        String nome,
        String descricao,
        String categoria,
        BigDecimal preco,
        int quantidadeEstoque,
        String imagemUrl,
        OffsetDateTime dataCadastro,
        boolean ativo
    ) {
        setId(id);
        setVendedorId(vendedorId);
        setNome(nome);
        setDescricao(descricao);
        setCategoria(categoria);
        setPreco(preco);
        setQuantidadeEstoque(quantidadeEstoque);
        setImagemUrl(imagemUrl);
        setDataCadastro(dataCadastro);
        setAtivo(ativo);
    }

    public int getId() {
        return id;
    }

    public int getVendedorId() {
        return vendedorId;
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getCategoria() {
        return categoria;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public int getQuantidadeEstoque() {
        return quantidadeEstoque;
    }


    public String getImagemUrl() {
        return imagemUrl;
    }

    public OffsetDateTime getDataCadastro() {
        return dataCadastro;
    }

    public boolean getAtivo() {
        return ativo;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setVendedorId(int vendedorId) {
        this.vendedorId = vendedorId;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }

    public void setQuantidadeEstoque(int quantidadeEstoque) {
        this.quantidadeEstoque = quantidadeEstoque;
    }

    public void setImagemUrl(String imagemUrl) {
        this.imagemUrl = imagemUrl;
    }

    public void setDataCadastro(OffsetDateTime dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }
}
