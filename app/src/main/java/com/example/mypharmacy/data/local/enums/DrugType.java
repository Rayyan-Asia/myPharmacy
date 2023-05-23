package com.example.mypharmacy.data.local.enums;

public enum DrugType {
    TABLET("Tablet"),
    CAPSULE("Capsule"),
    SYRUP("Syrup"),
    INJECTION("Injection"),
    OINTMENT("Ointment"),
    DROPS("Drops");

    private String displayName;

    DrugType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}

