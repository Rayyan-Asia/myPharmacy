package com.example.mypharmacy.data.local.enums;

public enum LabTestType {
    ADD_NEW("Take a picture"),
    ADD_FROM_LOCAL_STORAGE("Add from local storage");
    private final String value;

    LabTestType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }
}

