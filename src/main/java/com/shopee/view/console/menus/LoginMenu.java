package com.shopee.view.console.menus;

import java.util.Optional;

import com.shopee.model.Usuario;
import com.shopee.services.UsuarioService;
import com.shopee.util.Logger;

public class LoginMenu {
    UsuarioService usuarioService = new UsuarioService();

    public Optional<Usuario> login() {
        Usuario usuario;
        String email = Componentes.inputString("Digite seu email: ");
        String senha = Componentes.inputString("Digite sua senha: ");

        try {
            usuario = usuarioService.logar(email, senha);
            return Optional.of(usuario);
        } catch (Exception exception) {
            Logger.getInstance().logWarn("Falha ao realizar login", exception);
            System.out.println("Falha ao realizar login");
            return Optional.empty();
        }
    }
}
