package com.example.mypharmacy.ui.medReminder;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.mypharmacy.R;
import com.example.mypharmacy.data.local.entities.Reminder;
import com.example.mypharmacy.data.local.repositories.ReminderRepository;
import com.example.mypharmacy.data.local.repositories.impl.ReminderRepositoryImplementation;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class MedicationReminderFragment extends Fragment {
    ReminderAdapter reminderAdapter;
    RecyclerView reminderRecyclerView;
    private ReminderRepository reminderRepository;
    private FloatingActionButton addReminderButton;
    private ActivityResultLauncher<Intent> addReminderLauncher;

    public MedicationReminderFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_medication_reminder, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        reminderRepository = new ReminderRepositoryImplementation(view.getContext());
        reminderRecyclerView = view.findViewById(R.id.reminder_list);
        addReminderLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if ((result.getResultCode() == AppCompatActivity.RESULT_OK)) {
                // Refresh the list by updating the adapter and notifying it that the data set has changed
                new Thread(() -> {
                    List<Reminder> reminderList = reminderRepository.getActiveReminders();
                    getActivity().runOnUiThread(() -> {
                        reminderAdapter.updateData(reminderList);
                        reminderAdapter.notifyDataSetChanged();
                    });
                }).start();
            }
        });
        new Thread(() -> {
            List<Reminder> reminderList = reminderRepository.getActiveReminders();
            reminderAdapter = new ReminderAdapter(reminderList);
            reminderRecyclerView.setAdapter(reminderAdapter);
        }).start();
        reminderRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        addReminderButton = view.findViewById(R.id.add_reminder_button);
        addReminderButton.setOnClickListener(e -> {
            Intent intent = new Intent(view.getContext(), CreateReminderActivity.class);
            addReminderLauncher.launch(intent);
        });
    }
}

