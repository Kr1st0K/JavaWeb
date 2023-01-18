package com.example.services.subject;

import com.example.dao.subject.SubjectDao;
import com.example.dao.subject.SubjectDaoImpl;
import com.example.entities.Mark;
import com.example.entities.Subject;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class SubjectServiceImpl implements SubjectService {
    private static SubjectServiceImpl instance;
    private final SubjectDao dao;

    public static SubjectServiceImpl getInstance() {
        if (instance == null) {
            instance = new SubjectServiceImpl();
        }
        return instance;
    }

    private SubjectServiceImpl() {
        this.dao = SubjectDaoImpl.getInstance();
    }

    @Override
    public List<Subject> getAll() throws SQLException {
        return dao.getAll();
    }

    @Override
    public Subject getById(int id) throws SQLException {
        return dao.getById(id);
    }

    @Override
    public Subject getByName(String name) throws SQLException {
        return dao.getByName(name);
    }

    @Override
    public List<Subject> getMissingSubjectsFromMarkList(List<Mark> marks,
                                                        List<Subject> allSubjects) throws SQLException {
        return allSubjects.stream()
                .filter(x -> marks.stream()
                        .noneMatch(y -> y.getSubjectId() == x.getId()))
                .collect(Collectors.toList());
    }
}
