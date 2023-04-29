package com.example.mypharmacy.ui.menstrualCal;

import com.example.mypharmacy.data.local.enums.Color;

public class CalendarDay {
    private Color color;
    private String day;

    public CalendarDay(Color color, String day) {
        this.color = color;
        this.day = day;
    }

    public CalendarDay() {

    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }
}
