package com.shopee.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public class Produto {
    Integer id;
    Integer vendedorId;
    String nome;
    String descricao;
    String categoria;
    BigDecimal preco;
    Integer quantidadeEstoque;
    String imagemUrl;
    OffsetDateTime dataCadastro;
    Boolean ativo;

    public Produto(
        Integer id,
        Integer vendedorId,
        String nome,
        String descricao,
        String categoria,
        BigDecimal preco,
        Integer quantidadeEstoque,
        String imagemUrl,
        OffsetDateTime dataCadastro,
        Boolean ativo
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

    public Integer getId() {
        return id;
    }

    public Integer getVendedorId() {
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

    public Integer getQuantidadeEstoque() {
        return quantidadeEstoque;
    }


    public String getImagemUrl() {
        return imagemUrl;
    }

    public OffsetDateTime getDataCadastro() {
        return dataCadastro;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setVendedorId(Integer vendedorId) {
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

    public void setQuantidadeEstoque(Integer quantidadeEstoque) {
        this.quantidadeEstoque = quantidadeEstoque;
    }

    public void setImagemUrl(String imagemUrl) {
        this.imagemUrl = imagemUrl;
    }

    public void setDataCadastro(OffsetDateTime dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }
}
