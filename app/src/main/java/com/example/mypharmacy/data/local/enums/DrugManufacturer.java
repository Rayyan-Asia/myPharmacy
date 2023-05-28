package com.example.mypharmacy.data.local.enums;

public enum DrugManufacturer {
    BAYER("Bayer"),
    PFIZER("Pfizer"),
    NOVARTIS("Novartis"),
    JOHNSON_AND_JOHNSON("Johnson & Johnson"),
    GSK("GSK");
    private String displayName;

    DrugManufacturer(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
