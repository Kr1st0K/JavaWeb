package com.example.controllers;

import com.example.entities.*;
import com.example.enums.MarkType;
import com.example.services.applicant.ApplicantService;
import com.example.services.applicant.ApplicantServiceImpl;
import com.example.services.applicantAssign.ApplicantAssignService;
import com.example.services.applicantAssign.ApplicantAssignServiceImpl;
import com.example.services.applicantEnrollResult.ApplicantEnrollResultService;
import com.example.services.applicantEnrollResult.ApplicantEnrollResultServiceImpl;
import com.example.services.faculty.FacultyService;
import com.example.services.faculty.FacultyServiceImpl;
import com.example.services.mark.MarkService;
import com.example.services.mark.MarkServiceImpl;
import com.example.services.subject.SubjectService;
import com.example.services.subject.SubjectServiceImpl;
import com.example.services.university.UniversityService;
import com.example.services.university.UniversityServiceImpl;
import com.example.views.*;

import java.sql.SQLException;
import java.util.List;
import java.util.NoSuchElementException;

public class ApplicantController {
    private static ApplicantController instance;
    private final ApplicantService applicantService;
    private final FacultyService facultyService;
    private final UniversityService universityService;
    private final ApplicantAssignService applicantAssignService;
    private final SubjectService subjectService;
    private final MarkService markService;
    private final ApplicantEnrollResultService enrollService;
    private Applicant applicant;

    public ApplicantController() {
        this.applicantService = ApplicantServiceImpl.getInstance();
        this.applicantAssignService = ApplicantAssignServiceImpl.getInstance();
        this.facultyService = FacultyServiceImpl.getInstance();
        this.universityService = UniversityServiceImpl.getInstance();
        this.markService = MarkServiceImpl.getInstance();
        this.subjectService = SubjectServiceImpl.getInstance();
        this.enrollService = ApplicantEnrollResultServiceImpl.getInstance();
    }

    public static ApplicantController getInstance() {
        if (instance == null) {
            instance = new ApplicantController();
        }
        return instance;
    }

    public void mainMenu(Applicant applicant) {
        this.applicant = applicant;
        String answer;
        do {
            answer = ApplicantMainMenu.getAnswer();
            switch (answer) {
                case "1":
                    assign();
                    break;
                case "2":
                    getAssigns();
                    break;
                case "3":
                    addMark();
                    break;
                case "4":
                    getMarks();
                    break;
                case "5":
                    getEnrollResults();
                    break;
                case "6":
                    break;
            }
        } while (!answer.equals("6"));
    }

    public Applicant getApplicantByUserId(int userId) {
        try {
            return applicantService.getByUserId(userId);
        } catch (SQLException exception) {
            System.out.println(exception.getMessage());
            return null;
        }
    }

    public void assign() {
        do {
            try {
                List<ApplicantAssign> applicantAssigns =
                        applicantAssignService.getByUserId(applicant.getUserId());
                if (applicantAssigns.size() == 5)
                    break;

                List<University> universities = universityService.getAll();
                University university = UniversityView.getUniversity(universities);
                if (university == null)
                    break;

                List<Faculty> faculties = facultyService.getByUniversityId(university.getId());
                Faculty faculty = FacultyView.getFaculty(faculties);
                if (faculty == null)
                    break;
                if (applicantAssignService.isFacultyAssigned(faculty, applicant.getUserId()))
                    throw new IllegalArgumentException("You've already assigned to this faculty!!!");

                int priority = ApplicantAssignView.getPriority(applicantAssigns);
                ApplicantAssign applicantAssign = new ApplicantAssign(
                        applicant.getUserId(), faculty.getId(), priority);
                applicantAssignService.assign(applicantAssign);
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                break;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        } while (true);
    }

    public void getAssigns() {
        try {
            List<ApplicantAssign> assigns = applicantAssignService.getByUserId(applicant.getUserId());
            if (assigns == null || assigns.size() == 0) {
                throw new NoSuchElementException("You have no assigns!!!");
            }
            List<Integer> ids = applicantAssignService.getFacultyIdsFromApplicantAssigns(assigns);
            List<Faculty> faculties = facultyService.getByIds(ids);
            ids = facultyService.getUniversityIdsFromFaculties(faculties);
            List<University> universities = universityService.getByIds(ids);
            AssignsView.showAssigns(assigns, faculties, universities);
        } catch (SQLException | NoSuchElementException e) {
            System.out.println(e.getMessage());
        }
    }

    public void getMarks() {
        try {
            List<Mark> marks = markService.getByUserId(applicant.getUserId());
            if (marks == null || marks.size() == 0) {
                throw new NoSuchElementException("You have no marks!!!");
            }
            List<Subject> subjects = subjectService.getAll();
            MarksView.showMarks(marks, subjects);
        } catch (SQLException | NoSuchElementException e) {
            System.out.println(e.getMessage());
        }
    }

    public void addMark() {
        do {
            try {
                List<Subject> subjects = subjectService.getAll();
                List<Mark> marks = markService.getByUserId(applicant.getUserId());
                List<Mark> ieeMark = markService.getByMarkTypeFromList(marks, MarkType.IEE);
                List<Mark> certificateMark = markService.getByMarkTypeFromList(marks, MarkType.CERTIFICATE);
                List<Subject> ieeSubjectsToAdd = subjectService.
                        getMissingSubjectsFromMarkList(ieeMark, subjects);
                List<Subject> certificateSubjectsToAdd = subjectService.
                        getMissingSubjectsFromMarkList(certificateMark, subjects);
                Mark markToAdd = AddMarksView
                        .addMark(ieeSubjectsToAdd, certificateSubjectsToAdd, ieeMark.size());
                if (markToAdd == null)
                    break;
                markToAdd.setUserId(applicant.getUserId());

                markService.add(markToAdd);
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
                break;
            }
        } while (true);
    }

    public void getEnrollResults() {
        try {
            List<ApplicantAssign> assigns = applicantAssignService.getByUserId(applicant.getUserId());
            if (assigns == null || assigns.size() == 0) {
                throw new NoSuchElementException("You have no assigns!!!");
            }
            List<ApplicantEnrollResult> enrollResults = enrollService.getByUserId(applicant.getUserId());
            if (enrollResults == null || enrollResults.size() == 0) {
                throw new NoSuchElementException("You haven't your assign result!!!");
            }
            EnrollResultsView.showEnrollResults(enrollResults, assigns);
        } catch (SQLException | NoSuchElementException e) {
            System.out.println(e.getMessage());
        }
    }
}
