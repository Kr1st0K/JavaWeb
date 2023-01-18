package com.example.views;

import com.example.entities.ApplicantAssign;
import com.example.entities.Faculty;
import com.example.entities.University;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class AssignsView {
    public static void showAssigns(List<ApplicantAssign> assigns,
                                   List<Faculty> faculties,
                                   List<University> universities) {
        assigns = assigns.stream()
                .sorted(Comparator.comparing(ApplicantAssign::getPriority))
                .collect(Collectors.toList());
        for (int i = 0; i < assigns.size(); i++) {
            ApplicantAssign assign = assigns.get(i);
            Faculty faculty = faculties.stream()
                    .filter(x -> x.getId() == assign.getFacultyId())
                    .findFirst().get();
            University university = universities.stream()
                    .filter(x -> x.getId() == faculty.getUniversityId())
                    .findFirst().get();
            System.out.println((i + 1) + ". " + "University: " + university.getName() +
                    "; faculty: " + faculty.getName() + "; priority: " + assign.getPriority());
        }
        System.out.println();
    }
}
