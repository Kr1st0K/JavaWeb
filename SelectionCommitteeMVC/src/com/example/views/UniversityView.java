package com.example.views;

import com.example.entities.University;

import java.util.List;
import java.util.Scanner;

public class UniversityView {
    public static University getUniversity(List<University> universities) {
        Scanner scanner = new Scanner(System.in);
        String answer;
        do {
            showUniversities(universities);
            System.out.println((universities.size() + 1) + ". Exit");
            System.out.print("Choose university: ");
            answer = scanner.nextLine();
            int index;
            try {
                index = Integer.parseInt(answer);
                if (index == universities.size() + 1)
                    break;
                return universities.stream()
                        .skip(index - 1)
                        .findFirst()
                        .orElseThrow(() -> new IllegalArgumentException("Choose correct university"));
            } catch (IllegalArgumentException ex) {
                System.out.println(ex.getMessage());
            }
        } while (true);
        return null;
    }

    public static void showUniversities(List<University> universities) {
        for (int i = 0; i < universities.size(); i++) {
            System.out.println((i + 1) + ". " + universities.get(i).getName());
        }
    }
}
