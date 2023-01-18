package com.example.views;

import java.util.Scanner;

public class ApplicantMainMenu {
    private static void getInterface() {
        System.out.println("1. Assign to faculty");
        System.out.println("2. Show assigning faculties");
        System.out.println("3. Add marks");
        System.out.println("4. Get marks");
        System.out.println("5. Get enroll results");
        System.out.println("6. Exit");
        System.out.print("Enter option: ");
    }

    public static String getAnswer() {
        Scanner scanner = new Scanner(System.in);
        String answer;
        do {
            ApplicantMainMenu.getInterface();
            answer = scanner.nextLine();
        } while (!ApplicantMainMenu.verifyAnswer(answer));
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
                return true;
            default:
                return false;
        }
    }
}
