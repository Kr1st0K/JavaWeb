package com.example.views;

import com.example.entities.User;

import java.util.List;
import java.util.Scanner;

public class UserView {
    public static User getUser(List<User> users) {
        Scanner scanner = new Scanner(System.in);
        String answer;
        do {
            showUsers(users);
            System.out.println((users.size() + 1) + ". Exit");
            System.out.print("Choose user: ");
            answer = scanner.nextLine();
            int index;
            try {
                index = Integer.parseInt(answer);
                if (index == users.size() + 1)
                    break;
                return users.stream()
                        .skip(index - 1)
                        .findFirst()
                        .orElseThrow(() -> new IllegalArgumentException("Choose correct user"));
            } catch (IllegalArgumentException ex) {
                System.out.println(ex.getMessage());
            }
        } while (true);
        return null;
    }

    public static void showUsers(List<User> users) {
        for (int i = 0; i < users.size(); i++) {
            System.out.println((i + 1) + ". " + users.get(i).getLogin());
        }
    }
}
