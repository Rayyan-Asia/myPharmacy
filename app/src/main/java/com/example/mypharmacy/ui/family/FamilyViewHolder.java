package com.example.mypharmacy.ui.family;

import android.content.Context;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.mypharmacy.R;
import com.example.mypharmacy.data.local.entities.FamilyMemeber;

public class FamilyViewHolder extends RecyclerView.ViewHolder {
    private final Context context;
    private TextView family_name;
    private TextView family_role;
    private TextView family_phone;
    private TextView family_email;


    public FamilyViewHolder(@NonNull View itemView) {
        super(itemView);
        context = itemView.getContext();
        family_name = itemView.findViewById(R.id.family_member_name);
        family_role = itemView.findViewById(R.id.family_member_role);
        family_phone = itemView.findViewById(R.id.family_member_phone);
        family_email = itemView.findViewById(R.id.family_member_email);

    }

    public void bind(FamilyMemeber familyMember) {

                family_name.setText(familyMember.getFirstName() + " " + familyMember.getLastName());
                family_role.setText(familyMember.getRole());
                family_phone.setText(""+familyMember.getPhoneNumber());
                family_email.setText(familyMember.getEmail());
    }
}


