package com.example.sorters;

import com.example.entities.Faculty;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ReverseAllPlacesFacultySorter implements FacultySorter{
    @Override
    public List<Faculty> sort(List<Faculty> faculties) {
        return faculties.stream()
                .sorted(Comparator.comparing(Faculty::getAllPlacesCount).reversed())
                .collect(Collectors.toList());
    }
}
