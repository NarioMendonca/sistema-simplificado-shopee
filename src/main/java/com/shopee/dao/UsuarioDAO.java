package com.shopee.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.shopee.model.Cliente;
import com.shopee.model.Usuario;
import com.shopee.model.Vendedor;
import com.shopee.model.Usuario.TipoUsuario;
import com.shopee.util.DatabaseConnection;

public class UsuarioDAO implements DAO<Usuario> {
    private Connection connection = DatabaseConnection.getInstance().getConnection();

    private Usuario mapper(ResultSet rs) {
        Usuario usuario;
        try {
            if (rs.getString("tipo").equals("cliente")) {
                usuario = new Cliente();
            } else {
                usuario = new Vendedor();
            }
            usuario.setId(rs.getInt("id"));
            usuario.setNome(rs.getString("nome"));
            usuario.setEmail(rs.getString("email"));
            usuario.setSenha(rs.getString("senha"));
            usuario.setTipo(TipoUsuario.valueOf(rs.getString("tipo")));
            usuario.setDataCadastro(rs.getObject("data_cadastro", OffsetDateTime.class));
            return usuario;
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

    public Cliente salvarCliente(Cliente cliente) {
        String sqlUsuario = "INSERT INTO usuario (nome, email, senha, tipo) VALUES (?, ?, ?, ?) RETURNING *";
        String sqlCliente = "INSERT INTO cliente (usuario_id, cpf, telefone, data_nascimento, endereco) VALUES (?, ?, ?, ?, ?) RETURNING id";
        try (
            PreparedStatement pstmUsuario = connection.prepareStatement(sqlUsuario);
            PreparedStatement pstmCliente = connection.prepareStatement(sqlCliente)
        ) {
            pstmUsuario.setString(1, cliente.getNome());
            pstmUsuario.setString(2, cliente.getEmail());
            pstmUsuario.setString(3, cliente.getSenha());
            pstmUsuario.setObject(4, TipoUsuario.cliente, Types.OTHER);

            ResultSet rsUsuario = pstmUsuario.executeQuery();
            rsUsuario.next();
            int usuarioId = rsUsuario.getInt("id");

            pstmCliente.setInt(1, usuarioId);
            pstmCliente.setString(2, cliente.getCpf());
            pstmCliente.setString(3, cliente.getTelefone());
            if (cliente.getDataNascimento() != null) {
                pstmCliente.setDate(4, Date.valueOf(cliente.getDataNascimento().toLocalDate()));
            } else {
                pstmCliente.setNull(4, Types.DATE);
            }
            pstmCliente.setString(5, cliente.getEndereco());

            ResultSet rsCliente = pstmCliente.executeQuery();
            rsCliente.next();

            cliente.setId(rsCliente.getInt("id"));
            cliente.setUsuarioId(usuarioId);
            cliente.setTipo(TipoUsuario.cliente);
            cliente.setDataCadastro(rsUsuario.getObject("data_cadastro", OffsetDateTime.class));
            cliente.setAtivo(rsUsuario.getBoolean("ativo"));
            return cliente;
        } catch (SQLException sqlException) {
            throw new RuntimeException("Erro ao inserir cliente", sqlException);
        }
    }

    public Vendedor salvarVendedor(Vendedor vendedor) {
        String sqlUsuario = "INSERT INTO usuario (nome, email, senha, tipo) VALUES (?, ?, ?, ?) RETURNING *";
        String sqlVendedor = "INSERT INTO vendedor (usuario_id, cnpj, razao_social, telefone, avaliacao) VALUES (?, ?, ?, ?, ?) RETURNING id";
        try (
            PreparedStatement pstmUsuario = connection.prepareStatement(sqlUsuario);
            PreparedStatement pstmVendedor = connection.prepareStatement(sqlVendedor)
        ) {
            pstmUsuario.setString(1, vendedor.getNome());
            pstmUsuario.setString(2, vendedor.getEmail());
            pstmUsuario.setString(3, vendedor.getSenha());
            pstmUsuario.setObject(4, TipoUsuario.vendedor, Types.OTHER);

            ResultSet rsUsuario = pstmUsuario.executeQuery();
            rsUsuario.next();
            int usuarioId = rsUsuario.getInt("id");

            pstmVendedor.setInt(1, usuarioId);
            pstmVendedor.setString(2, vendedor.getCnpj());
            pstmVendedor.setString(3, vendedor.getRazaoSocial());
            pstmVendedor.setString(4, vendedor.getTelefone());
            pstmVendedor.setBigDecimal(5, vendedor.getAvaliacao());

            ResultSet rsVendedor = pstmVendedor.executeQuery();
            rsVendedor.next();

            vendedor.setId(rsVendedor.getInt("id"));
            vendedor.setUsuarioId(usuarioId);
            vendedor.setTipo(TipoUsuario.vendedor);
            vendedor.setDataCadastro(rsUsuario.getObject("data_cadastro", OffsetDateTime.class));
            vendedor.setAtivo(rsUsuario.getBoolean("ativo"));
            return vendedor;
        } catch (SQLException sqlException) {
            throw new RuntimeException("Erro ao inserir vendedor", sqlException);
        }
    }

    public Optional<Cliente> buscarCliente(int usuarioId) {
        String sql = """
            SELECT u.*, c.id as cliente_id, c.usuario_id, c.cpf, c.telefone, c.data_nascimento, c.endereco 
            FROM usuario u INNER JOIN cliente c ON c.usuario_id = u.id 
            WHERE u.id = ? AND u.tipo = 'cliente'
        """;;

        try (PreparedStatement pstm = connection.prepareStatement(sql)) {
            pstm.setInt(1, usuarioId);
            ResultSet rs = pstm.executeQuery();
            if (rs.next()) {
                Cliente cliente = new Cliente();
                cliente.setId(rs.getInt("cliente_id"));
                cliente.setUsuarioId(rs.getInt("usuario_id"));
                cliente.setNome(rs.getString("nome"));
                cliente.setEmail(rs.getString("email"));
                cliente.setSenha(rs.getString("senha"));
                cliente.setTipo(TipoUsuario.valueOf(rs.getString("tipo")));
                cliente.setDataCadastro(rs.getObject("data_cadastro", OffsetDateTime.class));
                cliente.setAtivo(rs.getBoolean("ativo"));
                cliente.setCpf(rs.getString("cpf"));
                cliente.setTelefone(rs.getString("telefone"));

                Date dataNascimento = rs.getDate("data_nascimento");
                if (dataNascimento != null) {
                    cliente.setDataNascimento(dataNascimento.toLocalDate().atStartOfDay().atOffset(OffsetDateTime.now().getOffset()));
                }

                cliente.setEndereco(rs.getString("endereco"));
                return Optional.of(cliente);
            }
        } catch (SQLException sqlException) {
            throw new RuntimeException("Erro ao buscar cliente usuario_id=" + usuarioId, sqlException);
        }

        return Optional.empty();
    }

    public Optional<Vendedor> buscarVendedor(int usuarioId) {
        String sql = """
            SELECT u.*, v.id as vendedor_id, v.usuario_id, v.cnpj, v.razao_social, v.telefone, v.avaliacao 
            FROM usuario u INNER JOIN vendedor v ON v.usuario_id = u.id 
            WHERE u.id = ? AND u.tipo = 'vendedor'
        """;

        try (PreparedStatement pstm = connection.prepareStatement(sql)) {
            pstm.setInt(1, usuarioId);
            ResultSet rs = pstm.executeQuery();
            if (rs.next()) {
                Vendedor vendedor = new Vendedor();
                vendedor.setId(rs.getInt("vendedor_id"));
                vendedor.setUsuarioId(rs.getInt("usuario_id"));
                vendedor.setNome(rs.getString("nome"));
                vendedor.setEmail(rs.getString("email"));
                vendedor.setSenha(rs.getString("senha"));
                vendedor.setTipo(TipoUsuario.valueOf(rs.getString("tipo")));
                vendedor.setDataCadastro(rs.getObject("data_cadastro", OffsetDateTime.class));
                vendedor.setAtivo(rs.getBoolean("ativo"));
                vendedor.setCnpj(rs.getString("cnpj"));
                vendedor.setRazaoSocial(rs.getString("razao_social"));
                vendedor.setTelefone(rs.getString("telefone"));
                vendedor.setAvaliacao(rs.getBigDecimal("avaliacao"));
                return Optional.of(vendedor);
            }
        } catch (SQLException sqlException) {
            throw new RuntimeException("Erro ao buscar vendedor usuario_id=" + usuarioId, sqlException);
        }

        return Optional.empty();
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
