package com.example.mypharmacy.ui.family;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import com.example.mypharmacy.R;

public class FamilyJoinCreate extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_join_family);

        Button join = findViewById(R.id.btnJoinFamily);
        join.setOnClickListener(e -> {
            Intent intent = new Intent(this, FamilyList.class);
            startActivity(intent);
        });
    }
}
