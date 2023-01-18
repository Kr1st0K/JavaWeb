package com.example.entities;

public class Faculty {
    private int id;
    private String name;
    private int allPlacesCount;
    private int budgetPlacesCount;
    private int universityId;

    public Faculty(String name, int allPlacesCount, int budgetPlacesCount) {
        this.name = name;
        this.allPlacesCount = allPlacesCount;
        this.budgetPlacesCount = budgetPlacesCount;
    }

    public Faculty(int id, String name, int allPlacesCount, int budgetPlacesCount, int universityId) {
        this.id = id;
        this.name = name;
        this.allPlacesCount = allPlacesCount;
        this.budgetPlacesCount = budgetPlacesCount;
        this.universityId = universityId;
    }

    public Faculty(String name, int allPlacesCount, int budgetPlacesCount, int universityId) {
        this.name = name;
        this.allPlacesCount = allPlacesCount;
        this.budgetPlacesCount = budgetPlacesCount;
        this.universityId = universityId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAllPlacesCount() {
        return allPlacesCount;
    }

    public void setAllPlacesCount(int allPlacesCount) {
        this.allPlacesCount = allPlacesCount;
    }

    public int getBudgetPlacesCount() {
        return budgetPlacesCount;
    }

    public void setBudgetPlacesCount(int budgetPlacesCount) {
        this.budgetPlacesCount = budgetPlacesCount;
    }

    public int getUniversityId() {
        return universityId;
    }

    public void setUniversityId(int universityId) {
        this.universityId = universityId;
    }
}
