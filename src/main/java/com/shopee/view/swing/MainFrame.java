package com.shopee.view.swing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.shopee.controller.CarrinhoController;
import com.shopee.controller.CarrinhoController.ProdutoCheckout;
import com.shopee.dao.ProdutoDAO;
import com.shopee.model.Produto;
import com.shopee.model.Usuario;

public class MainFrame extends JFrame {
    private CarrinhoController carrinhoController = new CarrinhoController();
    private Usuario usuario;
    private JPanel carrinhoPanel;
    private JLabel itensCarrinhoCount;
    private JLabel valorTotalCarrinho;

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
        ProdutoDAO produtoDAO = new ProdutoDAO();
        JPanel produtosPanel = new JPanel();
        produtosPanel.setLayout(new BoxLayout(produtosPanel, BoxLayout.Y_AXIS));


        List<Produto> produtos = produtoDAO.buscarTodos();
        for (Produto produto : produtos) {
            produtosPanel.add(cardProduto(produto));
        }

        JScrollPane produtosScroll = new JScrollPane(produtosPanel);
        produtosScroll.getVerticalScrollBar().setUnitIncrement(2);

        return produtosScroll;
    }

    private JPanel cardProduto(Produto produto) {
        JPanel card = new JPanel();
        card.setLayout(new BorderLayout());
        card.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel nomeLabel = new JLabel(produto.getNome());
        nomeLabel.setFont(new Font("Arial", Font.PLAIN, 16));

        JLabel precoLabel = new JLabel("R$ " + produto.getPreco().toString());
        precoLabel.setFont(new Font("Arial", Font.BOLD, 16));
        precoLabel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));

        JLabel avisoEstoque = new JLabel("fora de estoque");
        avisoEstoque.setFont(new Font("Arial", Font.BOLD, 16));
        avisoEstoque.setForeground(Color.RED);

        JButton botaoComprar = new JButton("Comprar");
        botaoComprar.addActionListener(e -> {
            carrinhoController.adicionarAoCarrinho(usuario.getId(), produto);
            itensCarrinhoCount.setText("Quantidade de itens: " + carrinhoController.countCarrinho(usuario.getId()));
            valorTotalCarrinho.setText("Valor total: R$ " + carrinhoController.valorTotal(usuario.getId()).toString());
        });

        card.add(nomeLabel, BorderLayout.WEST);
        card.add(precoLabel, BorderLayout.CENTER);
        card.add(botaoComprar, BorderLayout.EAST);
        if (produto.getQuantidadeEstoque().equals(0)) {
            card.add(avisoEstoque, BorderLayout.EAST);
        }

        return card;
    }

    private JPanel carrinho() {
        JPanel carrinho = new JPanel();
        carrinho.setLayout(new BorderLayout());
        carrinho.setPreferredSize(new Dimension(0, 60));
        carrinho.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        JPanel resumoCarrinho = new JPanel();
        resumoCarrinho.setLayout(new GridLayout(1, 3));
        resumoCarrinho.setOpaque(false);

        JLabel valorTotal = new JLabel("Valor total: RS$ 0");
        valorTotal.setFont(new Font("Arial", Font.BOLD, 20));
        valorTotalCarrinho = valorTotal;

        JLabel itensDoCarrinho = new JLabel("Quantidade de itens: 0");
        itensDoCarrinho.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));
        itensDoCarrinho.setFont(new Font("Arial", Font.PLAIN, 20));
        itensCarrinhoCount = itensDoCarrinho;

        resumoCarrinho.add(valorTotal);
        resumoCarrinho.add(itensDoCarrinho);

        JButton botaoFinalizar = new JButton("Finalizar");
        botaoFinalizar.setPreferredSize(new Dimension(120, 20));

        carrinho.add(resumoCarrinho, BorderLayout.WEST);
        carrinho.add(botaoFinalizar, BorderLayout.EAST);

        return carrinho;
    }
}
