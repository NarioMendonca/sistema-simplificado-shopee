package com.shopee.services;

import java.util.Optional;

import com.shopee.dao.UsuarioDAO;
import com.shopee.model.Usuario;

public class UsuarioService {
    private UsuarioDAO usuarioDAO = new UsuarioDAO();

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
    } 
}
