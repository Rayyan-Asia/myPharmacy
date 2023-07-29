package com.example.mypharmacy;

import android.app.Application;

import com.example.mypharmacy.data.local.entities.Appointment;
import com.example.mypharmacy.data.local.entities.Drug;
import com.example.mypharmacy.data.local.repositories.AppointmentRepository;
import com.example.mypharmacy.data.local.repositories.DrugRepository;
import com.example.mypharmacy.data.local.repositories.impl.AppointmentRepositoryImpl;
import com.example.mypharmacy.data.local.repositories.impl.DrugRepositoryImpl;
import com.facebook.stetho.Stetho;

import java.time.LocalDate;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// appComponent lives in the Application class to share its lifecycle
public class MyApplication extends Application {
    public void onCreate() {
        super.onCreate();
        DrugRepository drugRepository = new DrugRepositoryImpl(this);
        AppointmentRepository appointmentRepository = new AppointmentRepositoryImpl(this);
        new Thread(new Runnable() {
            @Override
            public void run() {
                Drug drug1 = new Drug();
                drug1.setName("Aspirin");
                drug1.setDescription("Pain reliever and fever reducer");
                drug1.setManufacturer("Bayer");
                drug1.setCategory("Analgesic");
                drug1.setType("Tablet");
                drug1.setExpiryDate(LocalDate.of(2024, 6, 30));

                Drug drug2 = new Drug();
                drug2.setName("Amoxicillin");
                drug2.setDescription("Antibiotic");
                drug2.setManufacturer("Pfizer");
                drug2.setCategory("Antibiotic");
                drug2.setType("Capsule");
                drug2.setExpiryDate(LocalDate.of(2023, 12, 31));

                Drug drug3 = new Drug();
                drug3.setName("Lisinopril");
                drug3.setDescription("Anti-hypertensive");
                drug3.setManufacturer("Novartis");
                drug3.setCategory("Antihypertensive");
                drug3.setType("Tablet");
                drug3.setExpiryDate(LocalDate.of(2025, 8, 15));

                Drug drug4 = new Drug();
                drug4.setName("Paracetamol");
                drug4.setDescription("Pain reliever and fever reducer");
                drug4.setManufacturer("Johnson & Johnson");
                drug4.setCategory("Analgesic");
                drug4.setType("Syrup");
                drug4.setExpiryDate(LocalDate.of(2023, 9, 30));

                Drug drug5 = new Drug();
                drug5.setName("Ibuprofen");
                drug5.setDescription("Pain reliever and fever reducer");
                drug5.setManufacturer("GSK");
                drug5.setCategory("Analgesic");
                drug5.setType("Tablet");
                drug5.setExpiryDate(LocalDate.of(2024, 5, 31));

                Drug drug6 = new Drug();
                drug6.setName("Lisinopril");
                drug6.setDescription("ACE inhibitor for hypertension");
                drug6.setManufacturer("Novartis");
                drug6.setCategory("Antihypertensive");
                drug6.setType("Tablet");
                drug6.setExpiryDate(LocalDate.of(2023, 11, 3));

                Drug drug7 = new Drug();
                drug7.setName("Cetirizine");
                drug7.setDescription("Antihistamine for allergies");
                drug7.setManufacturer("Bayer");
                drug7.setCategory("Antiallergic");
                drug7.setType("Tablet");
                drug7.setExpiryDate(LocalDate.of(2023, 8, 31));

                Drug drug8 = new Drug();
                drug8.setName("Sertraline");
                drug8.setDescription("Selective serotonin reuptake inhibitor");
                drug8.setManufacturer("GSK");
                drug8.setCategory("Antidepressant");
                drug8.setType("Tablet");
                drug8.setExpiryDate(LocalDate.of(2024, 4, 30));

                Drug drug9 = new Drug();
                drug9.setName("Azithromycin");
                drug9.setDescription("Macrolide antibiotic");
                drug9.setManufacturer("Pfizer");
                drug9.setCategory("Antibiotic");
                drug9.setType("Tablet");
                drug9.setExpiryDate(LocalDate.of(2024, 9, 15));

                Drug drug10 = new Drug();
                drug10.setName("Amlodipine");
                drug10.setDescription("Calcium channel blocker for hypertension");
                drug10.setManufacturer("Novartis");
                drug10.setCategory("Antihypertensive");
                drug10.setType("Tablet");
                drug10.setExpiryDate(LocalDate.of(2023, 10, 31));

                Drug drug11 = new Drug();
                drug11.setName("Loratadine");
                drug11.setDescription("Antihistamine for allergies");
                drug11.setManufacturer("Johnson & Johnson");
                drug11.setCategory("Antiallergic");
                drug11.setType("Tablet");
                drug11.setExpiryDate(LocalDate.of(2023, 5, 20));

                Drug drug12 = new Drug();
                drug12.setName("Fluoxetine");
                drug12.setDescription("Selective serotonin reuptake inhibitor");
                drug12.setManufacturer("Eli Lilly and Company");
                drug12.setCategory("Antidepressant");
                drug12.setType("Capsule");
                drug12.setExpiryDate(LocalDate.of(2024, 5, 20));

                // Insert drugs into the repository
                if(drugRepository.getDrug(1) == null) {
                    drugRepository.insertDrug(drug1);
                    drugRepository.insertDrug(drug2);
                    drugRepository.insertDrug(drug3);
                    drugRepository.insertDrug(drug4);
                    drugRepository.insertDrug(drug5);
                    drugRepository.insertDrug(drug6);
                    drugRepository.insertDrug(drug7);
                    drugRepository.insertDrug(drug8);
                    drugRepository.insertDrug(drug9);
                    drugRepository.insertDrug(drug10);
                    drugRepository.insertDrug(drug11);
                    drugRepository.insertDrug(drug12);
                }
            }
        }).start();
    }
}

