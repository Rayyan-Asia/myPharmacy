package com.example.mypharmacy.ui.medRecord.appointment;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.example.mypharmacy.R;
import com.example.mypharmacy.data.local.entities.Appointment;
import com.example.mypharmacy.data.local.entities.Doctor;
import com.example.mypharmacy.data.local.repositories.AppointmentRepository;
import com.example.mypharmacy.data.local.repositories.DoctorRepository;
import com.example.mypharmacy.data.local.repositories.impl.AppointmentRepositoryImpl;
import com.example.mypharmacy.data.local.repositories.impl.DoctorRepositoryImpl;
import com.example.mypharmacy.data.local.repositories.impl.PersonRepositoryImpl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CreateAppointmentActivity extends AppCompatActivity {

    private static LocalDate APPOINTMENT_DATE;
    private EditText titleEditText;
    private Spinner doctorSpinner;
    private EditText symptomsEditText;
    private EditText diagnosisEditText;
    private EditText dateOfAppointmentEditText;

    private AppointmentRepository appointmentRepository;
    private DoctorRepository doctorRepository;
    private PersonRepositoryImpl personRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_appointment);

        appointmentRepository = new AppointmentRepositoryImpl(this);
        doctorRepository = new DoctorRepositoryImpl(this);
        personRepository = new PersonRepositoryImpl(this);

        titleEditText = findViewById(R.id.edit_text_title);
        doctorSpinner = findViewById(R.id.spinner_doctor);
        symptomsEditText = findViewById(R.id.edit_text_symptoms);
        diagnosisEditText = findViewById(R.id.edit_text_diagnosis);
        dateOfAppointmentEditText = findViewById(R.id.edit_text_date_of_appointment);
        dateOfAppointmentEditText.setOnClickListener(e -> {
            final Calendar c = Calendar.getInstance();
            int mYear = c.get(Calendar.YEAR); // current year
            int mMonth = c.get(Calendar.MONTH); // current month
            int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
            // date picker dialog
            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                            // set day of month , month and year value in the edit text
                            dateOfAppointmentEditText.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            APPOINTMENT_DATE = LocalDate.of(year, monthOfYear, dayOfMonth);
                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        });

        // Populating the spinner with doctors
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<Doctor> doctors = doctorRepository.getAllDoctors();
                ArrayAdapter<Doctor> adapter = new ArrayAdapter<>(CreateAppointmentActivity.this, android.R.layout.simple_spinner_item, doctors);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                doctorSpinner.setAdapter(adapter);
            }
        }).start();

        Button createAppointmentButton = findViewById(R.id.button_create_appointment);
        createAppointmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Validating input fields
                String title = titleEditText.getText().toString().trim();
                if (title.isEmpty()) {
                    titleEditText.setError("Please enter appointment title");
                }

                String symptoms = symptomsEditText.getText().toString().trim();
                if (symptoms.isEmpty()) {
                    symptomsEditText.setError("Please enter symptoms");
                }

                String diagnosis = diagnosisEditText.getText().toString().trim();
                if (diagnosis.isEmpty()) {
                    diagnosisEditText.setError("Please enter diagnosis");
                }

                String dateOfAppointment = dateOfAppointmentEditText.getText().toString().trim();
                if (dateOfAppointment.isEmpty()) {
                    dateOfAppointmentEditText.setError("Please select date of appointment");
                }

                // Checking for errors
                if (title.isEmpty() || symptoms.isEmpty() || diagnosis.isEmpty() || dateOfAppointment.isEmpty()) {
                    return;
                }

                // Creating the appointment
                Doctor selectedDoctor = (Doctor) doctorSpinner.getSelectedItem();
                Appointment appointment = new Appointment();
                appointment.setTitle(title);
                appointment.setDoctorId(selectedDoctor.getId());
                appointment.setSymptoms(symptoms);
                appointment.setDiagnosis(diagnosis);
                appointment.setDateOfAppointment(APPOINTMENT_DATE);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        appointment.setPersonId(personRepository.getPerson().getId());
                        appointmentRepository.insertAppointment(appointment);
                    }
                }).start();


                // Showing success message
                Toast.makeText(getApplicationContext(), "Appointment created successfully", Toast.LENGTH_SHORT).show();
                Intent resultIntent = new Intent();
                setResult(RESULT_OK, resultIntent);
                finish();
            }
        });

    }
}

