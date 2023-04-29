package com.example.mypharmacy.data.local.enums;


public enum Color{
    RED("#FF0000"),
    GREEN("#00FF00"),
    BLUE("#0000FF");

    private final String hexCode;

    Color(String hexCode) {
        this.hexCode = hexCode;
    }

    public String getHexCode() {
        return hexCode;
    }
}
