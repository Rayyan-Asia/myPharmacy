package com.example.mypharmacy.data.local.enums;


public enum Color {
    LUTEAL("#ADD8E6"), //LIGHT BLUE
    FOLLICULAR("#90EE90"), // LIGHT GREEN
    OVULATION("#FFC0CB"), // PINK
    MENSTRUAL("#FF0000"); // RED

    private final String hexCode;

    Color(String hexCode) {
        this.hexCode = hexCode;
    }

    public String getHexCode() {
        return hexCode;
    }
}
