package com.shopee.view.swing;

import javax.swing.SwingUtilities;


public class SwingApp {
    public void startSwingInterface() {
        LoginFrame telaLogin = new LoginFrame();
        SwingUtilities.invokeLater(() -> {
            telaLogin.setVisible(true);
        });
    }
}
