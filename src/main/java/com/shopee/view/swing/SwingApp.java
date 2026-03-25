package com.shopee.view.swing;

import java.util.Optional;

import javax.swing.SwingUtilities;

import com.shopee.dao.UsuarioDAO;
import com.shopee.model.Usuario;

public class SwingApp {
    public void startSwingInterface() {
        LoginFrame telaLogin = new LoginFrame();
        SwingUtilities.invokeLater(() -> {
            UsuarioDAO usuarioDAO = new UsuarioDAO();
            Optional<Usuario> usuario = usuarioDAO.buscarPorId(1);
            MainFrame mainFrame = new MainFrame(usuario.get());
            mainFrame.setVisible(true);
        });
    }
}
