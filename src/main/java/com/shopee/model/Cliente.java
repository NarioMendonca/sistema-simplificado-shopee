package com.shopee.model;

import java.time.OffsetDateTime;

public class Cliente extends Usuario {
    int id;
    int usuarioId;
    String cpf;
    String telefone;
    OffsetDateTime dataNascimento;
    String endereco;

    public Cliente(
        int id,
        int usuarioId,
        String nome,
        String email,
        String senha,
        TipoUsuario tipo,
        OffsetDateTime dataCadastro,
        boolean ativo,
        String cpf,
        String telefone,
        OffsetDateTime dataNascimento,
        String endereco
    ) {
        super(usuarioId, nome, email, senha, tipo, dataCadastro, ativo);
        this.usuarioId = usuarioId;
        this.cpf = cpf;
        this.telefone = telefone;
        this.dataNascimento = dataNascimento;
        this.endereco = endereco;
    }

    @Override
    public int getId() {
        return this.id;
    }

    public int getUsuarioId() {
        return usuarioId;
    }

    public String getCpf() {
        return cpf;
    }

    public String getTelefone() {
        return telefone;
    }

    public OffsetDateTime getDataNascimento() {
        return dataNascimento;
    }

    public String getEndereco() {
        return endereco;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
        super.setId(usuarioId);
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public void setDataNascimento(OffsetDateTime dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }
}
