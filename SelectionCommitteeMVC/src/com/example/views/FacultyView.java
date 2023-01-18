package com.example.views;

import com.example.entities.Faculty;

import java.util.List;
import java.util.Scanner;

public class FacultyView {
    public static Faculty getFaculty(List<Faculty> faculties) {
        Scanner scanner = new Scanner(System.in);
        String answer;
        do {
            showFaculties(faculties);
            System.out.println((faculties.size() + 1) + ". Exit");
            System.out.print("Choose faculty: ");
            answer = scanner.nextLine();
            int index;
            try {
                index = Integer.parseInt(answer);
                if (index == faculties.size() + 1)
                    break;
                return faculties.stream()
                        .skip(index - 1)
                        .findFirst()
                        .orElseThrow(() -> new IllegalArgumentException("Choose correct faculty"));
            } catch (IllegalArgumentException ex) {
                System.out.println(ex.getMessage());
            }
        } while (true);
        return null;
    }

    public static void showFaculties(List<Faculty> faculties) {
        for (int i = 0; i < faculties.size(); i++) {
            System.out.println((i + 1) + ". " + faculties.get(i).getName());
        }
    }
}
