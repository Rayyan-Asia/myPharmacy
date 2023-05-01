package com.example.mypharmacy.ui.menstrualCal;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mypharmacy.R;
import com.example.mypharmacy.data.local.entities.Menstruation;
import com.example.mypharmacy.data.local.repositories.MenstruationRepository;
import com.example.mypharmacy.data.local.repositories.impl.MenstruationRepositoryImpl;

import java.time.LocalDate;
import java.util.Calendar;


public class MenstrualCycleSurvey extends Fragment {
    private EditText startDate;
    private EditText endDate;

    private Button save;
    public static LocalDate START_DAY;
    public static LocalDate END_DAY;

    public MenstruationRepository menstruationRepository;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        menstruationRepository = new MenstruationRepositoryImpl(this.getContext());
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_menstrual_cycle_survey, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        initWidgets(view);
        setListeners();
    }

    private void initWidgets(View view) {
        startDate = view.findViewById(R.id.menstrual_start_date_button);
        endDate = view.findViewById(R.id.menstrual_end_date_button);
        save = view.findViewById(R.id.menstrual_save_button);
    }

    private void setListeners() {

        startDate.setOnClickListener(e -> {
            final Calendar c = Calendar.getInstance();
            int mYear = c.get(Calendar.YEAR); // current year
            int mMonth = c.get(Calendar.MONTH); // current month
            int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
            // date picker dialog
            DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                    new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                            // set day of month , month and year value in the edit text
                            startDate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            START_DAY = LocalDate.of(year, monthOfYear+1, dayOfMonth);
                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        });

        endDate.setOnClickListener(e -> {
            final Calendar c = Calendar.getInstance();
            int mYear = c.get(Calendar.YEAR); // current year
            int mMonth = c.get(Calendar.MONTH); // current month
            int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
            // date picker dialog
            DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                    new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                            // set day of month , month and year value in the edit text
                            endDate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            END_DAY = LocalDate.of(year, monthOfYear + 1, dayOfMonth);
                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        });

        save.setOnClickListener(e -> {
            if (END_DAY == null || START_DAY == null) {
                if (START_DAY == null) {
                    startDate.setError("Date Cannot be empty");
                } else {
                    endDate.setError("Date Cannot be empty");
                }
            } else {
                if (END_DAY.isAfter(START_DAY)) {
                    if(START_DAY.until(END_DAY).getDays() <= 9){
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                Menstruation menstruation = new Menstruation();
                                menstruation.startDate = START_DAY;
                                menstruation.endDate = END_DAY;
                                Looper.prepare();
                                menstruationRepository.insertMenstruation(menstruation);
                                Toast.makeText(getContext(), "Menstruation saved successfully", Toast.LENGTH_SHORT).show();
                                switchToCalendar();

                            }
                        }).start();

                    }
                    else {
                        endDate.setError("You may need to see a doctor with a period this long!");
                        Toast.makeText(getContext(), "You may need to see a doctor with a period this long!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    endDate.setError("End Date must be after Start Date.");
                    Toast.makeText(getContext(), "End Date must be after Start Date.", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void switchToCalendar() {
        Fragment fragment = new MenstrualCalendarFragment();
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace( R.id.fragment_container,fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }


}