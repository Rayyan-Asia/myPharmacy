package com.example.mypharmacy.ui.menstrualCal;

import android.content.AttributionSource;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.CalendarView;

import com.example.mypharmacy.data.local.entities.Menstruation;
import com.example.mypharmacy.data.local.repositories.MenstruationRepository;

import java.time.LocalDate;

public class MenstrualCycleCalendarView extends CalendarView {

    private MenstruationRepository menstruationRepository;

    public MenstrualCycleCalendarView(Context context, MenstruationRepository menstruationRepository) {
        super(context);
        this.menstruationRepository = menstruationRepository;
    }
    public MenstrualCycleCalendarView(Context context, AttributeSet attributeSet){
        super(context,attributeSet);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // Get the current date
        LocalDate today = LocalDate.now();

        // Get the menstruation data
        Menstruation menstruation = menstruationRepository.getMenstruation();

        // Color the days of the cycle
        if (menstruation != null) {
            LocalDate startDate = menstruation.startDate;
            LocalDate endDate = menstruation.endDate;

            for (int i = 0; i < 7; i++) {
                int day = today.plusDays(i).getDayOfMonth();

                if (day >= startDate.getDayOfMonth() && day <= endDate.getDayOfMonth()) {
                    // Color the day red
                    canvas.drawCircle(getCellX(i), getCellY(i), 5, getPaint(Color.RED));
                }
            }
        }
    }

    private float getCellX(int position) {
        int cellWidth = getWidth() / 7; // Assuming 7 cells in a row
        return position * cellWidth + cellWidth / 2.0f;
    }

    private float getCellY(int position) {
        int cellHeight = getHeight() / 6; // Assuming 6 rows in the calendar
        return position * cellHeight + cellHeight / 2.0f;
    }

    private Paint getPaint(int color) {
        Paint paint = new Paint();
        paint.setColor(color);
        paint.setStyle(Paint.Style.FILL); // Set the style to fill the circle
        paint.setAntiAlias(true); // Enable anti-aliasing for smoother edges
        return paint;
    }

    public void setMenstruationRepository(MenstruationRepository menstruationRepository) {
        this.menstruationRepository = menstruationRepository;
    }
}
