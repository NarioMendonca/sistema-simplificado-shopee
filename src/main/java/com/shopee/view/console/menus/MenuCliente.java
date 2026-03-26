package com.shopee.view.console.menus;

import java.util.List;
import java.util.Optional;

import com.shopee.controller.CarrinhoController;
import com.shopee.controller.CarrinhoController.ProdutoCheckout;
import com.shopee.model.Cliente;
import com.shopee.model.Pedido;
import com.shopee.model.Produto;
import com.shopee.model.Usuario;
import com.shopee.services.PedidoService;
import com.shopee.services.ProdutoService;
import com.shopee.services.UsuarioService;
import com.shopee.util.Logger;

public class MenuCliente {
    private ProdutoService produtoService = new ProdutoService();
    private PedidoService pedidoService = new PedidoService();
    private UsuarioService usuarioService = new UsuarioService();
    private CarrinhoController carrinhoController = new CarrinhoController();
    private Logger logger = Logger.getInstance();


    public void iniciar(Usuario usuario) {
        Optional<Cliente> clienteOptional = usuarioService.buscarCliente(usuario.getId());
        if (clienteOptional.isEmpty()) {
            System.out.println("Cliente nao encontrado.");
            logger.logWarn("Cliente não foi encontrado ao abrir menu", new RuntimeException("usuarioId=" + usuario.getId()));
            return;
        }

        System.out.println("Escolha uma opção");
        Cliente cliente = clienteOptional.get();
        int opcao;
        do {
            System.out.println("1 - Listar produtos");
            System.out.println("2 - Adicionar ao carrinho");
            System.out.println("3 - Remover do carrinho");
            System.out.println("4 - Ver carrinho");
            System.out.println("5 - Ver pedidos");
            System.out.println("6 - Finalizar pedido");
            System.out.println("7 - Sair");
            opcao = Componentes.inputInteger("Opcão: ");

            try {
                switch (opcao) {
                    case 1:
                        listarProdutos();
                        break;
                    case 2:
                        adicionarAoCarrinho(cliente.getUsuarioId());
                        break;
                    case 3:
                        removerDoCarrinho(cliente.getUsuarioId());
                        break;
                    case 4:
                        mostrarCarrinho(cliente.getUsuarioId());
                        break;
                    case 5:
                        mostrarPedidos(cliente.getId());
                        break;
                    case 7:
                        System.out.println("Saindo...");
                        break;
                    default:
                        System.out.println("Opcao inválida!");
                }
            } catch (Exception exception) {
                logger.logError("Falha ao executar operacao no menu de cliente", exception);
                System.out.println("Falha: " + exception.getMessage());
            }
        } while (opcao != 7);
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

    private void adicionarAoCarrinho(Integer usuarioId) {
        int produtoId = Componentes.inputInteger("Digite o Id do produto a ser adicionado: ");
        Optional<Produto> produtoOptional = produtoService.buscarPorId(produtoId);

        if (produtoOptional.isEmpty()) {
            System.out.println("Produto nao encontrado");
            return;
        }

        Produto produto = produtoOptional.get();
        // vai que tenha produto negativo
        if (produto.getQuantidadeEstoque() == null || produto.getQuantidadeEstoque() <= 0) {
            System.out.println("Produto sem estoque");
            return;
        }

        carrinhoController.adicionarAoCarrinho(usuarioId, produto);
        System.out.println("Produto " + produto.getNome() + " adicionado ao carrinho!");
    }

    private void removerDoCarrinho(Integer usuarioId) {
        int produtoId = Componentes.inputInteger("Digite o Id do produto para remover do carrinho: ");
        carrinhoController.removerDoCarrinho(usuarioId, produtoId);
        System.out.println("Produto removido do carrinho");
    }

    private void mostrarCarrinho(Integer usuarioId) {
        Optional<List<ProdutoCheckout>> carrinhoOptional = carrinhoController.buscarCarrinho(usuarioId);
        // se o carrinho não existe, ou se ele existir mas estiver vazio
        if (carrinhoOptional.isEmpty() || carrinhoOptional.get().isEmpty()) {
            System.out.println("Carinho vazio");
            return;
        }

        System.out.println("Itens do carrinho:");
        for (ProdutoCheckout produto : carrinhoOptional.get()) {
            System.out.println(
                "Produto: " + produto.getProduto().getNome()
                + " | Quantidade: " + produto.getQuantidade()
                + " | Subtotal: " + produto.calcularTotal()
            );
        }
        System.out.println("Total carrinho R$" + carrinhoController.valorTotal(usuarioId));
    }

    private void mostrarPedidos(Integer clienteId) {
        List<Pedido> pedidos = pedidoService.listarPedidosCliente(clienteId);
        if (pedidos.isEmpty()) {
            System.out.println("Nenhum pedido encontrado");
            return;
        }

        System.out.println("pedidos: ");
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
