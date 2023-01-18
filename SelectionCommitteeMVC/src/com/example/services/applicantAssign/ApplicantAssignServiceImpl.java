package com.example.services.applicantAssign;

import com.example.dao.applicantAssign.ApplicantAssignDao;
import com.example.dao.applicantAssign.ApplicantAssignDaoImpl;
import com.example.dao.facultyCoefficient.FacultyCoefficientDao;
import com.example.dao.facultyCoefficient.FacultyCoefficientDaoImpl;
import com.example.dao.mark.MarkDao;
import com.example.dao.mark.MarkDaoImpl;
import com.example.entities.ApplicantAssign;
import com.example.entities.Faculty;
import com.example.entities.FacultyCoefficient;
import com.example.entities.Mark;
import com.example.enums.MarkType;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class ApplicantAssignServiceImpl implements ApplicantAssignService {
    private static ApplicantAssignServiceImpl instance;

    private final FacultyCoefficientDao facultyCoefficientDao;
    private final ApplicantAssignDao applicantAssignDao;
    private final MarkDao markDao;

    private ApplicantAssignServiceImpl() {
        this.applicantAssignDao = ApplicantAssignDaoImpl.getInstance();
        this.facultyCoefficientDao = FacultyCoefficientDaoImpl.getInstance();
        this.markDao = MarkDaoImpl.getInstance();
    }

    public static ApplicantAssignServiceImpl getInstance() {
        if (instance == null)
            instance = new ApplicantAssignServiceImpl();
        return instance;
    }

    @Override
    public void assign(ApplicantAssign assign) throws SQLException {
        List<FacultyCoefficient> coefficients = facultyCoefficientDao.getByFacultyId(assign.getFacultyId());
        List<Mark> ieeMarks = markDao.getByUserId(assign.getUserId())
                .stream()
                .filter(x -> x.getType().equals(MarkType.IEE))
                .collect(Collectors.toList());
        for (FacultyCoefficient coefficient : coefficients) {
            boolean hasSubjectMark = ieeMarks.stream()
                    .anyMatch(x -> x.getSubjectId() == coefficient.getSubjectId());
            if (!hasSubjectMark)
                throw new IllegalArgumentException("Add all iee marks that are" +
                        " needed to assign to this faculty!!!");
        }
        applicantAssignDao.add(assign);
    }

    @Override
    public List<ApplicantAssign> getByUserId(int userId) throws SQLException {
        return applicantAssignDao.getByUserId(userId);
    }

    @Override
    public List<Integer> getFacultyIdsFromApplicantAssigns(List<ApplicantAssign> assigns) throws SQLException {
        return assigns.stream()
                .map(ApplicantAssign::getFacultyId)
                .distinct()
                .collect(Collectors.toList());
    }

    @Override
    public boolean isFacultyAssigned(Faculty faculty, int userId) throws SQLException {
        return applicantAssignDao.getByUserId(userId)
                .stream()
                .anyMatch(x -> x.getFacultyId() == faculty.getId());
    }
}
