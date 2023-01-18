package com.example.views;

import java.util.Scanner;

public class AdminMainMenu {
    private static void getInterface() {
        System.out.println("1. Add faculty");
        System.out.println("2. Edit faculty");
        System.out.println("3. Delete faculty");
        System.out.println("4. Block user");
        System.out.println("5. Unblock user");
        System.out.println("6. Add marks to user");
        System.out.println("7. Finalize");
        System.out.println("8. Exit");
        System.out.print("Enter option: ");
    }

    public static String getAnswer() {
        Scanner scanner = new Scanner(System.in);
        String answer;
        do {
            AdminMainMenu.getInterface();
            answer = scanner.nextLine();
        } while (!AdminMainMenu.verifyAnswer(answer));
        return answer;
    }

    private static boolean verifyAnswer(String answer) {
        switch (answer) {
            case "1":
            case "2":
            case "3":
            case "4":
            case "5":
            case "6":
            case "7":
            case "8":
                return true;
            default:
                return false;
        }
    }
}
