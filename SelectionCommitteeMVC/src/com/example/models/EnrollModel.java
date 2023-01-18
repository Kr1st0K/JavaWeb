package com.example.models;

public class EnrollModel {
    private final int userId;
    private final int priority;
    private final double averageMark;

    public EnrollModel(int userId, int priority, double averageMark) {
        this.userId = userId;
        this.priority = priority;
        this.averageMark = averageMark;
    }

    public int getUserId() {
        return userId;
    }

    public int getPriority() {
        return priority;
    }

    public double getAverageMark() {
        return averageMark;
    }
}
