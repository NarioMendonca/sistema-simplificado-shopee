package com.shopee.view.console.menus;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import com.shopee.model.Pedido;
import com.shopee.model.Produto;
import com.shopee.model.Usuario;
import com.shopee.model.Vendedor;
import com.shopee.services.PedidoService;
import com.shopee.services.ProdutoService;
import com.shopee.services.UsuarioService;
import com.shopee.util.Logger;

public class MenuVendedor {
    private ProdutoService produtoService = new ProdutoService();
    private PedidoService pedidoService = new PedidoService();
    private UsuarioService usuarioService = new UsuarioService();
    private Logger logger = Logger.getInstance();

    
    public void iniciar(Usuario usuarioLogado) {
        Optional<Vendedor> vendedorOptional = usuarioService.buscarVendedor(usuarioLogado.getId());
        if (vendedorOptional.isEmpty()) {
            logger.logWarn("Vendedor nao encontrado ao abrir menu", new RuntimeException("usuarioId=" + usuarioLogado.getId()));
            System.out.println("Vendedor nao encontrado");
            return;
        }

        Vendedor vendedor = vendedorOptional.get();
        int opcao;
        System.out.println("Escolha uma opção");
        do {
            System.out.println("1 - Listar produtos da loja");
            System.out.println("2 - Adicionar produto na minha loja");
            System.out.println("3 - Remover produto da minha loja");
            System.out.println("4 - Ver pedidos da loja");
            System.out.println("5 - Sair");
            opcao = Componentes.inputInteger("Opcao: ");

            try {
                switch (opcao) {
                    case 1:
                        listarProdutos();
                        break;
                    case 2:
                        adicionarProduto(vendedor.getId());
                        break;
                    case 3:
                        removerProduto(vendedor.getId());
                        break;
                    case 4:
                        mostrarPedidos(vendedor.getId());
                        break;
                    case 5:
                        System.out.println("Saindo...");
                        break;
                    default:
                        System.out.println("Opcao invalida!");
                }
            } catch (Exception exception) {
                logger.logError("Falha ao executar o menu de vendedor", exception);
                System.out.println("Falha na operacao: " + exception.getMessage());
            }
        } while (opcao != 5);
    }

    private void listarProdutos() {
        List<Produto> produtos = produtoService.listarProdutos();
        if (produtos.isEmpty()) {
            System.out.println("Nenhum produto encontrado");
            return;
        }

        System.out.println("Produtos cadastrados:");
        for (Produto produto : produtos) {
            System.out.println(
                "Id: " + produto.getId()
                + " | Nome: " + produto.getNome()
                + " | Preco: " + produto.getPreco()
                + " | Estoque: " + produto.getQuantidadeEstoque()
                + " | Id vendedor: " + produto.getVendedorId()
            );
        }
    }

    private void adicionarProduto(Integer vendedorId) {
        String nome = Componentes.inputString("Nome do produto: ");
        String descricao = Componentes.inputString("Descricao: ");
        String categoria = Componentes.inputString("Categoria: ");
        BigDecimal preco = Componentes.inputBigDecimal("Preco: ");
        Integer quantidadeEstoque = Componentes.inputInteger("Quantidade em estoque: ");

        Produto produto = new Produto(
            null,
            vendedorId,
            nome,
            descricao,
            categoria,
            preco,
            quantidadeEstoque,
            null,
            null,
            true
        );

        Produto produtoSalvo = produtoService.cadastrar(produto);
        System.out.println("Produto cadastrado! id: " + produtoSalvo.getId());
    }

    private void removerProduto(Integer vendedorId) {
        int produtoId = Componentes.inputInteger("Id do produto a ser removido: ");
        produtoService.removerProduto(produtoId, vendedorId);
        System.out.println("Produto de id" + produtoId + "removido");
    }

    private void mostrarPedidos(Integer vendedorId) {
        List<Pedido> pedidos = pedidoService.listarPedidosVendedor(vendedorId);
        if (pedidos.isEmpty()) {
            System.out.println("Nenhum pedido encontrado");
            return;
        }

        System.out.println("Pedidos: ");
        for (Pedido pedido : pedidos) {
            System.out.println(
                "PedidoId: " + pedido.getId()
                + " | ClienteID: " + pedido.getClienteId()
                + " | Status: " + pedido.getStatus()
                + " | Total: " + pedido.getValorTotal()
                + " | Data: " + pedido.getDataPedido()
            );
        }
    }
}
