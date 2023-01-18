package com.example.services.applicantEnrollResult;

import com.example.dao.applicantAssign.ApplicantAssignDao;
import com.example.dao.applicantAssign.ApplicantAssignDaoImpl;
import com.example.dao.applicantEnrollResult.ApplicantEnrollResultDao;
import com.example.dao.applicantEnrollResult.ApplicantEnrollResultDaoImpl;
import com.example.dao.faculty.FacultyDao;
import com.example.dao.faculty.FacultyDaoImpl;
import com.example.dao.facultyCoefficient.FacultyCoefficientDao;
import com.example.dao.facultyCoefficient.FacultyCoefficientDaoImpl;
import com.example.dao.mark.MarkDao;
import com.example.dao.mark.MarkDaoImpl;
import com.example.entities.*;
import com.example.enums.EducationForm;
import com.example.enums.MarkType;
import com.example.models.EnrollModel;

import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

public class ApplicantEnrollResultServiceImpl implements ApplicantEnrollResultService {
    private static ApplicantEnrollResultServiceImpl instance;

    private final ApplicantEnrollResultDao enrollDao;

    private final ApplicantAssignDao assignDao;

    private final MarkDao markDao;
    private final FacultyCoefficientDao coefficientDao;
    private final FacultyDao facultyDao;

    private ApplicantEnrollResultServiceImpl() {
        this.enrollDao = ApplicantEnrollResultDaoImpl.getInstance();
        this.assignDao = ApplicantAssignDaoImpl.getInstance();
        this.markDao = MarkDaoImpl.getInstance();
        this.coefficientDao = FacultyCoefficientDaoImpl.getInstance();
        this.facultyDao = FacultyDaoImpl.getInstance();
    }

    public static ApplicantEnrollResultServiceImpl getInstance() {
        if (instance == null)
            instance = new ApplicantEnrollResultServiceImpl();
        return instance;
    }

    @Override
    public void addList(List<ApplicantEnrollResult> results) throws SQLException {
        enrollDao.addList(results);
    }

    @Override
    public List<ApplicantEnrollResult> getFinalizedResults(int facultyId) throws SQLException {
        Faculty faculty = facultyDao.getById(facultyId);
        int budgetPlaces = faculty.getBudgetPlacesCount();
        int contractPlacesCount = faculty.getAllPlacesCount() - budgetPlaces;
        List<FacultyCoefficient> coefficients = coefficientDao.getByFacultyId(facultyId);
        List<ApplicantAssign> assigns = assignDao.getByFacultyId(facultyId);

        List<Integer> userIds = assigns.stream()
                .map(ApplicantAssign::getUserId)
                .collect(Collectors.toList());
        List<Integer> alreadyEnrolled = enrollDao.getUserIdsByEducationForm(EducationForm.BUDGET);
        userIds = userIds.stream()
                .filter(x -> alreadyEnrolled.stream().noneMatch(y -> Objects.equals(x, y)))
                .collect(Collectors.toList());
        List<List<Mark>> usersMarks = getMarksByUserIds(userIds);

        List<EnrollModel> enrollModels = new ArrayList<>();
        for (List<Mark> marks : usersMarks) {
            int priority = assigns.stream()
                    .filter(x -> x.getUserId() == marks.get(0).getUserId())
                    .map(ApplicantAssign::getPriority)
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("Something wrong with getting priorities"));
            enrollModels.add(new EnrollModel(
                    marks.get(0).getUserId(), priority, getAverageUserMark(marks, coefficients)));
        }

        Comparator<EnrollModel> averageMarkComparator = Comparator
                .comparing(EnrollModel::getAverageMark).reversed();
        Comparator<EnrollModel> priorityComparator = averageMarkComparator
                .thenComparing(EnrollModel::getPriority);

        enrollModels = enrollModels.stream()
                .sorted(priorityComparator)
                .collect(Collectors.toList());
        List<EnrollModel> budgetResults = enrollModels.stream()
                .limit(budgetPlaces)
                .collect(Collectors.toList());
        List<EnrollModel> contractResults = enrollModels.stream()
                .skip(budgetPlaces)
                .limit(contractPlacesCount)
                .collect(Collectors.toList());
        List<EnrollModel> noResults = enrollModels.stream()
                .skip(budgetPlaces + contractPlacesCount)
                .collect(Collectors.toList());

        List<ApplicantEnrollResult> results = new ArrayList<>();
        for (EnrollModel enrollModel : budgetResults) {
            results.add(new ApplicantEnrollResult(
                    enrollModel.getUserId(), facultyId, EducationForm.BUDGET, true));
            results.add(new ApplicantEnrollResult(
                    enrollModel.getUserId(), facultyId, EducationForm.CONTRACT, false));
        }
        for (EnrollModel enrollModel : contractResults) {
            results.add(new ApplicantEnrollResult(
                    enrollModel.getUserId(), facultyId, EducationForm.CONTRACT, true));
            results.add(new ApplicantEnrollResult(
                    enrollModel.getUserId(), facultyId, EducationForm.BUDGET, false));
        }
        for (EnrollModel enrollModel : noResults) {
            results.add(new ApplicantEnrollResult(
                    enrollModel.getUserId(), facultyId, EducationForm.BUDGET, false));
            results.add(new ApplicantEnrollResult(
                    enrollModel.getUserId(), facultyId, EducationForm.CONTRACT, false));
        }
        return results;
    }

    @Override
    public List<ApplicantEnrollResult> getByUserId(int userId) throws SQLException {
        return enrollDao.getByUserId(userId);
    }

    private List<List<Mark>> getMarksByUserIds(List<Integer> ids) throws SQLException {
        List<List<Mark>> usersMarks = new ArrayList<>();
        for (Integer id : ids) {
            List<Mark> marks = markDao.getByUserId(id);
            usersMarks.add(marks);
        }
        return usersMarks;
    }

    private double getAverageUserMark(List<Mark> marks, List<FacultyCoefficient> coefficients) {
        double result = 0.0;
        List<Mark> ieeMarks = marks.stream()
                .filter(x -> x.getType().equals(MarkType.IEE))
                .collect(Collectors.toList());
        List<Mark> certificateMarks = marks.stream()
                .filter(x -> x.getType().equals(MarkType.CERTIFICATE))
                .collect(Collectors.toList());
        for (FacultyCoefficient coefficient : coefficients) {
            double coef = coefficient.getCoefficient();
            int subjectId = coefficient.getSubjectId();
            Mark mark = ieeMarks.stream()
                    .filter(x -> x.getSubjectId() == subjectId)
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("Something went wrong" +
                            " with getting average iee mark"));
            result += coef * mark.getMark();
        }

        double certificateCoefficient = 1 -
                coefficients.stream()
                        .mapToDouble(FacultyCoefficient::getCoefficient)
                        .sum();
        result += certificateCoefficient *
                certificateMarks.stream()
                        .mapToInt(Mark::getMark)
                        .average()
                        .orElse(0);

        if (result > 200)
            result = 200;
        result = Math.round(result * Math.pow(10, 3))
                / Math.pow(10, 3);
        return result;
    }
}