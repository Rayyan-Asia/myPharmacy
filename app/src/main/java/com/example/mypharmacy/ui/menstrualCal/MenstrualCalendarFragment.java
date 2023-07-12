package com.example.mypharmacy.ui.menstrualCal;

import static com.kizitonwose.calendar.core.ExtensionsKt.firstDayOfWeekFromLocale;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.example.mypharmacy.R;
import com.example.mypharmacy.data.local.entities.Menstruation;
import com.example.mypharmacy.data.local.repositories.MenstruationRepository;
import com.example.mypharmacy.data.local.repositories.impl.MenstruationRepositoryImpl;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.kizitonwose.calendar.core.CalendarDay;
import com.kizitonwose.calendar.core.DayPosition;
import com.kizitonwose.calendar.view.MonthDayBinder;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Locale;

public class MenstrualCalendarFragment extends Fragment {

    private FloatingActionButton editButton;
    private FloatingActionButton addButton;
    private LocalDate currentDate = LocalDate.now();
    private MutableLiveData<Menstruation> menstruation = new MutableLiveData<>();
    private MutableLiveData<Hashtable<LocalDate, Integer>> dates = new MutableLiveData<>();
    private com.kizitonwose.calendar.view.CalendarView menstrualCalendar;

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
        super.onViewCreated(view, savedInstanceState);
        initWidgets(view);
        setListeners();
        getMenstruation();
        getDates();
        this.getData().observe(getViewLifecycleOwner(), new Observer<Menstruation>() {
            @Override
            public void onChanged(Menstruation menstruation) {
                if (menstruation == null) {
                    switchToSurvey(false);
                } else if (!isSameMonth(currentDate, menstruation.endDate) &&
                        !isSameMonth(currentDate, menstruation.startDate) &&
                        menstruation.endDate.until(currentDate).getDays() > 35) {
                    Toast.makeText(getContext(), "No Entry in Previous Month", Toast.LENGTH_LONG).show();
                    switchToSurvey(false);
                }
            }
        });

        this.getCalendarDates().observe(getViewLifecycleOwner(), new Observer<Hashtable<LocalDate, Integer>>() {
            @Override
            public void onChanged(Hashtable<LocalDate, Integer> localDateColorHashtable) {
                // Update the calendar dates when the LiveData changes
                setupCalendar();

            }
        });


    }

    private void initWidgets(View view) {
        currentDate = LocalDate.now();
        editButton = view.findViewById(R.id.EditMenstruationButton);
        addButton = view.findViewById(R.id.AddMenstruationButton);
        menstrualCalendar = view.findViewById(R.id.calendarView);
    }

    private void setListeners() {
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToSurvey(true);
            }
        });
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToSurvey(false);
            }
        });
    }

    private void getMenstruation() {
        MenstruationRepository menstruationRepository = new MenstruationRepositoryImpl(requireContext());
        new Thread(new Runnable() {
            @Override
            public void run() {
                Menstruation menstruationTemp = menstruationRepository.getMenstruation();
                menstruation.postValue(menstruationTemp);
            }
        }).start();
    }

    private void getDates() {
        MenstruationRepository menstruationRepository = new MenstruationRepositoryImpl(requireContext());
        new Thread(new Runnable() {
            @Override
            public void run() {
                Hashtable<LocalDate, Integer> menstruationTemp = menstruationRepository.getCalendarDays();
                dates.postValue(menstruationTemp);
            }
        }).start();
    }

    private void setupCalendar() {
        setupDayBinder();
        YearMonth currentMonth = YearMonth.now();
        YearMonth startMonth = currentMonth.minusMonths(3); // Adjust as needed
        YearMonth endMonth = currentMonth.plusMonths(1); // Adjust as needed
        DayOfWeek firstDayOfWeek = firstDayOfWeekFromLocale(); // Available from the library

        menstrualCalendar.setup(startMonth, endMonth, firstDayOfWeek);
        menstrualCalendar.scrollToMonth(currentMonth);
        menstrualCalendar.setMonthHeaderBinder(new MonthHeaderBinderImpl());
    }

    private boolean isSameMonth(LocalDate date, LocalDate selectedDate) {
        return date.getMonth() == selectedDate.getMonth() && date.getYear() == selectedDate.getYear();
    }


    private void setupDayBinder() {
        menstrualCalendar.setDayBinder(new MonthDayBinder<DayViewContainer>() {
            @Override
            public DayViewContainer create(View view) {
                return new DayViewContainer(view);
            }

            @Override
            public void bind(DayViewContainer container, CalendarDay data) {
                String day = String.valueOf(data.getDate().getDayOfMonth());

                Integer color = dates.getValue().get(data.getDate());
                container.textView.setText(day);

                Drawable background = container.textView.getBackground();
                GradientDrawable gradientDrawable = (GradientDrawable) background;
                if (color != null) {
                    int resourceColor = getContext().getColor(color);
                    gradientDrawable.setColor(resourceColor);
                    container.textView.setTextColor(Color.BLACK);
                } else {
                    gradientDrawable.setColor(Color.WHITE);
                    container.textView.setTextColor(Color.BLACK);
                }


            }
        });
    }

    private void switchToSurvey(boolean isEdit) {
        MenstrualCycleSurvey fragment = new MenstrualCycleSurvey();
        fragment.isEdit = isEdit;
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }


    public LiveData<Menstruation> getData() {
        return menstruation;
    }

    public LiveData<Hashtable<LocalDate, Integer>> getCalendarDates() {
        return dates;
    }
}
