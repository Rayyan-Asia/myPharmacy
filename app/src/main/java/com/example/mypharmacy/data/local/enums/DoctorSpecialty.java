package com.example.mypharmacy.data.local.enums;

public enum DoctorSpecialty {
    CARDIOLOGIST("Cardiologist"),
    DERMATOLOGIST("Dermatologist"),
    ENDOCRINOLOGIST("Endocrinologist"),
    GASTROENTEROLOGIST("Gastroenterologist"),
    NEUROLOGIST("Neurologist"),
    ORTHOPEDIC_SURGEON("Orthopedic Surgeon"),
    PEDIATRICIAN("Pediatrician"),
    PSYCHIATRIST("Psychiatrist"),
    RADIOLOGIST("Radiologist"),
    UROLOGIST("Urologist");

    private String displayName;

    DoctorSpecialty(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}

