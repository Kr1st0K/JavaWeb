package com.example.views;

import com.example.entities.ApplicantAssign;
import com.example.entities.ApplicantEnrollResult;
import com.example.enums.EducationForm;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class EnrollResultsView {
    public static void showEnrollResults(List<ApplicantEnrollResult> enrollResults,
                                         List<ApplicantAssign> assigns) {
        assigns = assigns.stream()
                .sorted(Comparator.comparing(ApplicantAssign::getPriority))
                .collect(Collectors.toList());
        for (ApplicantAssign assign : assigns) {
            List<ApplicantEnrollResult> facultyResult = enrollResults.stream()
                    .filter(x -> x.getFacultyId() == assign.getFacultyId())
                    .collect(Collectors.toList());
            ApplicantEnrollResult budgetResult = facultyResult.stream()
                    .filter(x -> x.getForm().equals(EducationForm.BUDGET))
                    .findFirst()
                    .get();
            ApplicantEnrollResult contractResult = facultyResult.stream()
                    .filter(x -> x.getForm().equals(EducationForm.CONTRACT))
                    .findFirst()
                    .get();
            String budgetPriorityStatus = budgetResult.isEnrolled() ? "enrolled" : "not enrolled";
            String contractPriorityStatus = contractResult.isEnrolled() ? "enrolled" : "not enrolled";
            System.out.println(assign.getPriority() + " priority: ");
            System.out.println("\tbudget: " + budgetPriorityStatus);
            System.out.println("\tcontract: " + contractPriorityStatus);
        }
    }
}
