package com.example.mypharmacy.ui.medRecord;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mypharmacy.R;
import com.example.mypharmacy.data.local.entities.*;
import com.example.mypharmacy.data.local.repositories.*;
import com.example.mypharmacy.data.local.repositories.impl.*;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Calendar;
import java.util.List;
import android.util.Patterns;
public class AddAppointmentActivity extends AppCompatActivity {

    private static LocalDate END_DATE;
    private static LocalDate START_DATE;
    private static LocalDate APPOINTMENT_DATE;
    private EditText titleEditText;
    private EditText symptomEditText;
    private EditText diagnosisEditText;
    private EditText dateEditText;
    private Spinner doctorSpinner;
    private Spinner drugSpinner;
    private EditText nameEditText;
    private EditText specialtyEditText;
    private EditText phoneEditText;
    private EditText emailEditText;
    private EditText clinicalAddressText;
    private EditText prescriptionNameEditText;
    private EditText prescriptionDescriptionEditText;
    private EditText dosageEditText;
    private EditText frequencyEditText;
    private EditText startDateEditText;
    private EditText endDateEditText;
    private DoctorRepository doctorRepository;
    private DrugRepository drugRepository;
    private PrescriptionRepository prescriptionRepository;
    private AppointmentRepository appointmentRepository;
    private AppointmentPrescriptionRepository appointmentPrescriptionRepository;
    private RadioGroup doctorRadioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_medical_record);

        titleEditText = findViewById(R.id.titleEditText);
        symptomEditText = findViewById(R.id.symptomsEditText);
        diagnosisEditText = findViewById(R.id.diagnosisEditText);
        dateEditText = findViewById(R.id.dateEditText);
        doctorRadioGroup = findViewById(R.id.doctorRadioGroup);
        dateEditText.setOnClickListener(e -> {
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
                            dateEditText.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            APPOINTMENT_DATE = LocalDate.of(year, monthOfYear, dayOfMonth);
                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        });
        doctorSpinner = findViewById(R.id.doctorSpinner);
        nameEditText = findViewById(R.id.nameEditText);
        specialtyEditText = findViewById(R.id.specialtyEditText);
        phoneEditText = findViewById(R.id.phoneEditText);
        emailEditText = findViewById(R.id.emailEditText);
        clinicalAddressText = findViewById(R.id.clinicalAddressEditText);

        // find the radio group
        RadioGroup doctorRadioGroup = findViewById(R.id.doctorRadioGroup);

