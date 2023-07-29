package com.example.mypharmacy.ui.medRecord.appointment;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.example.mypharmacy.R;
import com.example.mypharmacy.data.local.entities.Appointment;
import com.example.mypharmacy.data.local.entities.AppointmentDrug;
import com.example.mypharmacy.data.local.entities.Doctor;
import com.example.mypharmacy.data.local.entities.Drug;
import com.example.mypharmacy.data.local.repositories.AppointmentDrugRepository;
import com.example.mypharmacy.data.local.repositories.AppointmentRepository;
import com.example.mypharmacy.data.local.repositories.DoctorRepository;
import com.example.mypharmacy.data.local.repositories.impl.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CreateAppointmentActivity extends AppCompatActivity {

    private static String FREQUENCY = "";
    private static LocalDate APPOINTMENT_DATE;
    private EditText titleEditText;
    private Spinner doctorSpinner;
    private Spinner drugSpinner1;
    private Spinner drugSpinner2;
    private Spinner drugSpinner3;
    private EditText symptomsEditText;
    private EditText diagnosisEditText;
    private EditText dateOfAppointmentEditText;

    private AppointmentRepository appointmentRepository;
    private DoctorRepository doctorRepository;
    private PersonRepositoryImpl personRepository;

    private AppointmentDrugRepository appointmentDrugRepository;
    private RadioGroup frequencyRadioGroup;
    private RadioButton radioButtonOne, radioButtonTwo, radioButtonThree;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_appointment);
        appointmentDrugRepository = new AppointmentDrugRepositoryImpl(this);
        appointmentRepository = new AppointmentRepositoryImpl(this);
        doctorRepository = new DoctorRepositoryImpl(this);
        personRepository = new PersonRepositoryImpl(this);
        drugSpinner1 = findViewById(R.id.spinner_drug_app_1);
        drugSpinner2 = findViewById(R.id.spinner_drug_app_2);
        drugSpinner3 = findViewById(R.id.spinner_drug_app_3);
        titleEditText = findViewById(R.id.edit_text_title);
        doctorSpinner = findViewById(R.id.spinner_doctor);
        symptomsEditText = findViewById(R.id.edit_text_symptoms);
        diagnosisEditText = findViewById(R.id.edit_text_diagnosis);
        dateOfAppointmentEditText = findViewById(R.id.edit_text_date_of_appointment);
        frequencyRadioGroup = findViewById(R.id.radio_group_frequency_app);
        radioButtonOne = findViewById(R.id.radio_button_one);
        radioButtonTwo = findViewById(R.id.radio_button_two);
        radioButtonThree = findViewById(R.id.radio_button_three);
        frequencyRadioGroup.setOnCheckedChangeListener((group, checkedId) -> updateEditTextVisibility(checkedId));
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
                populateDrugSpinner();
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
                Drug selectedDrug = (Drug) drugSpinner1.getSelectedItem();
                Drug selectedDrug2 = (Drug) drugSpinner2.getSelectedItem();
                Drug selectedDrug3 = (Drug) drugSpinner3.getSelectedItem();

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
                        long id = appointmentRepository.insertAppointment(appointment);

                        AppointmentDrug appointmentDrug = new AppointmentDrug();
                        appointmentDrug.setAppointmentId((int) id);
                        appointmentDrug.setDrugId(selectedDrug.getId());
                        appointmentDrugRepository.insert(appointmentDrug);

                        AppointmentDrug appointmentDrug2 = new AppointmentDrug();
                        appointmentDrug2.setAppointmentId((int) id);
                        appointmentDrug2.setDrugId(selectedDrug2.getId());
                        appointmentDrugRepository.insert(appointmentDrug2);

                        AppointmentDrug appointmentDrug3 = new AppointmentDrug();
                        appointmentDrug3.setAppointmentId((int) id);
                        appointmentDrug3.setDrugId(selectedDrug3.getId());
                        appointmentDrugRepository.insert(appointmentDrug3);
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

    private void populateDrugSpinner() {
        // retrieve drugs from database
        List<Drug> drugs = new DrugRepositoryImpl(this).getAllDrugs();
        List<Drug> drugs2 = new DrugRepositoryImpl(this).getAllDrugs();
        List<Drug> drugs3 = new DrugRepositoryImpl(this).getAllDrugs();
        // create a list of drug names
        List<String> drugNames = new ArrayList<>();
        for (Drug drug : drugs) {
            drugNames.add(drug.getName());
        }
        // create an adapter and set it to the spinner
        ArrayAdapter<Drug> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, drugs);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ArrayAdapter<Drug> adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, drugs2);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ArrayAdapter<Drug> adapter3 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, drugs3);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        drugSpinner1.setAdapter(adapter);
        drugSpinner2.setAdapter(adapter2);
        drugSpinner3.setAdapter(adapter3);


    }


    @SuppressLint("NonConstantResourceId")
    private void updateEditTextVisibility(int checkedId) {
        switch (checkedId) {
            case R.id.radio_button_one:
                FREQUENCY = "One Drug";
                drugSpinner1.setVisibility(View.VISIBLE);
                drugSpinner2.setVisibility(View.GONE);
                drugSpinner3.setVisibility(View.GONE);
                break;
            case R.id.radio_button_two:
                FREQUENCY = "Two Drug";
                drugSpinner1.setVisibility(View.VISIBLE);
                drugSpinner2.setVisibility(View.VISIBLE);
                drugSpinner3.setVisibility(View.GONE);
                break;
            case R.id.radio_button_three:
                FREQUENCY = "Three Drug";
                drugSpinner1.setVisibility(View.VISIBLE);
                drugSpinner2.setVisibility(View.VISIBLE);
                drugSpinner3.setVisibility(View.VISIBLE);
                break;
        }
    }
}

