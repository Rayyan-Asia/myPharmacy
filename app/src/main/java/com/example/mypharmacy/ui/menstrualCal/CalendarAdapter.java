package com.example.mypharmacy.ui.menstrualCal;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mypharmacy.R;

import java.util.ArrayList;

public class CalendarAdapter extends RecyclerView.Adapter<CalendarViewHolder> {
    private final ArrayList<CalendarDay> daysOfMonth;
    private final OnItemListener onItemListener;

    public CalendarAdapter(ArrayList<CalendarDay> daysOfMonth, OnItemListener onItemListener) {
        this.daysOfMonth = daysOfMonth;
        this.onItemListener = onItemListener;
    }

    @NonNull
    @Override
    public CalendarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.calendar_cell,parent,false);
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.height = (int) (parent.getHeight() * 0.1666666);
        return new CalendarViewHolder(view, onItemListener);
    }

    @Override
    public void onBindViewHolder(@NonNull CalendarViewHolder holder, int position) {
        CalendarDay calendarDay = daysOfMonth.get(position);
        if ((calendarDay.getDay() != null)) {
            holder.dayOfMonth.setText(calendarDay.getDay());
        } else {
            holder.dayOfMonth.setText("");
        }
        if ((calendarDay.getColor() != null))
            holder.itemView.setBackgroundColor(Color.parseColor(calendarDay.getColor().getHexCode()));
    }

    @Override
    public int getItemCount() {
        return daysOfMonth.size();
    }
    public interface OnItemListener{
        void OnItemClick(int position, String dayText);
    }
}
