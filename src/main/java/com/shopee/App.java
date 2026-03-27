package com.shopee;

import com.shopee.view.console.ConsoleApp;
import com.shopee.view.swing.SwingApp;

public class App {
    public static void main(String[] args) {
        SwingApp swingInterface = new SwingApp();
        ConsoleApp consoleApp = new ConsoleApp();
        if (args.length == 0) {
            consoleApp.iniciar();
        } else if (args.length == 1) {
            if (args[0].equals("swing")) {
                swingInterface.startSwingInterface();
            } else if (args[0].equals("cli")) {
                consoleApp.iniciar();
            } else {
                System.out.println("Argumento não reconhecido! argumentos: swing, cli");
            }
        }

    }
}
