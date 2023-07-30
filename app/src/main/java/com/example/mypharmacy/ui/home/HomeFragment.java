package com.example.mypharmacy.ui.home;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.example.mypharmacy.R;
import com.example.mypharmacy.data.local.entities.Person;
import com.example.mypharmacy.data.local.repositories.PersonRepository;
import com.example.mypharmacy.data.local.repositories.impl.PersonRepositoryImpl;
import com.example.mypharmacy.ui.menu.MenuActivity;

import java.io.File;

public class HomeFragment extends Fragment {

    TextView nameView;
    TextView genderView;
    TextView birthView;
    TextView addressView;
    TextView phoneView;
    TextView maritalStatus;
    TextView bloodType;
    ImageView imageView;

    Context context;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle bundle) {
        this.context = view.getContext();
        nameView = view.findViewById(R.id.name_textview);
        genderView = view.findViewById(R.id.gender_textview);
        birthView = view.findViewById(R.id.birthdate_textview);
        addressView = view.findViewById(R.id.address_textview);
        phoneView = view.findViewById(R.id.phone_number_textview);
        maritalStatus = view.findViewById(R.id.marital_status_textview);
        bloodType = view.findViewById(R.id.blood_type_textview);
        imageView = view.findViewById(R.id.profile_imageview);

        new LoadPersonDataTask().execute();
    }

    private void setPersonalInfo(Person person) {
        nameView.setText(new StringBuilder().append(person.getFirstName()).append(" ").append(person.getLastName()).toString());
        genderView.setText(person.getGender());
        birthView.setText(person.getBirthDate().toString());
        addressView.setText(person.getAddress());
        phoneView.setText(new StringBuilder().append('0').append(person.getPhoneNumber()).toString());
        maritalStatus.setText(person.getMaritalStatus());
        bloodType.setText(person.getBloodType());
        if (person.getProfilePicPath() != null) {
            File file = new File(person.getProfilePicPath());
            if(file.exists()){
                Glide.with(context)
                        .load(file) // Assuming the image path is stored in the 'path' variable of the LabTest object
                        .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                        .into(imageView);
            }

        } else {
            Log.e("ERROR", "FILE NOT FOUND!!!! " + person.getProfilePicPath());
        }
    }

    private class LoadPersonDataTask extends AsyncTask<Void, Void, Person> {
        @Override
        protected Person doInBackground(Void... voids) {
            PersonRepository personRepository = new PersonRepositoryImpl(getContext());
            return personRepository.getPerson();
        }

        @Override
        protected void onPostExecute(Person person) {
            // if the person is male hide menstrual calendar
            if (person.getGender().equals("Male"))
                MenuActivity.bottomNavigationView.getMenu().findItem(R.id.menstrual_calendar_tab).setVisible(false);
            else
                MenuActivity.bottomNavigationView.getMenu().findItem(R.id.menstrual_calendar_tab).setVisible(true);

            // Update the UI with the retrieved person object
            setPersonalInfo(person);
        }
    }
}
