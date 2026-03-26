package com.shopee.view.console;

import java.util.Optional;
import java.util.Scanner;

import com.shopee.model.Usuario;
import com.shopee.view.console.menus.CadastroMenu;
import com.shopee.view.console.menus.LoginMenu;

public class ConsoleApp {
    Scanner scanner = new Scanner(System.in);
    CadastroMenu cadastroMenu = new CadastroMenu();
    LoginMenu loginMenu = new LoginMenu();
    Usuario usuario;

    public void iniciar() {
        System.out.println("Entre na shopee fazendo login ou se cadastrando");
        
        int opcao;
        Optional<Usuario> usuario = Optional.empty();
        do {
            System.out.println("1 - login (já possui conta)");
            System.out.println("2 - cadastre-se já!");
            System.out.println("3 - Sair");
            System.out.print("Opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    usuario = loginMenu.login();
                    break;
                case 2:
                    System.out.println(opcao);
                    usuario = cadastroMenu.cadastrar();
                    break;
                case 3:
                    continue;
                default:
                    System.out.println("Opção inválida!"); break;
            }

            if (!usuario.isEmpty()) {
                this.usuario = usuario.get();
                System.out.println("Bem vindo ao sistema, " + this.usuario.getNome());
                break;
            }
        } while (opcao != 3);
    }

    public String dadoInput(String text) {
        System.out.println(text);
        return scanner.nextLine();
    }
}

