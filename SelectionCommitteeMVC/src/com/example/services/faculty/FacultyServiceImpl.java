package com.example.services.faculty;

import com.example.dao.faculty.FacultyDao;
import com.example.dao.faculty.FacultyDaoImpl;
import com.example.dao.university.UniversityDao;
import com.example.dao.university.UniversityDaoImpl;
import com.example.entities.Faculty;
import com.example.sorters.FacultySorter;

import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class FacultyServiceImpl implements FacultyService {
    private static FacultyServiceImpl instance;
    private final FacultyDao facultyDao;

    public static FacultyServiceImpl getInstance() {
        if (instance == null) {
            instance = new FacultyServiceImpl();
        }
        return instance;
    }

    private FacultyServiceImpl() {
        this.facultyDao = FacultyDaoImpl.getInstance();
    }

    @Override
    public void add(Faculty faculty) throws SQLException {
        boolean isExisted = facultyDao.getByUniversityId(faculty.getUniversityId())
                .stream()
                .anyMatch(x -> Objects.equals(x.getName(), faculty.getName()));
        if (isExisted)
            throw new IllegalArgumentException("Faculty with such name has already existed");
        facultyDao.add(faculty);
    }

    @Override
    public void edit(Faculty faculty) throws SQLException {
        boolean isExisted = facultyDao.getByUniversityId(faculty.getUniversityId())
                .stream()
                .filter(x -> Objects.equals(x.getName(), faculty.getName()))
                .anyMatch(x -> !Objects.equals(x.getId(), faculty.getId()));
        if (isExisted)
            throw new IllegalArgumentException("Faculty with such name has already existed");
        facultyDao.edit(faculty);
    }

    @Override
    public void delete(int id) throws SQLException {
        facultyDao.delete(id);
    }

    @Override
    public List<Faculty> sort(FacultySorter sorter) throws SQLException {
        List<Faculty> faculties = facultyDao.getAll();
        return sorter.sort(faculties);
    }

    @Override
    public List<Faculty> getByUniversityId(int universityId) throws SQLException {
        return facultyDao.getByUniversityId(universityId);
    }

    @Override
    public List<Faculty> getByIds(List<Integer> ids) throws SQLException {
        return facultyDao.getAll()
                .stream()
                .filter(x -> ids.stream().anyMatch(y -> x.getId() == y))
                .collect(Collectors.toList());
    }

    @Override
    public List<Integer> getUniversityIdsFromFaculties(List<Faculty> faculties) {
        return faculties.stream()
                .map(Faculty::getUniversityId)
                .distinct()
                .collect(Collectors.toList());
    }

    @Override
    public Faculty getByNameAndUniversityId(int universityId, String name) throws SQLException {
        return facultyDao.getByNameAndUniversityId(universityId, name);
    }
}
