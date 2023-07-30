package com.example.mypharmacy.ui.name;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import com.example.mypharmacy.R;
import com.example.mypharmacy.data.local.repositories.NameRepository;
import com.example.mypharmacy.data.local.repositories.impl.FirebaseNameRepository;

import javax.inject.Inject;


public class NameActivity extends AppCompatActivity {
    private EditText nameEditText;
    private TextView nameLabel;
    @Inject
    public NameRepository nameRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name);
       // ((MyApplication) getApplicationContext()).appComponent.inject(this);


        nameEditText = findViewById(R.id.nameEditText);
        nameLabel = findViewById(R.id.nameLabel);

        nameRepository.getName().observe(this, name -> nameLabel.setText(name));

        findViewById(R.id.saveButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameEditText.getText().toString().trim();
                if (!name.isEmpty()) {
                    nameRepository.saveName(name);
                }
            }
        });
    }
}

