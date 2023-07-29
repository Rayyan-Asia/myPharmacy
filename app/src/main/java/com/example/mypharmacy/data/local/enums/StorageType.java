package com.example.mypharmacy.data.local.enums;

public enum StorageType {
    ADD_NEW("Take a picture"),
    ADD_FROM_LOCAL_STORAGE("Add from local storage");
    private final String value;

    StorageType(String value) {
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

