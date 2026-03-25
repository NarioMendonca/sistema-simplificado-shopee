package com.shopee.pattern.factory;

import java.time.OffsetDateTime;

import com.shopee.model.Cliente;
import com.shopee.model.Usuario.TipoUsuario;
import com.shopee.model.Vendedor;

public class UsuarioFactory {
    public static Cliente criarCliente(
        int usuarioId,
        String nome,
        String email,
        String senha,
        TipoUsuario tipo,
        OffsetDateTime dataCadastro,
        boolean ativo
    ) {
        return new Cliente
        (
            null, 
            usuarioId, 
            nome, 
            email, 
            senha, 
            TipoUsuario.cliente, 
            dataCadastro, 
            ativo, 
            "", 
            "", 
            null, 
            ""
        );
    }

    public static Vendedor criarVendedor(
        int usuarioId,
        String nome,
        String email,
        String senha,
        TipoUsuario tipo,
        OffsetDateTime dataCadastro,
        boolean ativo
    ) {
        return new Vendedor(
            null, 
            usuarioId, 
            nome, 
            email, 
            senha, 
            TipoUsuario.cliente, 
            dataCadastro, 
            ativo, 
            "", 
            "", 
            "", 
            null
        );
    }
}
