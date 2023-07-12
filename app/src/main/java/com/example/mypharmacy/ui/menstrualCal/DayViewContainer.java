package com.example.mypharmacy.ui.menstrualCal;
import android.view.View;
import android.widget.TextView;
import com.example.mypharmacy.R;
import com.kizitonwose.calendar.view.ViewContainer;

public class DayViewContainer extends ViewContainer {
    public final TextView textView;

    public DayViewContainer(View view) {
        super(view);
        textView = view.findViewById(R.id.calendarDayText);

    }
}
