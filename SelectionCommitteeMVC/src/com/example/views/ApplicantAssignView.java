package com.example.views;

import com.example.entities.ApplicantAssign;

import java.util.List;
import java.util.Scanner;

public class ApplicantAssignView {
    public static int getPriority(List<ApplicantAssign> assigns) {
        Scanner scanner = new Scanner(System.in);
        String answer;
        do {
            System.out.print("Enter priority: ");
            answer = scanner.nextLine();
            try {
                int priority = Integer.parseInt(answer);
                if (priority < 1 || priority > 5)
                    throw new IllegalArgumentException("Enter correct priority");
                int size = (int) assigns.stream()
                        .filter(x -> x.getPriority() == priority)
                        .count();
                if (size == 0)
                    return priority;
                else
                    throw new IllegalArgumentException("Enter priority you haven't set");
            } catch (IllegalArgumentException exception) {
                System.out.println(exception.getMessage());
            }
        } while (true);
    }
}
