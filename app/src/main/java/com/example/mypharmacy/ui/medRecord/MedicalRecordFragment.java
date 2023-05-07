package com.example.mypharmacy.ui.medRecord;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import com.example.mypharmacy.R;
import com.example.mypharmacy.ui.medRecord.appointment.AppointmentCardActivity;
import com.example.mypharmacy.ui.medRecord.doctor.DoctorCardActivity;
import com.example.mypharmacy.ui.medRecord.drug.DrugCardActivity;

public class MedicalRecordFragment extends Fragment {

    public MedicalRecordFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_medical_records, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Find the CardView views
        CardView doctorCard = view.findViewById(R.id.doctor_card);
        CardView drugCard = view.findViewById(R.id.drug_card);
        CardView appointmentCard = view.findViewById(R.id.appointment_card);
        CardView labTestsCard = view.findViewById(R.id.lab_tests_card);

        // Set OnClickListener for each CardView
        doctorCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), DoctorCardActivity.class));
            }
        });

        drugCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), DrugCardActivity.class));
            }
        });

        appointmentCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), AppointmentCardActivity.class));
            }
        });

        labTestsCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle labTestsCard click event
                // For example, you can start a new activity or fragment
                // to display information about lab tests
            }
        });
    }
}
