package com.shopee.view.console.menus;

import java.math.BigDecimal;
import java.util.Scanner;

public class Componentes {
    public static Scanner scanner = new Scanner(System.in);

    public static int inputInteger(String text) {
        while (true) {
            try {
                System.out.println(text);
                return scanner.nextInt();
            } catch (NumberFormatException exception) {
                System.out.println("Digite um numero inteiro válido");
            }
        }
    }

    public static BigDecimal inputBigDecimal(String text) {
        while (true) {
            System.out.print(text);
            String valor = scanner.nextLine();
            try {
                return new BigDecimal(valor);
            } catch (NumberFormatException exception) {
                System.out.println("Digite um valor decimal válido");
            }
        }
    }

    public static String inputString(String text) {
        System.out.print(text);
        return scanner.nextLine();
    }
}
