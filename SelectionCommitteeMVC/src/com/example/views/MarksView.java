package com.example.views;

import com.example.entities.Mark;
import com.example.entities.Subject;
import com.example.enums.MarkType;

import java.util.List;
import java.util.stream.Collectors;

public class MarksView {
    public static void showMarks(List<Mark> marks, List<Subject> subjects) {
        System.out.println();
        System.out.println("IEE: ");
        showMarksByType(marks, subjects, MarkType.IEE);
        System.out.println();
        System.out.println("Certificate: ");
        showMarksByType(marks, subjects, MarkType.CERTIFICATE);
        System.out.println();
    }

    private static void showMarksByType(List<Mark> marks, List<Subject> subjects, MarkType type) {
        List<Mark> marksByType = marks.stream()
                .filter(x -> x.getType().equals(type))
                .collect(Collectors.toList());
        for (Mark mark : marksByType) {
            Subject subject = subjects.stream()
                    .filter(x -> x.getId() == mark.getSubjectId())
                    .findFirst().get();
            System.out.println(subject.getName() + ": " + mark.getMark());
        }
    }
}
