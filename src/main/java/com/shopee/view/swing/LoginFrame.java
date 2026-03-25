package com.shopee.view.swing;

import java.awt.*;
import java.util.Optional;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.shopee.controller.LoginController;
import com.shopee.model.Usuario;

public class LoginFrame extends JFrame {
    private LoginController loginController = new LoginController();

    public LoginFrame() {
        setTitle("Login");
        setSize(1280, 720);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel header = new JPanel();
        JLabel titulo = new JLabel("SHOPEE - LOGIN");
        titulo.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 48));
        header.add(titulo);

        JPanel painelPrincipal = new JPanel();
        painelPrincipal.setLayout(new GridLayout(6, 1));
        painelPrincipal.setBorder(BorderFactory.createEmptyBorder(100, 100, 100, 100));
        painelPrincipal.add(header);

        JLabel errorMessage = new JLabel();
        errorMessage.setForeground(Color.RED);
        painelPrincipal.add(errorMessage);

        JPanel wrapperEmail = new JPanel();
        wrapperEmail.setLayout(new GridLayout(2, 1));
        JTextField campoEmail = new JTextField();
        wrapperEmail.add(new JLabel("Email:"));
        wrapperEmail.add(campoEmail);
        painelPrincipal.add(wrapperEmail);

        JPanel wrapperSenha = new JPanel();
        wrapperSenha.setLayout(new GridLayout(2, 1));
        JPasswordField campoSenha = new JPasswordField();
        wrapperSenha.add(new JLabel("Senha:"));
        wrapperSenha.add(campoSenha);
        painelPrincipal.add(wrapperSenha);

        JPanel wrapperBotao = new JPanel();
        wrapperBotao.setLayout(new GridLayout(1, 1));
        JButton botaoLogin = new JButton("Entrar");
        wrapperBotao.add(botaoLogin);
        painelPrincipal.add(wrapperBotao);

        JPanel wrapperCadastrarBotao = new JPanel();
        wrapperBotao.setLayout(new GridLayout(2, 1));
        JLabel cadastrarTexto = new JLabel("não possui uma conta? cadastre-se!");
        JButton cadastrarBotao = new JButton("cadastrar");
        wrapperCadastrarBotao.add(cadastrarTexto);
        wrapperCadastrarBotao.add(cadastrarBotao);
        painelPrincipal.add(wrapperCadastrarBotao);

        add(painelPrincipal);

        botaoLogin.addActionListener(e -> {
            Optional<Usuario> usuario = loginController.logar(campoEmail.getText(), campoSenha.getText());
            if (usuario.isEmpty()) {
                errorMessage.setText("Credenciais inválidas - conta não encontrada");
            } else {
                new MainFrame(usuario.get());
                dispose();
            }
        });
    }
}
