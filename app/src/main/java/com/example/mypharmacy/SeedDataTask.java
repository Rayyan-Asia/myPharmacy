package com.example.mypharmacy;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Environment;

import com.example.mypharmacy.data.local.entities.Appointment;
import com.example.mypharmacy.data.local.entities.Doctor;
import com.example.mypharmacy.data.local.entities.Drug;
import com.example.mypharmacy.data.local.entities.DrugAlias;
import com.example.mypharmacy.data.local.entities.DrugConflict;
import com.example.mypharmacy.data.local.entities.Family;
import com.example.mypharmacy.data.local.entities.FamilyMember;
import com.example.mypharmacy.data.local.entities.Person;
import com.example.mypharmacy.data.local.enums.DoctorSpecialty;
import com.example.mypharmacy.data.local.repositories.AppointmentRepository;
import com.example.mypharmacy.data.local.repositories.DoctorRepository;
import com.example.mypharmacy.data.local.repositories.DrugAliasRepository;
import com.example.mypharmacy.data.local.repositories.DrugConflictRepository;
import com.example.mypharmacy.data.local.repositories.DrugRepository;
import com.example.mypharmacy.data.local.repositories.FamilyRepository;
import com.example.mypharmacy.data.local.repositories.PersonRepository;
import com.example.mypharmacy.data.local.repositories.impl.AppointmentRepositoryImpl;
import com.example.mypharmacy.data.local.repositories.impl.DoctorRepositoryImpl;
import com.example.mypharmacy.data.local.repositories.impl.DrugAliasRepositoryImpl;
import com.example.mypharmacy.data.local.repositories.impl.DrugConflictRepositoryImpl;
import com.example.mypharmacy.data.local.repositories.impl.DrugRepositoryImpl;
import com.example.mypharmacy.data.local.repositories.impl.FamilyRepositoryImpl;
import com.example.mypharmacy.data.local.repositories.impl.PersonRepositoryImpl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.Locale;

public class SeedDataTask extends AsyncTask<Void, Void, Void> {
    private Context context;
    private DrugRepository drugRepository;
    private DrugAliasRepository drugAliasRepository;
    private DrugConflictRepository drugConflictRepository;
    private DoctorRepository doctorRepository;
    private FamilyRepository familyRepository;
    private PersonRepository personRepository;

    private AppointmentRepository appointmentRepository;

    public SeedDataTask(Context context) {
        this.context = context;
        drugRepository = new DrugRepositoryImpl(context);
        drugAliasRepository = new DrugAliasRepositoryImpl(context);
        drugConflictRepository = new DrugConflictRepositoryImpl(context);
        familyRepository = new FamilyRepositoryImpl(context);
        personRepository = new PersonRepositoryImpl(context);
        doctorRepository = new DoctorRepositoryImpl(context);
        appointmentRepository = new AppointmentRepositoryImpl(context);
    }

    @Override
    protected Void doInBackground(Void... voids) {
        if (drugRepository.getDrug(1) == null) {
            seedDrugs();
            seedDrugAliases();
            seedDrugConflicts();
        }
        if (personRepository.getPerson() == null){
            seedPeople();
        }
        if (familyRepository.getAllFamilies().size() == 0){
            seedFamily();
        }
        if(doctorRepository.getAllDoctors().size() == 0)
            seedDoctors();
        if(appointmentRepository.getAllAppointments().size() ==0)
            seedAppointments();
        return null;
    }

