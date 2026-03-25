package com.shopee.view.swing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.concurrent.Flow;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.shopee.model.Usuario;

public class MainFrame extends JFrame {
    private Usuario usuario;
    private JPanel carrinhoPanel;

    public MainFrame(Usuario usuario) {
        this.usuario = usuario;

        setTitle("Home");
        setSize(1280, 720);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        setLayout(new BorderLayout());

        this.carrinhoPanel = carrinho();
        add(header(), BorderLayout.NORTH);
        add(main(), BorderLayout.CENTER);
        add(carrinhoPanel, BorderLayout.SOUTH);
        carrinhoPanel.setVisible(true);
    }

    public JPanel header() {
        JPanel headerPanel = new JPanel();

        headerPanel.setLayout(new BorderLayout());
        headerPanel.setPreferredSize(new Dimension(0, 60));
        headerPanel.setBackground(Color.ORANGE);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));

        JLabel headerTitle = new JLabel("Shopee");
        headerTitle.setFont(new Font("Arial", Font.BOLD, 20));

        JPanel userAccountPanel = new JPanel();
        userAccountPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 15, 15));
        userAccountPanel.setOpaque(false);
        JLabel headerAccount = new JLabel("Logado como: " + this.usuario.getNome() + " - " + this.usuario.getTipo().toString());
        JButton deslogarButton = new JButton("Sair");
        userAccountPanel.add(headerAccount);
        userAccountPanel.add(deslogarButton);

        headerPanel.add(headerTitle, BorderLayout.WEST);
        headerPanel.add(userAccountPanel, BorderLayout.EAST);

        deslogarButton.addActionListener(e -> {
            LoginFrame loginFrame = new LoginFrame();
            dispose();
            loginFrame.setVisible(true);
        });

        return headerPanel;
    }

    public JScrollPane main() {
        JPanel produtosPanel = new JPanel();
        produtosPanel.setLayout(new BoxLayout(produtosPanel, BoxLayout.Y_AXIS));

        for (int i = 1; i <= 15; i++) {
            produtosPanel.add(cardProduto("Produto " + i, " R$ " + (i * 15)));
        }

        JScrollPane produtosScroll = new JScrollPane(produtosPanel);
        produtosScroll.getVerticalScrollBar().setUnitIncrement(2);

        return produtosScroll;
    }

    private JPanel cardProduto(String nome, String preco) {
        JPanel card = new JPanel();
        card.setLayout(new BorderLayout());
        card.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel nomeLabel = new JLabel(nome);
        JLabel precoLabel = new JLabel(preco);

        JButton comprar = new JButton("Comprar");

        card.add(nomeLabel, BorderLayout.WEST);
        card.add(precoLabel, BorderLayout.CENTER);
        card.add(comprar, BorderLayout.EAST);

        return card;
    }

    private JPanel carrinho() {
        JPanel carrinho = new JPanel();
        carrinho.setLayout(new BorderLayout());
        carrinho.setPreferredSize(new Dimension(0, 60));
        carrinho.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        JPanel resumoCarrinho = new JPanel();
        resumoCarrinho.setLayout(new GridLayout(1, 2));
        resumoCarrinho.setOpaque(false);
        JLabel valorTotal = new JLabel("RS$ 150.45");
        JLabel itensDoCarrinho = new JLabel("Quantidade de itens: 3");
        resumoCarrinho.add(valorTotal);
        resumoCarrinho.add(itensDoCarrinho);


        JButton botaoFinalizar = new JButton("Finalizar");
        botaoFinalizar.setPreferredSize(new Dimension(120, 20));

        carrinho.add(resumoCarrinho, BorderLayout.WEST);
        carrinho.add(botaoFinalizar, BorderLayout.EAST);

        return carrinho;
    }
}
