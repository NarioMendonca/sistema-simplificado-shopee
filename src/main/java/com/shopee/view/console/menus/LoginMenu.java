package com.shopee.view.console.menus;

import java.util.Optional;
import java.util.Scanner;

import com.shopee.model.Usuario;
import com.shopee.services.UsuarioService;
import com.shopee.util.Logger;

public class LoginMenu {
    Scanner scanner = new Scanner(System.in);
    UsuarioService usuarioService = new UsuarioService();

    public Optional<Usuario> login() {
        Usuario usuario;
        String email = emailInput();
        String senha = senhaInput();

        try {
            usuario = usuarioService.logar(email, senha);
            return Optional.of(usuario);
        } catch (Exception exception) {
            Logger.getInstance().logWarn("Falha ao realizar login", exception);
            System.out.println("Falha ao realizar login");
            return Optional.empty();
        }
    }
    
    public String emailInput() {
        System.out.println("Insira seu email: ");
        String email = scanner.nextLine();
        return email;

    }

    public String senhaInput() {
        System.out.println("Insira sua senha: ");
        String senha = scanner.nextLine();
        return senha;
    }
}
