package main.java.com.payments.view;

import java.util.Scanner;

public class ConsoleView {
    private final Scanner scanner = new Scanner(System.in);

    public int askInt(String prompt) {
        System.out.print(prompt + " ");
        while (!scanner.hasNextInt()) {
            System.out.print("Entrée invalide. " + prompt + " ");
            scanner.nextLine();
        }
        int val = scanner.nextInt();
        scanner.nextLine(); // consume newline
        return val;
    }

    public String askString(String prompt) {
        System.out.print(prompt + " ");
        return scanner.nextLine().trim();
    }

    public void show(String s) {
        System.out.println(s);
    }

    public void showLine() {
        System.out.println("-------------------------------------------------");
    }
}
