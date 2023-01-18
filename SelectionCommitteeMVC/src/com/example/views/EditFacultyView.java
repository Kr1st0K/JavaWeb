package com.example.views;

import com.example.entities.Faculty;

import java.util.Scanner;

public class EditFacultyView {
    public static Faculty editFaculty(Faculty faculty) {
        Scanner scanner = new Scanner(System.in);
        String answer;
        Faculty origFaculty = new Faculty(faculty.getId(), faculty.getName(),
                faculty.getAllPlacesCount(), faculty.getBudgetPlacesCount(), faculty.getUniversityId());
        do {
            showEditInterface();
            answer = scanner.nextLine();
            switch (answer) {
                case "1":
                    System.out.print("Enter new name: ");
                    faculty.setName(scanner.nextLine());
                    break;
                case "2":
                    System.out.print("Enter new all places count: ");
                    int allPlaces;
                    try {
                        allPlaces = Integer.parseInt(scanner.nextLine());
                        if (allPlaces < faculty.getBudgetPlacesCount())
                            throw new IllegalArgumentException("All places should be higher than budget places!!!");
                    } catch (IllegalArgumentException ex) {
                        System.out.println(ex.getMessage());
                        break;
                    }
                    faculty.setAllPlacesCount(allPlaces);
                    break;
                case "3":
                    System.out.print("Enter new budget places count: ");
                    int budgetPlaces;
                    try {
                        budgetPlaces = Integer.parseInt(scanner.nextLine());
                        if (budgetPlaces > faculty.getAllPlacesCount())
                            throw new IllegalArgumentException("Budget places should be lower than all places!!!");
                    } catch (IllegalArgumentException ex) {
                        System.out.println(ex.getMessage());
                        break;
                    }
                    faculty.setBudgetPlacesCount(budgetPlaces);
                    break;
                case "4":
                    return faculty;
                case "5":
                    return origFaculty;
                default:
                    System.out.println("Enter correct option");
            }
        } while (true);
    }

    private static void showEditInterface() {
        System.out.println("1. Name");
        System.out.println("2. All places");
        System.out.println("3. Budget places");
        System.out.println("4. Save and exit");
        System.out.println("5. Cancel and exit");
        System.out.print("Enter option: ");
    }
}
