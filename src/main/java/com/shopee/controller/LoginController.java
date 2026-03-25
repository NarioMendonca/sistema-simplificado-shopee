package com.shopee.controller;

import java.util.Optional;

import com.shopee.model.Usuario;
import com.shopee.services.UsuarioService;

public class LoginController {
    UsuarioService usuarioService = new UsuarioService();

    public Optional<Usuario> logar(String email, String senha) {
        try {
            Usuario usuario = usuarioService.logar(email, senha);
            return Optional.of(usuario);
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
