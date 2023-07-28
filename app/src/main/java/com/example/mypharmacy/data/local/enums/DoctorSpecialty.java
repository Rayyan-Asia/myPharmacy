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

    private final String value;

    DoctorSpecialty(String value) {
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

