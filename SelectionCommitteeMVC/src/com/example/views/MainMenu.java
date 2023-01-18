package com.example.views;

import java.util.Scanner;

public class MainMenu {
    private static void getInterface() {
        System.out.println("1. Login");
        System.out.println("2. Register");
        System.out.println("3. Sort Faculties");
        System.out.println("4. Exit");
        System.out.print("Enter option: ");
    }

    public static String getAnswer() {
        Scanner scanner = new Scanner(System.in);
        String answer;
        do {
            MainMenu.getInterface();
            answer = scanner.nextLine();
        } while (!MainMenu.verifyAnswer(answer));
        return answer;
    }

    private static boolean verifyAnswer(String answer) {
        switch (answer) {
            case "1":
            case "2":
            case "3":
            case "4":
                return true;
            default:
                return false;
        }
    }
}
