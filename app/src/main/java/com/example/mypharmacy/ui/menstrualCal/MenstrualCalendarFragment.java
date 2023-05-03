package com.example.mypharmacy.ui.menstrualCal;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mypharmacy.MenstruationsList;
import com.example.mypharmacy.R;
import com.example.mypharmacy.data.local.entities.Menstruation;
import com.example.mypharmacy.data.local.enums.Color;
import com.example.mypharmacy.data.local.repositories.MenstruationRepository;
import com.example.mypharmacy.data.local.repositories.impl.MenstruationRepositoryImpl;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class MenstrualCalendarFragment extends Fragment implements CalendarAdapter.OnItemListener {

    private TextView monthYearText;
    private RecyclerView calendarRecyclerView;
    private Button EditButton;
    private Button previousMenstruationsButton;
    private LocalDate currentDate = LocalDate.now();


    private MutableLiveData<Menstruation> menstruation = new MutableLiveData<>();

    public LiveData<Menstruation> getData() {
        return menstruation;
    }

    public MenstrualCalendarFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_menstrual_calendar, container, false);

    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        getMenstruation();
        this.getData().observe(getViewLifecycleOwner(), new Observer<Menstruation>() {
            @Override
            public void onChanged(Menstruation menstruation) {
                if ((menstruation == null || (!isSameMonth(currentDate, menstruation.endDate)
                        && !isSameMonth(currentDate, menstruation.startDate))) && menstruation.endDate.until(currentDate).getDays()> 35) {
                    Toast.makeText(getContext(), "No Entry in Previous Month", Toast.LENGTH_LONG).show();
                    switchToSurvey();
                } else {
                    initWidgets(view);
                    setListeners();
                    SetMonthView();
                }
            }
        });

    }

    private void switchToSurvey() {
        Fragment fragment = new MenstrualCycleSurvey();
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private void getMenstruation() {
        MenstruationRepository menstruationRepository = new MenstruationRepositoryImpl(this.getContext());
        new Thread(new Runnable() {
            @Override
            public void run() {
                Menstruation menstruationTemp = menstruationRepository.getMenstruation();
                menstruation.postValue(menstruationTemp);
            }
        }).start();


    }


    private void setListeners() {
        previousMenstruationsButton.setOnClickListener(e -> {
            Fragment fragment = new MenstruationsList();
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        });
        EditButton.setOnClickListener(e -> {
            switchToSurvey();
        });
    }

    private void SetMonthView() {
        monthYearText.setText(MonthYearFromDate(currentDate));
        ArrayList<CalendarDay> daysInMonth = DaysInMonthArray(currentDate);
        CalendarAdapter calendarAdapter = new CalendarAdapter(daysInMonth, this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext().getApplicationContext(), 7);
        calendarRecyclerView.setLayoutManager(layoutManager);
        calendarRecyclerView.setAdapter(calendarAdapter);

    }


    private ArrayList<CalendarDay> DaysInMonthArray(LocalDate selectedDate) {
        ArrayList<CalendarDay> daysInMonthArray = new ArrayList<>();
        YearMonth yearMonth = YearMonth.from(selectedDate);
        int daysInMonth = yearMonth.lengthOfMonth();
        LocalDate firstOfMonth = selectedDate.withDayOfMonth(1);
        int dayOfWeek = firstOfMonth.getDayOfWeek().getValue();

        for (int i = 1; i <= 42; i++) {
            if (i <= dayOfWeek || i > daysInMonth + dayOfWeek) {
                daysInMonthArray.add(new CalendarDay());
            } else {
                Color color = getColorFromDate(i - dayOfWeek, selectedDate);
                daysInMonthArray.add(new CalendarDay(color, String.valueOf(i - dayOfWeek)));
            }

        }
        return daysInMonthArray;
    }

    private Color getColorFromDate(int i, LocalDate selectedDate) {
        LocalDate endDate = menstruation.getValue().endDate;
        LocalDate startDate = menstruation.getValue().startDate;
        if (isSameMonth(endDate, selectedDate) && isSameMonth(startDate, selectedDate)) {
            return getColorSameMonth(i);
        } else if (isSameMonth(endDate, selectedDate) && !isSameMonth(startDate, selectedDate)) {
            return getColorAfter(i, endDate.getDayOfMonth());
        } else if (!isSameMonth(endDate, selectedDate) && isSameMonth(startDate, selectedDate)) {
            return getColorBefore(startDate.getDayOfMonth(), i);
        } else if (endDate.until(currentDate).getDays() < 40){
            return getColorAfter(i,endDate.getDayOfMonth() - endDate.getMonth().maxLength());
        }
        else return Color.PLAIN;

    }

    private boolean isSameMonth(LocalDate date, LocalDate selectedDate) {
        return (date.getMonth().equals(selectedDate.getMonth()) && date.getYear() == (selectedDate.getYear()));
    }

    @NonNull
    private Color getColorSameMonth(int i) {
        Color color;
        int endDay = menstruation.getValue().endDate.getDayOfMonth();
        int startDay = menstruation.getValue().startDate.getDayOfMonth();
        if (i >= startDay && i <= endDay) {
            color = Color.MENSTRUAL;
        } else {
            if (i < startDay) {
                color = getColorBefore(startDay, i);
            } else {
                color = getColorAfter(i, endDay);
            }
        }

        return color;
    }

    @NonNull
    private Color getColorBefore(int startDay, int i) {
        Color color;
        int stage;
        stage = startDay - i;
        if (stage < 0) {
            color = Color.MENSTRUAL;
        } else if (stage <= 9) {
            color = Color.LUTEAL;
        } else if (stage <= 14) {
            color = Color.OVULATION;
        } else if (stage <= 23)
            color = Color.FOLLICULAR;
        else color = Color.PLAIN;
        return color;
    }

    @NonNull
    private Color getColorAfter(int i, int endDay) {
        Color color;
        int stage;
        stage = i - endDay;
        if (i <= endDay)
            return Color.MENSTRUAL;
        if (stage <= 9) {
            color = Color.FOLLICULAR;
        } else if (stage <= 14) {
            color = Color.OVULATION;
        } else if (stage <= 23)
            color = Color.LUTEAL;
        else if (stage <= 28) {
            color = Color.EXPECTED;
        } else
            color = Color.PLAIN;


        return color;
    }

    private String MonthYearFromDate(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM yyyy");
        return date.format(formatter);
    }


    private void initWidgets(View view) {
        calendarRecyclerView = view.findViewById(R.id.calendarRecyclerView);
        monthYearText = view.findViewById(R.id.monthYearTextview);
        currentDate = LocalDate.now();
        previousMenstruationsButton = view.findViewById(R.id.PreviousMenstruationsButton);
        EditButton = view.findViewById(R.id.EditMenstruationButton);

    }

    @Override
    public void OnItemClick(int position, String dayText) {
        if (!dayText.equals("")) {
            String message = "Selected Date " + dayText + " " + MonthYearFromDate(currentDate);
            Toast.makeText(this.getContext(), message, Toast.LENGTH_LONG).show();
        }
    }
}