    private void seedDrugs() {
        Drug drug1 = new Drug();
        drug1.id =1;
        drug1.setName("Aspirin");
        drug1.setDescription("Pain reliever and fever reducer");
        drug1.setManufacturer("Bayer");
        drug1.setCategory("Analgesic");
        drug1.setType("Tablet");
        drug1.setExpiryDate(LocalDate.of(2024, 6, 30));

        Drug drug2 = new Drug();
        drug2.id =2;
        drug2.setName("Amoxicillin");
        drug2.setDescription("Antibiotic");
        drug2.setManufacturer("Pfizer");
        drug2.setCategory("Antibiotic");
        drug2.setType("Capsule");
        drug2.setExpiryDate(LocalDate.of(2023, 12, 31));

        Drug drug3 = new Drug();
        drug3.id =3;
        drug3.setName("Lisinopril");
        drug3.setDescription("Anti-hypertensive");
        drug3.setManufacturer("Novartis");
        drug3.setCategory("Antihypertensive");
        drug3.setType("Tablet");
        drug3.setExpiryDate(LocalDate.of(2025, 8, 15));

        Drug drug4 = new Drug();
        drug4.id =4;
        drug4.setName("Paracetamol");
        drug4.setDescription("Pain reliever and fever reducer");
        drug4.setManufacturer("Johnson & Johnson");
        drug4.setCategory("Analgesic");
        drug4.setType("Syrup");
        drug4.setExpiryDate(LocalDate.of(2023, 9, 30));


        Drug drug5 = new Drug();
        drug5.id =5;
        drug5.setName("Ibuprofen");
        drug5.setDescription("Pain reliever and fever reducer");
        drug5.setManufacturer("GSK");
        drug5.setCategory("Analgesic");
        drug5.setType("Tablet");
        drug5.setExpiryDate(LocalDate.of(2024, 5, 31));

        Drug drug6 = new Drug();
        drug6.id =6;
        drug6.setName("Lisinopril");
        drug6.setDescription("ACE inhibitor for hypertension");
        drug6.setManufacturer("Novartis");
        drug6.setCategory("Antihypertensive");
        drug6.setType("Tablet");
        drug6.setExpiryDate(LocalDate.of(2023, 11, 3));

        Drug drug7 = new Drug();
        drug7.id =7;
        drug7.setName("Cetirizine");
        drug7.setDescription("Antihistamine for allergies");
        drug7.setManufacturer("Bayer");
        drug7.setCategory("Antiallergic");
        drug7.setType("Tablet");
        drug7.setExpiryDate(LocalDate.of(2023, 8, 31));

        Drug drug8 = new Drug();
        drug8.id =8;
        drug8.setName("Sertraline");
        drug8.setDescription("Selective serotonin reuptake inhibitor");
        drug8.setManufacturer("GSK");
        drug8.setCategory("Antidepressant");
        drug8.setType("Tablet");
        drug8.setExpiryDate(LocalDate.of(2024, 4, 30));

        Drug drug9 = new Drug();
        drug9.id =9;
        drug9.setName("Azithromycin");
        drug9.setDescription("Macrolide antibiotic");
        drug9.setManufacturer("Pfizer");
        drug9.setCategory("Antibiotic");
        drug9.setType("Tablet");
        drug9.setExpiryDate(LocalDate.of(2024, 9, 15));

        Drug drug10 = new Drug();
        drug10.id =10;
        drug10.setName("Amlodipine");
        drug10.setDescription("Calcium channel blocker for hypertension");
        drug10.setManufacturer("Novartis");
        drug10.setCategory("Antihypertensive");
        drug10.setType("Tablet");
        drug10.setExpiryDate(LocalDate.of(2023, 10, 31));

        Drug drug11 = new Drug();
        drug11.id =11;
        drug11.setName("Loratadine");
        drug11.setDescription("Antihistamine for allergies");
        drug11.setManufacturer("Johnson & Johnson");
        drug11.setCategory("Antiallergic");
        drug11.setType("Tablet");
        drug11.setExpiryDate(LocalDate.of(2023, 5, 20));

        Drug drug12 = new Drug();
        drug12.id =12;
        drug12.setName("Fluoxetine");
        drug12.setDescription("Selective serotonin reuptake inhibitor");
        drug12.setManufacturer("Eli Lilly and Company");
        drug12.setCategory("Antidepressant");
        drug12.setType("Capsule");
        drug12.setExpiryDate(LocalDate.of(2024, 5, 20));
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

    private void seedDrugConflicts() {
        DrugConflict drugConflict1 = new DrugConflict();
        drugConflict1.id=1;
        drugConflict1.drugId1=1;
        drugConflict1.drugId2=2;
        drugConflictRepository.insert(drugConflict1);

        DrugConflict drugConflict2 = new DrugConflict();
        drugConflict2.id=2;
        drugConflict2.drugId1=3;
        drugConflict2.drugId2=4;
        drugConflictRepository.insert(drugConflict2);

        DrugConflict drugConflict3 = new DrugConflict();
        drugConflict3.id=3;
        drugConflict3.drugId1=5;
        drugConflict3.drugId2=6;
        drugConflictRepository.insert(drugConflict3);

        DrugConflict drugConflict4 = new DrugConflict();
        drugConflict4.id=4;
        drugConflict4.drugId1=7;
        drugConflict4.drugId2=8;
        drugConflictRepository.insert(drugConflict4);

        DrugConflict drugConflict5 = new DrugConflict();
        drugConflict5.id=5;
        drugConflict5.drugId1=9;
        drugConflict5.drugId2=10;
        drugConflictRepository.insert(drugConflict5);

        DrugConflict drugConflict6 = new DrugConflict();
        drugConflict6.id=6;
        drugConflict6.drugId1=11;
        drugConflict6.drugId2=12;
        drugConflictRepository.insert(drugConflict6);
    }

    private void seedDrugAliases() {
        DrugAlias drugAlias1 = new DrugAlias();
        drugAlias1.id=1;
        drugAlias1.drugId1 = 1;
        drugAlias1.drugId2 = 2;
        drugAliasRepository.insert(drugAlias1);

        DrugAlias drugAlias2 = new DrugAlias();
        drugAlias2.id=2;
        drugAlias2.drugId1 = 3;
        drugAlias2.drugId2 = 4;
        drugAliasRepository.insert(drugAlias2);

        DrugAlias drugAlias3 = new DrugAlias();
        drugAlias3.id=3;
        drugAlias3.drugId1 = 5;
        drugAlias3.drugId2 = 6;

        drugAliasRepository.insert(drugAlias3);

        DrugAlias drugAlias4 = new DrugAlias();
        drugAlias4.id=4;
        drugAlias4.drugId1 = 7;
        drugAlias4.drugId2 = 8;

        drugAliasRepository.insert(drugAlias4);

        DrugAlias drugAlias5 = new DrugAlias();
        drugAlias5.id=5;
        drugAlias5.drugId1 = 9;
        drugAlias5.drugId2 = 10;
        drugAliasRepository.insert(drugAlias5);

        DrugAlias drugAlias6 = new DrugAlias();
        drugAlias6.id=6;
        drugAlias6.drugId1 = 11;
        drugAlias6.drugId2 = 12;
        drugAliasRepository.insert(drugAlias6);
    }

    private void seedFamily() {
        Family family = new Family();
        family.id =1 ;
        family.name = "Asia Family";
        family.profilePicPath = copyResourceImageToFile(R.raw.family, "family").getAbsolutePath();
        familyRepository.insertFamily(family);

        FamilyMember familyMember1 = new FamilyMember();
        familyMember1.id=1;
        familyMember1.familyId = 1;
        familyMember1.personId = 2;
        familyRepository.insertFamilyMember(familyMember1);

        FamilyMember familyMember2 = new FamilyMember();
        familyMember2.id=2;
        familyMember2.familyId = 1;
        familyMember2.personId = 3;
        familyRepository.insertFamilyMember(familyMember2);

    }

    private void seedPeople() {
        Person person1 = new Person();
        person1.id = 1;
        person1.address = "Ramallah";
        person1.birthDate = LocalDate.of(2001, 9, 23);
        person1.firstName = "Rayyan";
        person1.lastName = "Asia";
        person1.weight = 60;
        person1.height = 1.7F;
        person1.phoneNumber = 568317311;
        person1.maritalStatus = "Single";
        person1.bloodType = "A+";
        person1.gender= "Male";
        person1.profilePicPath = copyResourceImageToFile(R.raw.rayyan, "rayyan").getAbsolutePath();

        personRepository.insertPerson(person1);

        Person person2 = new Person();
        person2.id = 2;
        person2.address = "Ramallah";
        person2.birthDate = LocalDate.of(1992, 3, 14);
        person2.firstName = "Rami";
        person2.lastName = "Asia";
        person2.weight = 60;
        person2.height = 1.7F;
        person2.phoneNumber = 568317311;
        person2.maritalStatus = "Single";
        person2.bloodType = "A+";
        person2.gender= "Male";
        person2.profilePicPath = copyResourceImageToFile(R.raw.rami, "rami").getAbsolutePath();

        // Insert the person1 object into the database using your existing method//
        personRepository.insertPerson(person2);

        Person person3 = new Person();
        person3.id = 3;
        person3.address = "Ramallah";
        person3.birthDate = LocalDate.of(1992, 3, 14);
        person3.firstName = "Tawfieg";
        person3.lastName = "Asia";
        person3.weight = 60;
        person3.height = 1.7F;
        person3.phoneNumber = 568317311;
        person3.maritalStatus = "Married";
        person3.bloodType = "A+";
        person3.gender= "Male";
        person3.profilePicPath = copyResourceImageToFile(R.raw.tony, "tony").getAbsolutePath();

        // Insert the person1 object into the database using your existing method//
        personRepository.insertPerson(person3);

    }

    private void seedDoctors() {
        Doctor doctor1 = new Doctor();
        doctor1.id = 1;
        doctor1.setName("Dr. John Smith");
        doctor1.setSpecialty(DoctorSpecialty.CARDIOLOGIST.getValue());
        doctor1.setPhone(123456789);
        doctor1.setEmail("john.smith@example.com");
        doctor1.setClinicalAddress("123 Main Street, City");

        Doctor doctor2 = new Doctor();
        doctor2.id =2;
        doctor2.setName("Dr. Emily Johnson");
        doctor2.setSpecialty(DoctorSpecialty.DERMATOLOGIST.getValue());
        doctor2.setPhone(987654321);
        doctor2.setEmail("emily.johnson@example.com");
        doctor2.setClinicalAddress("456 Oak Avenue, Town");


        doctorRepository.insertDoctor(doctor1);
        doctorRepository.insertDoctor(doctor2);
    }

    private void seedAppointments() {

        Appointment appointment1 = new Appointment();
        appointment1.setTitle("Follow-up Checkup");
        appointment1.setDoctorId(1);
        appointment1.setPersonId(1);
        appointment1.setSymptoms("Feeling better, but need a checkup.");
        appointment1.setDiagnosis("Recovered from flu.");
        appointment1.setDateOfAppointment(LocalDate.of(2023, 7, 31));
        appointmentRepository.insertAppointment(appointment1);



        Appointment appointment2 = new Appointment();
        appointment2.setTitle("Follow-up Checkup");
        appointment2.setDoctorId(1);
        appointment2.setPersonId(2);
        appointment2.setSymptoms("Feeling better, but need a checkup.");
        appointment2.setDiagnosis("Recovered from flu.");
        appointment2.setDateOfAppointment(LocalDate.of(2023, 7, 31));
        appointmentRepository.insertAppointment(appointment2);

        Appointment appointment3 = new Appointment();
        appointment3.setTitle("Follow-up Checkup");
        appointment3.setDoctorId(1);
        appointment3.setPersonId(3);
        appointment3.setSymptoms("Feeling better, but need a checkup.");
        appointment3.setDiagnosis("Recovered from flu.");
        appointment3.setDateOfAppointment(LocalDate.of(2023, 7, 31));
        appointmentRepository.insertAppointment(appointment3);

        Appointment appointment4 = new Appointment();
        appointment4.setTitle("Follow-up Checkup");
        appointment4.setDoctorId(2);
        appointment4.setPersonId(1);
        appointment4.setSymptoms("Feeling better, but need a checkup.");
        appointment4.setDiagnosis("Recovered from flu.");
        appointment4.setDateOfAppointment(LocalDate.of(2023, 7, 31));
        appointmentRepository.insertAppointment(appointment4);


        Appointment appointment5 = new Appointment();
        appointment5.setTitle("Follow-up Checkup");
        appointment5.setDoctorId(2);
        appointment5.setPersonId(2);
        appointment5.setSymptoms("Feeling better, but need a checkup.");
        appointment5.setDiagnosis("Recovered from flu.");
        appointment5.setDateOfAppointment(LocalDate.of(2023, 7, 31));
        appointmentRepository.insertAppointment(appointment5);

        Appointment appointment6 = new Appointment();
        appointment6.setTitle("Follow-up Checkup");
        appointment6.setDoctorId(2);
        appointment6.setPersonId(3);
        appointment6.setSymptoms("Feeling better, but need a checkup.");
        appointment6.setDiagnosis("Recovered from flu.");
        appointment6.setDateOfAppointment(LocalDate.of(2023, 7, 31));
        appointmentRepository.insertAppointment(appointment6);
    }

    private File copyResourceImageToFile(int resourceId, String name) {
        Bitmap image = BitmapFactory.decodeResource(context.getResources(), resourceId);
        return storeImage(image,name);
    }
    private File storeImage(Bitmap image, String name) {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String imageFileName = "IMG_" + name + timeStamp + ".jpg";
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), imageFileName);
        // Try to write the image to the file.
        try {
            FileOutputStream outputStream = new FileOutputStream(file);
            image.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
            outputStream.close();
        } catch (IOException e) {
        }
        return file;
    }
}
