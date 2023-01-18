package com.example.views;

import com.example.models.RegisterModel;

import java.util.Scanner;

public class RegisterView {
    public static RegisterModel getAnswer() {
        Scanner scanner = new Scanner(System.in);
        String login, password, name, email,
                phoneNumber, town, region, schoolName;
        int age;
        System.out.print("Enter login: ");
        login = scanner.nextLine();
        System.out.print("Enter password: ");
        password = scanner.nextLine();
        System.out.print("Enter name: ");
        name = scanner.nextLine();
        do {
            try {
                System.out.print("Enter age: ");
                age = Integer.parseInt(scanner.nextLine());
                break;
            } catch (NumberFormatException e) {
                System.out.println("Enter correct age");
            }
        } while (true);
        System.out.print("Enter email: ");
        email = scanner.nextLine();
        System.out.print("Enter phone number: ");
        phoneNumber = scanner.nextLine();
        System.out.print("Enter town: ");
        town = scanner.nextLine();
        System.out.print("Enter region: ");
        region = scanner.nextLine();
        System.out.print("Enter school name: ");
        schoolName = scanner.nextLine();
        return new RegisterModel(login, password, name, age, email, phoneNumber, town, region, schoolName);
    }
}
