package com.example.mypharmacy.ui.menstrualCal;


import android.app.Notification;
import android.graphics.Color;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.mypharmacy.R;
import com.kizitonwose.calendar.core.CalendarMonth;
import com.kizitonwose.calendar.view.MonthHeaderFooterBinder;

public class MonthHeaderBinderImpl implements MonthHeaderFooterBinder<MonthViewContainer> {
    private final String[] daysOfWeek = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};

    @Override
    public MonthViewContainer create(View view) {
        return new MonthViewContainer(view);
    }

    @Override
    public void bind(MonthViewContainer container, CalendarMonth data) {
        // Remember that the header is reused, so this will be called for each month.
        // However, the first day of the week will not change, so no need to bind
        // the same view every time it is reused.
        if (container.getTitlesContainer().getTag() == null) {
            container.getTitlesContainer().setTag(data.getYearMonth());
            int childCount = container.getTitlesContainer().getChildCount();
            for (int index = 0; index < childCount; index++) {
                View child = container.getTitlesContainer().getChildAt(index);
                if (child instanceof LinearLayout){
                    for (int index2 = 0; index2 < ((LinearLayout) child).getChildCount(); index2++) {
                        View innerChild = ((LinearLayout) child).getChildAt(index2);
                        if(innerChild instanceof TextView){
                            TextView textView = (TextView) innerChild;
                            String title = daysOfWeek[index2];
                            textView.setText(title);
                            textView.setTextColor(Color.WHITE);
                        }

                    }
                }else{
                    TextView textView = (TextView) child;
                    String title = data.getYearMonth().getMonth().toString();
                    textView.setText(title);
//                    int color = textView.getContext().getResources().getColor(R.color.myPharmacy_main_color);
//                    textView.setTextColor(color);
                    textView.setTextColor(Color.WHITE);

                }
            }
        }
    }
}