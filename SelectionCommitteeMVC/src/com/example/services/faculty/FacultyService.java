package com.example.services.faculty;

import com.example.entities.Faculty;
import com.example.sorters.FacultySorter;

import java.sql.SQLException;
import java.util.List;

public interface FacultyService {
    void add(Faculty faculty) throws SQLException;

    void edit(Faculty faculty) throws SQLException;

    void delete(int id) throws SQLException;

    List<Faculty> sort(FacultySorter sorter) throws SQLException;

    List<Faculty> getByUniversityId(int universityId) throws SQLException;

    List<Faculty> getByIds(List<Integer> ids) throws SQLException;

    List<Integer> getUniversityIdsFromFaculties(List<Faculty> faculties);

    Faculty getByNameAndUniversityId(int universityId, String name) throws SQLException;
}
