package com.example.mypharmacy.data.local;

import android.content.ContentValues;
import android.content.Context;
import androidx.annotation.NonNull;
import androidx.room.*;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;
import com.example.mypharmacy.data.local.daos.*;
import com.example.mypharmacy.data.local.entities.*;

import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;

@Database(entities = {Person.class, Doctor.class, Drug.class, Prescription.class, Document.class,
        Appointment.class, LabTest.class, DocumentTest.class, AppointmentPrescription.class,
          Menstruation.class},
        version = 9, autoMigrations = { @AutoMigration(from = 1, to = 2), @AutoMigration(from = 3, to = 4), @AutoMigration(from = 5, to = 6),
        @AutoMigration(from = 6, to = 7), @AutoMigration(from = 7, to = 8)})
public abstract class myPharmacyDatabase extends RoomDatabase {
     private static final String DB_NAME = "myPharmacy.db";
     private static myPharmacyDatabase instance;


     public abstract PersonDao getPersonDao();
     public abstract DoctorDao getDoctorDao();
     public abstract DrugDao getDrugDao();
     public abstract PrescriptionDao getPrescriptionDao();
     public abstract AppointmentDao getAppointmentDao();
     public abstract AppointmentPrescriptionDao getAppointmentPrescriptionDao();
     public abstract MenstruationDao getMenstruationDao();

     public abstract LabTestDao getLabTestDao();


     public static synchronized myPharmacyDatabase getInstance(Context context) {
          if (instance == null) {
               instance = Room.databaseBuilder(context, myPharmacyDatabase.class, DB_NAME)
                       .addMigrations(SEED_DRUG_TABLE,SEED_ENTITIES)
                       .build();
          }
          return instance;
     }
     public static final Migration SEED_DRUG_TABLE = new Migration(2, 3) {
          @Override
          public void migrate(SupportSQLiteDatabase database) {
               database.execSQL("CREATE TABLE drug_new (id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, name TEXT, description TEXT, manufacturer TEXT, category TEXT, type TEXT, expiry_date INTEGER)");

               LocalDate now = LocalDate.now();

               database.execSQL("INSERT INTO drug_new (name, description, manufacturer, category, type, expiry_date) VALUES ('Amoxicillin', 'An antibiotic used to treat bacterial infections', 'Pfizer', 'Antibiotic', 'Capsule', 1681474136)");
               database.execSQL("INSERT INTO drug_new (name, description, manufacturer, category, type, expiry_date) VALUES ('Paracetamol', 'An analgesic used to relieve pain and reduce fever', 'Johnson & Johnson', 'Analgesic', 'Tablet', 1681474136)");
               database.execSQL("INSERT INTO drug_new (name, description, manufacturer, category, type, expiry_date) VALUES ('Lisinopril-AstraZeneca', 'An antihypertensive used to treat high blood pressure', 'AstraZeneca', 'Antihypertensive', 'Tablet', 1681474136)");
               database.execSQL("INSERT INTO drug_new (name, description, manufacturer, category, type, expiry_date) VALUES ('Ibuprofen-Bayer', 'An analgesic used to relieve pain and reduce fever', 'Bayer', 'Analgesic', 'Tablet', 1681474136)");
               database.execSQL("INSERT INTO drug_new (name, description, manufacturer, category, type, expiry_date) VALUES ('Ceftriaxone', 'An antibiotic used to treat bacterial infections', 'Roche', 'Antibiotic', 'Injection', 1681474136)");
               database.execSQL("INSERT INTO drug_new (name, description, manufacturer, category, type, expiry_date) VALUES ('Omeprazole-Pfizer', 'A proton-pump inhibitor used to treat gastroesophageal reflux disease', 'Pfizer', 'Gastrointestinal', 'Capsule', 1681474136)");


               database.execSQL("DROP TABLE drug");
               database.execSQL("ALTER TABLE drug_new RENAME TO drug");
          }
     };
     public static final Migration SEED_ENTITIES = new Migration(4, 5) {

          @Override
          public void migrate(@NonNull @NotNull SupportSQLiteDatabase database) {
               database.execSQL("INSERT INTO person (first_name, last_name, gender, address, weight, height, phone_number, birth_date, marital_status, blood_type) VALUES ('John', 'Doe', 'Male', 'Sharafa', 30, 30, 0595874752, 1681474136, 'Single', 'O+'  )");
               //add dummy doctors
               database.execSQL("INSERT INTO doctor (name, specialty, phone, email, clinical_address) VALUES ('John Doe', 'Cardiologist', 1234567890, 'johndoe@email.com', '123 Main St')");
               database.execSQL("INSERT INTO doctor (name, specialty, phone, email, clinical_address) VALUES ('Jane Smith', 'Pediatrician', 2345678901, 'janesmith@email.com', '456 Oak Ave')");

               //adding prescriptions
               database.execSQL("INSERT INTO prescription (name, description, dosage, frequency, start_date, end_date, doctor_id, drug_id) VALUES ('Paracetemol', 'For pain and fever', '100mg', 'twice a day', '2023-04-11', '2023-04-20', 1, 2 )");
               database.execSQL("INSERT INTO prescription (name, description, dosage, frequency, start_date, end_date, doctor_id, drug_id) VALUES ('Amoxicillin', 'Antibiotic', '500mg', 'three times a day', '2023-04-10', '2023-04-17', 2, 1)");

               //adding appointments
               database.execSQL("INSERT INTO appointment (title, doctor_id, person_id, symptoms, diagnosis, date_of_appointment) VALUES ('I got a Fever', 1, 1, 'Headache', 'Migraine', '2023-04-12')");
               database.execSQL("INSERT INTO appointment (title, doctor_id, person_id, symptoms, diagnosis, date_of_appointment) VALUES ('I have the flu', 2, 1, 'Sore throat', 'Strep throat', '2023-04-14')");

               database.execSQL("INSERT INTO appointment_prescription (appointment_id, prescription_id) VALUES (1,1)");
               database.execSQL("INSERT INTO appointment_prescription (appointment_id, prescription_id) VALUES (2,2)");

          }
     };
}







