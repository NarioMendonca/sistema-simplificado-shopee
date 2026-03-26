package com.shopee;

import com.shopee.view.console.ConsoleApp;
import com.shopee.view.swing.SwingApp;

public class App {
    public static void main(String[] args) {
        // SwingApp swingInterface = new SwingApp();
        // swingInterface.startSwingInterface();

        ConsoleApp consoleApp = new ConsoleApp();
        consoleApp.iniciar();
    }
}
