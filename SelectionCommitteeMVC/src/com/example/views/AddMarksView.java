package com.example.views;

import com.example.entities.Mark;
import com.example.entities.Subject;
import com.example.enums.MarkType;

import java.util.List;
import java.util.Scanner;

public class AddMarksView {
    public static Mark addMark(List<Subject> ieeSubjects,
                               List<Subject> certificateSubjects,
                               int countOfIeeMarks) {
        Scanner scanner = new Scanner(System.in);
        Mark mark;
        String answer;
        do {
            showInterface();
            answer = scanner.nextLine();
            switch (answer) {
                case "1":
                    if (countOfIeeMarks == 4) {
                        System.out.println("You can't add more than 4 iee marks!!!");
                        break;
                    }
                    mark = getMark(ieeSubjects, MarkType.IEE);
                    if (mark == null)
                        continue;
                    return mark;
                case "2":
                    mark = getMark(certificateSubjects, MarkType.CERTIFICATE);
                    if (mark == null)
                        continue;
                    return mark;
                case "3":
                    return null;
                default:
                    System.out.println("Enter correct option!!!");
            }
        } while (true);
    }

    private static Mark getMark(List<Subject> subjects, MarkType type) {
        Scanner scanner = new Scanner(System.in);
        String answer;
        do {
            showSubjects(subjects);
            System.out.println((subjects.size() + 1) + ". Exit");
            System.out.print("Choose subject: ");
            answer = scanner.nextLine();
            int index;
            try {
                index = Integer.parseInt(answer);
                if (index == subjects.size() + 1)
                    break;
                Subject subject = subjects.stream()
                        .skip(index - 1)
                        .findFirst()
                        .orElseThrow(() -> new IllegalArgumentException("Choose correct subject"));

                int mark = enterMark();
                return new Mark(subject.getId(), type, mark);
            } catch (IllegalArgumentException ex) {
                System.out.println(ex.getMessage());
            }
        } while (true);
        return null;
    }

    private static int enterMark() {
        Scanner scanner = new Scanner(System.in);
        String answer;
        do {
            System.out.print("Enter mark: ");
            answer = scanner.nextLine();
            try {
                return Integer.parseInt(answer);
            } catch (NumberFormatException ex) {
                System.out.println("Enter correct number!!!");
            }
        } while (true);
    }

    private static void showInterface() {
        System.out.println("1. IEE");
        System.out.println("2. Certificate");
        System.out.println("3. Exit");
        System.out.println("Enter option: ");
    }

    private static void showSubjects(List<Subject> subjects) {
        for (int i = 0; i < subjects.size(); i++)
            System.out.println((i + 1) + ". " + subjects.get(i).getName());
    }
}
