package com.example.views;

import com.example.models.LoginModel;

import java.util.Scanner;

public class LoginView {
    public static LoginModel getAnswer() {
        Scanner scanner = new Scanner(System.in);
        String login, password;
        System.out.print("Enter login: ");
        login = scanner.nextLine();
        System.out.print("Enter password: ");
        password = scanner.nextLine();
        return new LoginModel(login, password);
    }
}
