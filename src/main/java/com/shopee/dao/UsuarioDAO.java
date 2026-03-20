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

    private Usuario mapper(ResultSet rs) throws SQLException {
        return new Usuario(
            rs.getInt("id"),
            rs.getString("nome"),
            rs.getString("email"),
            rs.getString("senha"),
            TipoUsuario.valueOf(rs.getString("tipo")),
            rs.getObject("data_cadastro", OffsetDateTime.class),
            rs.getBoolean("ativo")
        );
    }

    @Override
    public Usuario salvar(Usuario entidade) throws SQLException {
        String sql = "INSERT INTO usuario (nome, email, senha, tipo) VALUES (?, ?, ?, ?) RETURNING *";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, entidade.getNome());
        pstm.setString(2, entidade.getEmail());
        pstm.setString(3, entidade.getSenha());
        pstm.setObject(4, entidade.getTipo(), Types.OTHER);
        ResultSet resultSet = pstm.executeQuery();
        resultSet.next();
        return mapper(resultSet);
    }

    @Override
    public Optional<Usuario> buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM usuario WHERE id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setInt(1, id);
        ResultSet rs = pstm.executeQuery();
        if (rs.next()) {
            Usuario usuario = mapper(rs);
            return Optional.of(usuario);
        }
        
        return Optional.empty();
    }

    @Override
    public List<Usuario> buscarTodos() throws SQLException {
        List<Usuario> usuarios = new ArrayList<>();
        PreparedStatement pstm = connection.prepareStatement("SELECT * FROM usuario");
        ResultSet resultSet = pstm.executeQuery();
        while (resultSet.next()) {
            usuarios.add(mapper(resultSet));
        }
        
        return usuarios;
    }

    @Override
    public void atualizar(Usuario entidade) throws SQLException {
        String sql = "UPDATE usuario SET nome = ?, email = ?, senha = ? WHERE id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, entidade.getNome());
        pstm.setString(2, entidade.getEmail());
        pstm.setString(3, entidade.getSenha());
        pstm.setInt(4, entidade.getId());
        pstm.executeUpdate();
    }

    @Override
    public void deletar(int id) throws SQLException {
        String sql = "DELETE FROM usuario WHERE id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setInt(1, id);
        pstm.executeUpdate();
    }

    @Override
    public int contar() throws SQLException {
        String sql = "SELECT COUNT(*) as contagem_usuarios FROM usuario";
        PreparedStatement pstm = connection.prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();
        resultSet.next();
        int contagemUsuarios = resultSet.getInt("contagem_usuarios");
        return contagemUsuarios;
    }
    
}
