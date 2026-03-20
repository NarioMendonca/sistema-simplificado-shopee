package com.shopee.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public class Vendedor extends Usuario {
    int id;
    int usuarioId;
    String cnpj;
    String razaoSocial;
    String telefone;
    BigDecimal avaliacao;

    public Vendedor(
        int id,
        int usuarioId,
        String nome,
        String email,
        String senha,
        TipoUsuario tipo,
        OffsetDateTime dataCadastro,
        boolean ativo,
        String cnpj,
        String razaoSocial,
        String telefone,
        BigDecimal avaliacao
    ) {
        super(usuarioId, nome, email, senha, tipo, dataCadastro, ativo);
        setCnpj(cnpj);
        setRazaoSocial(razaoSocial);
        setTelefone(telefone);
        setAvaliacao(avaliacao);
    }

    @Override
    public int getId() {
        return this.getId();
    }

    public int getUsuarioId() {
        return usuarioId;
    }

    public String getCnpj() {
        return cnpj;
    }

    public String getRazaoSocial() {
        return razaoSocial;
    }

    public String getTelefone() {
        return telefone;
    }

    public BigDecimal getAvaliacao() {
        return avaliacao;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
        super.setId(usuarioId);
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public void setRazaoSocial(String razaoSocial) {
        this.razaoSocial = razaoSocial;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public void setAvaliacao(BigDecimal avaliacao) {
        this.avaliacao = avaliacao;
    }
}
