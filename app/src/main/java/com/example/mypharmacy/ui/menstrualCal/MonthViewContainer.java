package com.example.mypharmacy.ui.menstrualCal;

import android.view.View;
import android.view.ViewGroup;

import com.kizitonwose.calendar.view.ViewContainer;

public class MonthViewContainer extends ViewContainer {
    public ViewGroup getTitlesContainer() {
        return titlesContainer;
    }

    public void setTitlesContainer(ViewGroup titlesContainer) {
        this.titlesContainer = titlesContainer;
    }

    // Alternatively, you can add an ID to the container layout and use findViewById()
    private ViewGroup titlesContainer;

    public MonthViewContainer(View view) {
        super(view);
        titlesContainer = (ViewGroup) view;
    }

    // Add any additional methods or fields as needed
}
