package com.shopee.services;

import java.util.Optional;

import com.shopee.dao.UsuarioDAO;
import com.shopee.model.Cliente;
import com.shopee.model.Usuario;
import com.shopee.model.Vendedor;
import com.shopee.util.Logger;

public class UsuarioService {
    private UsuarioDAO usuarioDAO = new UsuarioDAO();
    private Logger logger = Logger.getInstance();

    public Usuario cadastrar(String nome, String email, String senha, Usuario.TipoUsuario tipo) {
        if (nome.length() < 3) {
            throw new RuntimeException("Nome deve ter pelo menos 3 caracteres");
        }

        if (!email.contains("@")) {
            throw new RuntimeException("Email invalido");
        }

        if (senha.length() < 6) {
            throw new RuntimeException("Senha deve ser maior que 6 caracteres");
        }

        try {
            Usuario usuario = new Usuario(
                null, 
                nome, 
                email, 
                senha, 
                tipo, 
                null, 
                false
            );
            Usuario usuarioCriado = usuarioDAO.salvar(usuario);
            return usuarioCriado;
        } catch (Exception exception) {
            logger.logError("Erro ao cadastrar usuario email=" + email, exception);
            throw new RuntimeException("Erro ao cadastrar usuario", exception);
        }
    }

    public Cliente criarCliente(String nome, String email, String senha, String cpf, String telefone, java.time.OffsetDateTime dataNascimento, String endereco) {
        if (cpf == null || cpf.length() != 11) {
            throw new RuntimeException("CPF deve ter 11 caracteres");
        }

        try {
            Cliente cliente = new Cliente(
                null,
                null,
                nome,
                email,
                senha,
                Usuario.TipoUsuario.cliente,
                null,
                true,
                cpf,
                telefone,
                dataNascimento,
                endereco
            );

            Cliente clienteCriado = usuarioDAO.salvarCliente(cliente);
            return clienteCriado;
        } catch (Exception exception) {
            logger.logError("Erro ao cadastrar cliente email=" + email, exception);
            throw new RuntimeException("Erro ao cadastrar cliente", exception);
        }
    }

    public Vendedor criarVendedor(String nome, String email, String senha, String cnpj, String razaoSocial, String telefone) {
        if (cnpj == null || cnpj.length() != 14) {
            throw new RuntimeException("CNPJ deve ter 14 caracteres");
        }

        try {
            Vendedor vendedor = new Vendedor(
                null,
                null,
                nome,
                email,
                senha,
                Usuario.TipoUsuario.vendedor,
                null,
                true,
                cnpj,
                razaoSocial,
                telefone,
                null
            );

            Vendedor vendedorCriado = usuarioDAO.salvarVendedor(vendedor);
            return vendedorCriado;
        } catch (Exception exception) {
            logger.logError("Erro ao cadastrar vendedor email=" + email, exception);
            throw new RuntimeException("erro ao cadastrar vendedor", exception);
        }
    }

    public Optional<Cliente> buscarCliente(Integer usuarioId) {
        Optional<Cliente> cliente = usuarioDAO.buscarCliente(usuarioId);
        if (cliente.isEmpty()) {
            logger.logInfo("Falha ao buscar cliente usuarioId=" + usuarioId);
            return Optional.empty();
        }
        return cliente;
    }

    public Optional<Vendedor> buscarVendedor(Integer usuarioId) {
        Optional<Vendedor> vendedor = usuarioDAO.buscarVendedor(usuarioId);
        if (vendedor.isEmpty()) {
            logger.logInfo("Vendedor nao encontrado usuarioId=" + usuarioId);
            return Optional.empty();
        } else {
            return vendedor;
        }
    }

    public Usuario logar(String email, String senha) {
        Optional<Usuario> usuario = usuarioDAO.buscarPorEmail(email);
        if (usuario.isEmpty()) {
            throw new RuntimeException("Credenciais inválidas");
        }
        if (!usuario.get().getSenha().equals(senha)) {
            throw new RuntimeException("Credenciais inválidas");
        }
        return usuario.get();
    }

    public Usuario atualizarDados(Integer id, String nome, String email, String senha, Boolean ativo) {
        Optional<Usuario> antigoUsuario = usuarioDAO.buscarPorId(id);

        if (antigoUsuario.isEmpty()) {
            throw new RuntimeException("Usuario não encontrado");
        }
        if (nome != null && nome.length() < 3) {
            throw new RuntimeException("Nome deve ter pelo menos 3 caracteres");
        }
        if (email != null && !email.contains("@")) {
            throw new RuntimeException("Email invalido");
        }
        if (senha != null && senha.length() < 6) {
            throw new RuntimeException("Senha deve ser maior que 6 caracteres");
        }

        Optional<Usuario> usuarioJaExiste = usuarioDAO.buscarPorEmail(email);
        if (!usuarioJaExiste.isEmpty()) {
            throw new RuntimeException("Usuario com esse email já existe!");
        }


        try {
            Usuario usuario = new Usuario(
                id, 
                (nome != null) ? nome : antigoUsuario.get().getNome(), 
                (email != null) ? email : antigoUsuario.get().getEmail(), 
                (senha != null) ? senha : antigoUsuario.get().getSenha(), 
                null, 
                null, 
                (ativo != null) ? ativo : antigoUsuario.get().getAtivo()
            );
            usuarioDAO.atualizar(usuario);
            return usuario;
        } catch (Exception exception) {
            logger.logError("Erro ao atualizar dados de usuario id=" + id, exception);
            throw new RuntimeException("Erro ao atualizar dados de usuario id=" + id, exception);
        }
    } 
}
