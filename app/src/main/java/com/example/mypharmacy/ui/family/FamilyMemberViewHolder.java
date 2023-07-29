package com.example.mypharmacy.ui.family;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mypharmacy.R;
import com.example.mypharmacy.data.local.entities.Family;
import com.example.mypharmacy.data.local.entities.FamilyMember;
import com.example.mypharmacy.data.local.entities.Person;

import java.io.File;

public class FamilyMemberViewHolder extends RecyclerView.ViewHolder {

    private Context context;
    private TextView genderText,nameText,phoneText,bloodTypeText;
    private ImageView profilePicView;
    public FamilyMemberViewHolder(@NonNull View itemView, Context context) {
        super(itemView);
        this.context = context;
        genderText = itemView.findViewById(R.id.family_gender_textview);
        nameText = itemView.findViewById(R.id.family_member_name_text);
        phoneText = itemView.findViewById(R.id.family_phone_number_textview);
        bloodTypeText = itemView.findViewById(R.id.family_blood_type_textview);
        profilePicView = itemView.findViewById(R.id.file_view_family_member);

    }

    public void bind(FamilyMember familyMember) {
        RetrievePersonTask retrievePersonTask = new RetrievePersonTask(context, new RetrievePersonTask.OnPersonRetrievedListener() {
            @Override
            public void onPersonRetrieved(Person person) {
                if (person != null) {
                    nameText.setText(person.firstName.substring(0, 1).toUpperCase() + person.firstName.substring(1));
                    genderText.setText(person.gender);
                    phoneText.setText(person.phoneNumber+"");
                    bloodTypeText.setText(person.bloodType);
                    File file = new File(person.profilePicPath);
                    if (file.exists()) {
                        Glide.with(context)
                                .load(file) // Assuming the image path is stored in the 'path' variable of the LabTest object
                                .into(profilePicView);
                    } else {
                        Log.e("ERROR", "FILE NOT FOUND!!!! " + person.profilePicPath);
                    }
                } else {
                    // Person not found or some error occurred.
                }
            }
        });

        retrievePersonTask.execute(familyMember.personId);


    }

}
