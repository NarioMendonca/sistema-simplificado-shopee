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

    public Usuario(
        int id,
        String nome,
        String email,
        String senha,
        TipoUsuario tipo,
        OffsetDateTime dataCadastro,
        boolean ativo
    ) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.tipo = tipo;
        this.dataCadastro = dataCadastro;
        this.ativo = ativo;
    }

     public Usuario(
        String nome,
        String email,
        String senha,
        TipoUsuario tipo
    ) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.tipo = tipo;
    }

    public int getId() {
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

    public boolean getAtivo() {
        return ativo;
    }

    public void setId(int id) {
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

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }
}
