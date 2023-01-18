package com.example.controllers;

import com.example.entities.*;
import com.example.enums.MarkType;
import com.example.enums.UserRole;
import com.example.services.applicantEnrollResult.ApplicantEnrollResultService;
import com.example.services.applicantEnrollResult.ApplicantEnrollResultServiceImpl;
import com.example.services.faculty.FacultyService;
import com.example.services.faculty.FacultyServiceImpl;
import com.example.services.facultyCoefficient.FacultyCoefficientService;
import com.example.services.facultyCoefficient.FacultyCoefficientServiceImpl;
import com.example.services.mark.MarkService;
import com.example.services.mark.MarkServiceImpl;
import com.example.services.subject.SubjectService;
import com.example.services.subject.SubjectServiceImpl;
import com.example.services.university.UniversityService;
import com.example.services.university.UniversityServiceImpl;
import com.example.services.user.UserService;
import com.example.services.user.UserServiceImpl;
import com.example.views.*;

import java.sql.SQLException;
import java.util.List;

public class AdminController {
    private static AdminController instance;
    private final UserService userService;
    private final UniversityService universityService;
    private final FacultyService facultyService;
    private final SubjectService subjectService;
    private final MarkService markService;
    private final FacultyCoefficientService facultyCoefficientService;

    private final ApplicantEnrollResultService enrollService;
    private User currentUser;

    private AdminController() {
        this.userService = UserServiceImpl.getInstance();
        this.universityService = UniversityServiceImpl.getInstance();
        this.facultyService = FacultyServiceImpl.getInstance();
        this.subjectService = SubjectServiceImpl.getInstance();
        this.facultyCoefficientService = FacultyCoefficientServiceImpl.getInstance();
        this.markService = MarkServiceImpl.getInstance();
        this.enrollService = ApplicantEnrollResultServiceImpl.getInstance();
    }

    public static AdminController getInstance() {
        if (instance == null) {
            instance = new AdminController();
        }
        return instance;
    }

    public void mainMenu(User user) {
        this.currentUser = user;
        String answer;
        do {
            answer = AdminMainMenu.getAnswer();
            switch (answer) {
                case "1":
                    addFaculty();
                    break;
                case "2":
                    editFaculty();
                    break;
                case "3":
                    deleteFaculty();
                    break;
                case "4":
                    block();
                    break;
                case "5":
                    unblock();
                    break;
                case "6":
                    addMarks();
                    break;
                case "7":
                    finalizeResults();
                    break;
                case "8":
                    break;
            }
        } while (!answer.equals("8"));
    }

    public void block() {
        do {
            try {
                List<User> users = userService.getUsersWhoCanBeBlocked();
                User user = UserView.getUser(users);
                if (user == null)
                    break;
                userService.block(user.getId());
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
                break;
            }
        } while (true);
    }

    public void unblock() {
        do {
            try {
                List<User> users = userService.getBlockedUsers();
                User user = UserView.getUser(users);
                if (user == null)
                    break;
                userService.unblock(user.getId());
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
                break;
            }
        } while (true);
    }

    public void addFaculty() {
        do {
            try {
                List<University> universities = universityService.getAll();
                University university = UniversityView.getUniversity(universities);
                if (university == null)
                    break;
                Faculty faculty = AddFacultyView.addFaculty();
                faculty.setUniversityId(university.getId());
                facultyService.add(faculty);

                List<Subject> subjects = subjectService.getAll();
                List<FacultyCoefficient> coefficients = AddFacultyView.getCoefficients(subjects);

                Faculty facultyWithId = facultyService.getByNameAndUniversityId(
                        university.getId(), faculty.getName());
                coefficients.forEach(x -> x.setFacultyId(facultyWithId.getId()));
                facultyCoefficientService.addList(coefficients);
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
                break;
            }
        } while (true);
    }

    public void deleteFaculty() {
        do {
            try {
                List<University> universities = universityService.getAll();
                University university = UniversityView.getUniversity(universities);
                if (university == null)
                    break;

                List<Faculty> faculties = facultyService.getByUniversityId(university.getId());
                Faculty faculty = FacultyView.getFaculty(faculties);
                if (faculty == null)
                    break;

                facultyCoefficientService.deleteByFacultyId(faculty.getId());
                facultyService.delete(faculty.getId());
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
                break;
            }
        } while (true);
    }

    public void editFaculty() {
        do {
            try {
                List<University> universities = universityService.getAll();
                University university = UniversityView.getUniversity(universities);
                if (university == null)
                    break;

                List<Faculty> faculties = facultyService.getByUniversityId(university.getId());
                Faculty faculty = FacultyView.getFaculty(faculties);
                if (faculty == null)
                    break;

                faculty = EditFacultyView.editFaculty(faculty);
                facultyService.edit(faculty);
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
                break;
            }
        } while (true);
    }

    public void addMarks() {
        do {
            try {
                List<User> users = userService.getByUserRole(UserRole.APPLICANT);
                User user = UserView.getUser(users);
                if (user == null)
                    break;

                List<Subject> subjects = subjectService.getAll();
                List<Mark> marks = markService.getByUserId(user.getId());
                List<Mark> ieeMark = markService.getByMarkTypeFromList(marks, MarkType.IEE);
                List<Mark> certificateMark = markService.getByMarkTypeFromList(marks, MarkType.CERTIFICATE);
                List<Subject> ieeSubjectsToAdd = subjectService.
                        getMissingSubjectsFromMarkList(ieeMark, subjects);
                List<Subject> certificateSubjectsToAdd = subjectService.
                        getMissingSubjectsFromMarkList(certificateMark, subjects);
                Mark markToAdd = AddMarksView
                        .addMark(ieeSubjectsToAdd, certificateSubjectsToAdd, ieeMark.size());
                if (markToAdd == null)
                    continue;
                markToAdd.setUserId(user.getId());

                markService.add(markToAdd);
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
                break;
            }
        } while (true);
    }

    public void finalizeResults() {
        do {
            try {
                List<University> universities = universityService.getAll();
                University university = UniversityView.getUniversity(universities);
                if (university == null)
                    break;

                List<Faculty> faculties = facultyService.getByUniversityId(university.getId());
                Faculty faculty = FacultyView.getFaculty(faculties);
                if (faculty == null)
                    break;

                List<ApplicantEnrollResult> results = enrollService.getFinalizedResults(faculty.getId());
                enrollService.addList(results);
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
                break;
            }
        } while (true);
    }
}
