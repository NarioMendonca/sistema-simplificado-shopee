package com.shopee.model;

import java.time.OffsetDateTime;

public class Usuario {
    
    private int id;
    private String nome;
    private String email;
    private String senha;
    private TipoUsuario tipo;
    private OffsetDateTime dataCadastro;
    private boolean ativo;

    public enum TipoUsuario {
        cliente,
        vendedor
    }

    public Usuario() {}

    public Usuario(
        Integer id,
        String nome,
        String email,
        String senha,
        TipoUsuario tipo,
        OffsetDateTime dataCadastro,
        Boolean ativo
    ) {
        setId(id);
        setNome(nome);
        setEmail(email);
        setSenha(senha);
        setTipo(tipo);
        setDataCadastro(dataCadastro);
        setAtivo(ativo);
    }

    public Integer getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public String getSenha() {
        return senha;
    }

    public TipoUsuario getTipo() {
        return tipo;
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

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public void setTipo(TipoUsuario tipo) {
        this.tipo = tipo;
    }

    public void setDataCadastro(OffsetDateTime dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }
}
