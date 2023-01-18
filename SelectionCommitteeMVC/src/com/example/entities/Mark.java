package com.example.entities;

import com.example.enums.MarkType;

public class Mark {
    private int userId;
    private int subjectId;
    private MarkType type;
    private int mark;

    public Mark(int userId, int subjectId, MarkType type, int mark) {
        this.userId = userId;
        this.subjectId = subjectId;
        this.type = type;
        this.mark = mark;
    }

    public Mark(int subjectId, MarkType type, int mark) {
        this.subjectId = subjectId;
        this.type = type;
        this.mark = mark;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
    }

    public MarkType getType() {
        return type;
    }

    public void setType(MarkType type) {
        this.type = type;
    }

    public int getMark() {
        return mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }
}
