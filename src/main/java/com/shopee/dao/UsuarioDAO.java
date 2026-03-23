package com.shopee.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.shopee.model.Usuario;
import com.shopee.model.Usuario.TipoUsuario;
import com.shopee.util.DatabaseConnection;

public class UsuarioDAO implements DAO<Usuario> {
    private Connection connection = DatabaseConnection.getInstance().getConnection();

    private Usuario mapper(ResultSet rs) {
        try {
            return new Usuario(
                rs.getInt("id"),
                rs.getString("nome"),
                rs.getString("email"),
                rs.getString("senha"),
                TipoUsuario.valueOf(rs.getString("tipo")),
                rs.getObject("data_cadastro", OffsetDateTime.class),
                rs.getBoolean("ativo")
            );
        } catch (SQLException sqlException) {
            throw new RuntimeException("Erro ao mapear dados para entidade usuario", sqlException);
        }
    }

    @Override
    public Usuario salvar(Usuario entidade) {
        String sql = "INSERT INTO usuario (nome, email, senha, tipo) VALUES (?, ?, ?, ?) RETURNING *";
        try (PreparedStatement pstm = connection.prepareStatement(sql)) {
            pstm.setString(1, entidade.getNome());
            pstm.setString(2, entidade.getEmail());
            pstm.setString(3, entidade.getSenha());
            pstm.setObject(4, entidade.getTipo(), Types.OTHER);
            ResultSet resultSet = pstm.executeQuery();
            resultSet.next();
            return mapper(resultSet);
        } catch (SQLException sqlException) {
            throw new RuntimeException("Erro ao inserir usuario", sqlException);
        }
    }

    @Override
    public Optional<Usuario> buscarPorId(int id) {
        String sql = "SELECT * FROM usuario WHERE id = ?";
        try(PreparedStatement pstm = connection.prepareStatement(sql)) {
            pstm.setInt(1, id);
            ResultSet rs = pstm.executeQuery();
            if (rs.next()) {
                Usuario usuario = mapper(rs);
                return Optional.of(usuario);
            }
        } catch (SQLException sqlException) {
            throw new RuntimeException("Erro ao buscar usuario id=" + id, sqlException);
        };
        
        return Optional.empty();
    }

    public Optional<Usuario> buscarPorEmail(String email) {
        String sql = "SELECT * FROM usuario WHERE email = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return Optional.of(mapper(rs));
            }
        } catch (SQLException sqlException) {
            throw new RuntimeException("Erro ao buscar usuario por email email=" + email, sqlException);
        }
        return Optional.empty();
    }


    @Override
    public List<Usuario> buscarTodos() {
        List<Usuario> usuarios = new ArrayList<>();
        try(PreparedStatement pstm = connection.prepareStatement("SELECT * FROM usuario")) {
            ResultSet resultSet = pstm.executeQuery();
            while (resultSet.next()) {
                usuarios.add(mapper(resultSet));
            }
        } catch (SQLException sqlException) {
            throw new RuntimeException("Erro ao buscar na tabela usuarios", sqlException);
        };
        
        return usuarios;
    }

    @Override
    public void atualizar(Usuario entidade) {
        String sql = "UPDATE usuario SET nome = ?, email = ?, senha = ? WHERE id = ?";
        try(PreparedStatement pstm = connection.prepareStatement(sql)) {
            pstm.setString(1, entidade.getNome());
            pstm.setString(2, entidade.getEmail());
            pstm.setString(3, entidade.getSenha());
            pstm.setInt(4, entidade.getId());
            pstm.executeUpdate();
        } catch (SQLException sqlException) {
            throw new RuntimeException("Erro ao atualizar usuario id=" + entidade.getId(), sqlException);
        };
    }

    @Override
    public void deletar(int id) {
        String sql = "DELETE FROM usuario WHERE id = ?";
        try(PreparedStatement pstm = connection.prepareStatement(sql)) {
            pstm.setInt(1, id);
            pstm.executeUpdate();
        } catch (SQLException sqlException) {
            throw new RuntimeException("Erro ao deletar usuario id=" + id, sqlException);
        };
    }

    @Override
    public long contar() {
        String sql = "SELECT COUNT(*) as contagem_usuarios FROM usuario";
        try (PreparedStatement pstm = connection.prepareStatement(sql)) {
            ResultSet resultSet = pstm.executeQuery();
            resultSet.next();
            long contagemUsuarios = resultSet.getLong("contagem_usuarios");
            return contagemUsuarios;
        } catch (SQLException sqlException) {
            throw new RuntimeException("Erro contar na tabela usuario", sqlException);
        }
    }
    
}
