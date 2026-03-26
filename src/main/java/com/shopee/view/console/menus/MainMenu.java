package com.shopee.view.console.menus;

import com.shopee.model.Usuario;

public class MainMenu {
    private MenuCliente menuCliente = new MenuCliente();
    private MenuVendedor menuVendedor = new MenuVendedor();

    public void iniciar(Usuario usuario) {
        if (usuario.getTipo() == Usuario.TipoUsuario.cliente) {
            menuCliente.iniciar(usuario);
            return;
        }
        menuVendedor.iniciar(usuario);
    }
}
