package com.example.views;

import com.example.entities.Faculty;
import com.example.entities.FacultyCoefficient;
import com.example.entities.Subject;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AddFacultyView {
    public static Faculty addFaculty() {
        Scanner scanner = new Scanner(System.in);
        String name;
        int allPlaces, budgetPlaces;
        System.out.print("Enter name: ");
        name = scanner.nextLine();
        do {
            try {
                System.out.print("Enter count of all places: ");
                allPlaces = Integer.parseInt(scanner.nextLine());
                System.out.print("Enter count of budget places: ");
                budgetPlaces = Integer.parseInt(scanner.nextLine());
                if (budgetPlaces > allPlaces)
                    throw new IllegalArgumentException("Amount of budget places must be " +
                            "lower than amount of all places");
                break;
            } catch (NumberFormatException e) {
                System.out.println("Enter correct number");
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        } while (true);
        return new Faculty(name, allPlaces, budgetPlaces);
    }

    public static List<FacultyCoefficient> getCoefficients(List<Subject> subjects) {
        Scanner scanner = new Scanner(System.in);
        String answer;
        List<FacultyCoefficient> coefficients = new ArrayList<>(3);
        for (int i = 0; i < 3; i++) {
            do {
                showSubjects(subjects);
                System.out.print("Choose " + (i + 1) + " subject to set coefficient: ");
                answer = scanner.nextLine();
                int index;
                try {
                    index = Integer.parseInt(answer);
                    Subject subject = subjects.stream()
                            .skip(index - 1)
                            .findFirst()
                            .orElseThrow(() -> new IllegalArgumentException("Choose correct subject"));

                    float coefficient = getCoefficient();
                    FacultyCoefficient facultyCoefficient = new FacultyCoefficient(subject.getId(), coefficient);
                    coefficients.add(facultyCoefficient);

                    if (areCoefficientsCorrect(coefficients)) {
                        subjects.remove(subject);
                    } else {
                        coefficients.remove(facultyCoefficient);
                        throw new IllegalArgumentException("Enter correct coefficient");
                    }
                    break;
                } catch (IllegalArgumentException ex) {
                    System.out.println(ex.getMessage());
                }
            } while (true);
        }
        return coefficients;
    }

    private static void showSubjects(List<Subject> subjects) {
        for (int i = 0; i < subjects.size(); i++) {
            System.out.println((i + 1) + ". " + subjects.get(i).getName());
        }
    }

    private static float getCoefficient() {
        Scanner scanner = new Scanner(System.in);
        String answer;
        float coefficient;
        do {
            System.out.println("Enter coefficient: ");
            answer = scanner.nextLine();
            try {
                coefficient = Float.parseFloat(answer);
                break;
            } catch (NumberFormatException ex) {
                System.out.println(ex.getMessage());
            }
        } while (true);
        return coefficient;
    }

    private static boolean areCoefficientsCorrect(List<FacultyCoefficient> coefficients) {
        return coefficients.stream()
                .mapToDouble(FacultyCoefficient::getCoefficient)
                .sum() <= 1;
    }
}
