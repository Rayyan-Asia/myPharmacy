package com.example.mypharmacy.data.local.enums;

public enum DrugCategory {
    ANALGESIC("Analgesic"),
    ANTIBIOTIC("Antibiotic"),
    ANTIHYPERTENSIVE("Antihypertensive"),
    ANTIALLERGIC("Antiallergic"),
    ANTIDEPRESSANT("Antidepressant"),
    ANTIPYRETIC("Antipyretic");

    private String displayName;

    DrugCategory(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}

