package com.example.mypharmacy.ui.family;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.mypharmacy.R;
import com.example.mypharmacy.data.local.entities.Doctor;
import com.example.mypharmacy.data.local.entities.FamilyMemeber;
import com.example.mypharmacy.data.local.entities.Person;
import com.example.mypharmacy.data.local.repositories.DoctorRepository;
import com.example.mypharmacy.data.local.repositories.impl.DoctorRepositoryImpl;
import com.example.mypharmacy.ui.medRecord.doctor.CreateDoctorActivity;
import com.example.mypharmacy.ui.medRecord.doctor.DoctorAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class FamilyList extends AppCompatActivity {

    FamilyAdapter familyAdapter;
    RecyclerView familyRecycleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_family_list);

        familyRecycleView = findViewById(R.id.family_member_recyclerview);

                List<FamilyMemeber> personList = new ArrayList<>();
                FamilyMemeber familyMemeber1 = new FamilyMemeber();
        familyMemeber1.setFirstName("Rayyan");
        familyMemeber1.setLastName("Asia");
        familyMemeber1.setRole("Brother");
        familyMemeber1.setEmail("rayyyanasia@gmail.com");

                FamilyMemeber familyMemeber2 = new FamilyMemeber();
        familyMemeber2.setFirstName("Rami");
        familyMemeber2.setLastName("Asia");
        familyMemeber2.setRole("Brother");
        familyMemeber2.setEmail("ramiasia@gmail.com");

                FamilyMemeber familyMemeber3 = new FamilyMemeber();
        familyMemeber3.setFirstName("Reema");
        familyMemeber3.setLastName("Asia");
        familyMemeber3.setRole("Sister");
        familyMemeber3.setEmail("reemaasia@gmail.com");
        FamilyMemeber familyMemeber4 = new FamilyMemeber();
        familyMemeber4.setFirstName("Raneen");
        familyMemeber4.setLastName("Asia");
        familyMemeber4.setRole("Sister");
        familyMemeber4.setEmail("raneen@gmail.com");
        FamilyMemeber familyMemeber5 = new FamilyMemeber();
        familyMemeber5.setFirstName("Eman");
        familyMemeber5.setLastName("Asia");
        familyMemeber5.setRole("Mother");
        familyMemeber5.setEmail("eman@gmail.com");
        FamilyMemeber familyMemeber6 = new FamilyMemeber();
        familyMemeber6.setFirstName("Tawfeeq");
        familyMemeber6.setLastName("Asia");
        familyMemeber6.setRole("Father");
        familyMemeber6.setEmail("tawfeeq@gmail.com");
        personList.add(familyMemeber1);
        personList.add(familyMemeber2);
        personList.add(familyMemeber3);
        personList.add(familyMemeber4);
        personList.add(familyMemeber5);
        personList.add(familyMemeber6);

                familyAdapter = new FamilyAdapter(personList);
                familyRecycleView.setAdapter(familyAdapter);

        familyRecycleView.setLayoutManager(new LinearLayoutManager(this));
    }
}