// set a listener for the radio group
        doctorRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // find the selected radio button
                RadioButton radioButton = group.findViewById(checkedId);

                // get the selected doctor layout
                String selectedLayout = radioButton.getText().toString();

                // find the doctor layout views
                View existingDoctorLayout = findViewById(R.id.existingDoctorLinearLayout);
                View newDoctorLayout = findViewById(R.id.newDoctorLinearLayout);

                // show or hide the doctor layouts based on the selected layout
                if (selectedLayout.equals("Choose existing doctor")) {
                    existingDoctorLayout.setVisibility(View.VISIBLE);
                    newDoctorLayout.setVisibility(View.GONE);
                } else {
                    existingDoctorLayout.setVisibility(View.GONE);
                    newDoctorLayout.setVisibility(View.VISIBLE);
                }
            }
        });

        Button addButton = findViewById(R.id.add_prescription_button);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddPrescriptionDialog();
            }
        });


        doctorRepository = new DoctorRepositoryImpl(getApplication());
        drugRepository = new DrugRepositoryImpl(getApplication());
        prescriptionRepository = new PrescriptionRepositoryImpl(getApplication());
        appointmentRepository = new AppointmentRepositoryImpl(getApplication());
        appointmentPrescriptionRepository = new AppointmentPrescriptionRepositoryImpl(getApplication());

        // Populate the doctor spinner with available doctors
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<Doctor> doctors = doctorRepository.getAllDoctors();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ArrayAdapter<Doctor> doctorAdapter = new ArrayAdapter<>(AddAppointmentActivity.this,
                                android.R.layout.simple_spinner_item, doctors);
                        doctorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        doctorSpinner.setAdapter(doctorAdapter);
                    }
                });
            }
        }).start();

    }

    public void onSaveButtonClick(View view) {
        String title = titleEditText.getText().toString();
        String symptom = symptomEditText.getText().toString();
        String diagnosis = diagnosisEditText.getText().toString();
        String dateString = dateEditText.getText().toString();
        Doctor doctor = null;
        Drug drug = null;
        String doctorName = "";
        String doctorSpecialty = "";
        int doctorPhone = 0;
        String doctorEmail = "";
        String clinicalAddress = "";

        int check=0;
        // Validation for the appointment fields
        if (title.trim().isEmpty()) {
            titleEditText.setError("Title is required");
            check = 1;
        }
        if (dateString.trim().isEmpty()) {
            dateEditText.setError("Date is required");
            check = 1;
        }

        // Validation for the doctor fields
        if (doctorRadioGroup.getCheckedRadioButtonId() == R.id.existingDoctorRadioButton) {
            if (doctorSpinner.getSelectedItemPosition() == 0) {
                Toast.makeText(this, "Please select a doctor", Toast.LENGTH_SHORT).show();
                check = 1;
            }
            doctor = (Doctor) doctorSpinner.getSelectedItem();
        } else if (doctorRadioGroup.getCheckedRadioButtonId() == R.id.newDoctorRadioButton) {
            doctorName = nameEditText.getText().toString().trim();
            doctorSpecialty = specialtyEditText.getText().toString().trim();
            String phoneString = phoneEditText.getText().toString().trim();
            if (doctorName.isEmpty()) {
                nameEditText.setError("Name is required");
                check = 1;
            }
            doctor = new Doctor();
            doctor.setName(doctorName);
            doctor.setSpecialty(doctorSpecialty);
            doctor.setPhone(doctorPhone);
            doctor.setEmail(doctorEmail);
            doctor.setClinicalAddress(clinicalAddress);
        }
        if(check == 0) {
            //Create appointment
            Appointment appointment = new Appointment();
            appointment.setTitle(title);
            appointment.setDoctorId(doctor.getId());
            appointment.setSymptoms(symptom);
            appointment.setDiagnosis(diagnosis);
            appointment.setDateOfAppointment(APPOINTMENT_DATE);
            // Save the appointment to the database
            long appointmentID = appointmentRepository.insertAppointment(appointment);

            //add appointment prescription
            AppointmentPrescription appointmentPrescription = new AppointmentPrescription();
            appointmentPrescription.setAppointmentId((int) appointmentID);
            //appointmentPrescription.setPrescriptionId((int) prescriptionID);
            appointmentPrescriptionRepository.insert(appointmentPrescription);

            // Show a confirmation message
            Toast.makeText(this, "Appointment saved", Toast.LENGTH_SHORT).show();
            finish();
        }
    }
    private void showAddPrescriptionDialog() {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.activity_add_prescription_dialog_layout);

        drugSpinner = dialog.findViewById(R.id.drugSpinner);
        prescriptionNameEditText = dialog.findViewById(R.id.prescriptionNameEditText);
        prescriptionDescriptionEditText = dialog.findViewById(R.id.prescriptionDescriptionEditText);
        dosageEditText = dialog.findViewById(R.id.dosageEditText);
        frequencyEditText = dialog.findViewById(R.id.frequencyEditText);
        startDateEditText = dialog.findViewById(R.id.startDateEditText);
        startDateEditText.setOnClickListener(e -> {
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
                            startDateEditText.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            START_DATE = LocalDate.of(year, monthOfYear, dayOfMonth);
                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        });
        endDateEditText = dialog.findViewById(R.id.endDateEditText);
        endDateEditText.setOnClickListener(e -> {
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
                            endDateEditText.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            END_DATE = LocalDate.of(year, monthOfYear, dayOfMonth);
                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        });
        new Thread(new Runnable() {
            @Override
            public void run() {

                runOnUiThread(new Runnable() {
                    List<Drug> drugs = drugRepository.getAllDrugs();
                    @Override
                    public void run() {
                        ArrayAdapter<Drug> drugAdapter = new ArrayAdapter<>(AddAppointmentActivity.this,
                                android.R.layout.simple_spinner_item, drugs);
                        drugAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        drugSpinner.setAdapter(drugAdapter);
                    }
                });
            }
        }).start();
        Button addButton = dialog.findViewById(R.id.add_prescription_button_dialog);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String startDateString = startDateEditText.getText().toString();
                String endDateString = startDateEditText.getText().toString();
                String prescriptionName = prescriptionNameEditText.getText().toString();
                String prescriptionDescription = prescriptionDescriptionEditText.getText().toString();
                String prescriptionDosage = dosageEditText.getText().toString();
                String prescriptionFrequency = frequencyEditText.getText().toString();
                Drug drug = null;

                int check = 0;
                if (prescriptionName.isEmpty()) {
                    prescriptionNameEditText.setError("Prescription name is required");
                    check = 1;
                }
                if (prescriptionDosage.isEmpty()) {
                    dosageEditText.setError("Prescription dosage is required");
                    check = 1;
                }
                if (prescriptionFrequency.isEmpty()) {
                    frequencyEditText.setError("Prescription frequency is required");
                    check = 1;
                }
                if (startDateString.trim().isEmpty()) {
                    startDateEditText.setError("Date is required");
                    check = 1;
                }
                if (endDateString.trim().isEmpty()) {
                    endDateEditText.setError("Date is required");
                    check = 1;
                }
                if(drugSpinner.getSelectedItem().toString().equals("")) {
                    drug = (Drug) drugSpinner.getSelectedItem();
                    check = 1;
                }
                if(check == 0) {
                    Prescription prescription = new Prescription();
                    prescription.setName(prescriptionName);
                    prescription.setDescription(prescriptionDescription);
                    prescription.setDosage(prescriptionDosage);
                    prescription.setFrequency(prescriptionFrequency);
                    prescription.setStartDate(START_DATE);
                    prescription.setEndDate(END_DATE);
                    prescription.setDrugId(drug.getId());
                    long prescriptionID = prescriptionRepository.insertPrescription(prescription);
                }
            }
        });
        dialog.show();
    }

}
