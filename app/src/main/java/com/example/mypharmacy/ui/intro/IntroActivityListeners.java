package com.example.mypharmacy.ui.intro;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.widget.*;
import com.example.mypharmacy.R;
import com.example.mypharmacy.data.local.entities.Person;
import com.example.mypharmacy.ui.home.HomeFragment;
import com.example.mypharmacy.ui.menu.MenuActivity;

import java.time.LocalDate;
import java.util.Calendar;

public class IntroActivityListeners {
    private final IntroActivity activity;
    private final EditText birthDayField;


    public IntroActivityListeners(IntroActivity activity) {
        this.activity = activity;
        this.birthDayField = activity.findViewById(R.id.birth_day_field);
        initListener();
    }

    private void initListener() {

    }
}
