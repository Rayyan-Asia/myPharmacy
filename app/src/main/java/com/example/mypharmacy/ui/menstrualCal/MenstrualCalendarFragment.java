package com.example.mypharmacy.ui.menstrualCal;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

import java.time.LocalDate;

public class MenstrualCalendarFragment extends Fragment {

    private Button editButton;
    private LocalDate currentDate = LocalDate.now();
    private MutableLiveData<Menstruation> menstruation = new MutableLiveData<>();
    private MenstrualCycleCalendarView calendarView;

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

        this.getData().observe(getViewLifecycleOwner(), new Observer<Menstruation>() {
            @Override
            public void onChanged(Menstruation menstruation) {
                if (menstruation == null) {
                    switchToSurvey();
                } else if (!isSameMonth(currentDate, menstruation.endDate) &&
                        !isSameMonth(currentDate, menstruation.startDate) &&
                        menstruation.endDate.until(currentDate).getDays() > 35) {
                    Toast.makeText(getContext(), "No Entry in Previous Month", Toast.LENGTH_LONG).show();
                    switchToSurvey();
                }
            }
        });
    }

    private void switchToSurvey() {
        Fragment fragment = new MenstrualCycleSurvey();
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
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

    private void setListeners() {
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToSurvey();
            }
        });
    }

    private boolean isSameMonth(LocalDate date, LocalDate selectedDate) {
        return date.getMonth().equals(selectedDate.getMonth()) && date.getYear() == selectedDate.getYear();
    }

    private void initWidgets(View view) {
        currentDate = LocalDate.now();
        editButton = view.findViewById(R.id.EditMenstruationButton);
        calendarView = view.findViewById(R.id.menstrualcalender);
        MenstruationRepository menstruationRepository = new MenstruationRepositoryImpl(requireContext());
        calendarView.setMenstruationRepository(menstruationRepository);
    }

    public LiveData<Menstruation> getData() {
        return menstruation;
    }
}
