package com.example.enums;

public enum MarkType {
    IEE("IEE"),
    CERTIFICATE("CERTIFICATE");

    private final String type;
    MarkType(String type){
        this.type = type;
    }

    @Override
    public String toString() {
        return this.type;
    }
}
