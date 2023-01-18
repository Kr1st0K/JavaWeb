package com.example.controllers;

import com.example.entities.Faculty;
import com.example.entities.User;
import com.example.models.LoginModel;
import com.example.models.RegisterModel;
import com.example.services.applicant.ApplicantService;
import com.example.services.applicant.ApplicantServiceImpl;
import com.example.services.faculty.FacultyService;
import com.example.services.faculty.FacultyServiceImpl;
import com.example.services.user.UserService;
import com.example.services.user.UserServiceImpl;
import com.example.sorters.FacultySorter;
import com.example.views.FacultySorterMenu;
import com.example.views.FacultyView;
import com.example.views.LoginView;
import com.example.views.RegisterView;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class UserController {
    private static UserController instance;
    private final UserService userService;
    private final FacultyService facultyService;
    private final ApplicantService applicantService;

    private UserController() {
        this.userService = UserServiceImpl.getInstance();
        this.facultyService = FacultyServiceImpl.getInstance();
        this.applicantService = ApplicantServiceImpl.getInstance();
    }

    public static UserController getInstance() {
        if (instance == null) {
            instance = new UserController();
        }
        return instance;
    }

    public User login() {
        do {
            LoginModel loginModel = LoginView.getAnswer();
            try {
                return userService.login(loginModel);
            } catch (IllegalArgumentException | SQLException ex) {
                System.out.println(ex.getMessage());
                if (exit()) {
                    break;
                }
            }
        } while (true);
        return null;
    }

    public void register() {
        do {
            RegisterModel registerModel = RegisterView.getAnswer();
            try {
                userService.register(registerModel);
                applicantService.register(registerModel);
                break;
            } catch (IllegalArgumentException | SQLException ex) {
                System.out.println(ex.getMessage());
                if (exit()) {
                    break;
                }
            }
        } while (true);
    }

    public void sortFaculties() {
        do {
            FacultySorter sorter = FacultySorterMenu.getSorter();
            if (sorter == null)
                break;
            try {
                List<Faculty> faculties = facultyService.sort(sorter);
                FacultyView.showFaculties(faculties);
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        } while (true);
    }

    public boolean exit() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Do you want to exit(Y/N)? ");
        String answer = scanner.nextLine();
        switch (answer) {
            case "Y":
            case "y":
                return true;
            case "N":
            case "n":
            default:
                return false;
        }
    }
}
