package com.shopee.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public class Vendedor extends Usuario {
    Integer id;
    Integer usuarioId;
    String cnpj;
    String razaoSocial;
    String telefone;
    BigDecimal avaliacao;

    public Vendedor() {}

    public Vendedor(
        Integer id,
        Integer usuarioId,
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
    public Integer getId() {
        return this.id;
    }

    public Integer getUsuarioId() {
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
    public void setId(Integer id) {
        this.id = id;
    }

    public void setUsuarioId(Integer usuarioId) {
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
