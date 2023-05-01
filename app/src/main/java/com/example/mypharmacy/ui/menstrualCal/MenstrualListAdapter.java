package com.example.mypharmacy.ui.menstrualCal;

import android.app.LauncherActivity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.mypharmacy.R;
import com.example.mypharmacy.data.local.entities.Menstruation;

import java.util.List;

public class MenstrualListAdapter extends ArrayAdapter<Menstruation> {


    public MenstrualListAdapter(@NonNull Context context, int resource, @NonNull List<Menstruation> objects) {
        super(context, resource, objects);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Inflate the custom layout file
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.menstrual_list_item, parent, false);
        }

        // Get the current item from the ArrayList
        Menstruation currentItem = getItem(position);

        // Set the text of the main and sub TextViews with the corresponding values from the current item
        TextView mainTextView = convertView.findViewById(R.id.main_text_view);
        mainTextView.setText((currentItem.startDate.getMonth() + " -> " + currentItem.endDate.getMonth()));

        TextView subTextView = convertView.findViewById(R.id.sub_text_view);
        subTextView.setText((currentItem.startDate.getDayOfMonth() + " -> " + currentItem.endDate.getDayOfMonth()));

        // Return the convertView
        return convertView;
    }

}
