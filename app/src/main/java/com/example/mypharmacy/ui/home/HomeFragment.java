package com.example.mypharmacy.ui.home;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.example.mypharmacy.R;
import com.example.mypharmacy.data.local.entities.Person;
import com.example.mypharmacy.data.local.repositories.PersonRepository;
import com.example.mypharmacy.data.local.repositories.impl.PersonRepositoryImpl;
import com.example.mypharmacy.ui.menu.MenuActivity;

public class HomeFragment extends Fragment {

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        PersonRepository personRepository = new PersonRepositoryImpl(this.getContext());
        new Thread(new Runnable() {
            @Override
            public void run() {
                final Person person = personRepository.getPerson();
                // if the person is male hide menstrual calendar
                if (person.getGender().equals("Male"))
                    MenuActivity.bottomNavigationView.getMenu().findItem(R.id.menstrual_calendar_tab).setVisible(false);

                else
                    MenuActivity.bottomNavigationView.getMenu().findItem(R.id.menstrual_calendar_tab).setVisible(true);

                // Pass the person object back to the main thread using a Handler
                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        setPersonalInfo(person);
                    }
                });
            }
        }).start();
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    private void setPersonalInfo(Person person) {
        TextView nameView = getView().findViewById(R.id.name_textview);
        TextView genderView = getView().findViewById(R.id.gender_textview);
        TextView birthView = getView().findViewById(R.id.birthdate_textview);
        TextView addressView = getView().findViewById(R.id.address_textview);
        TextView phoneView = getView().findViewById(R.id.phone_number_textview);
        TextView maritalStatus = getView().findViewById(R.id.marital_status_textview);
        TextView bloodType = getView().findViewById(R.id.blood_type_textview);
        nameView.setText(new StringBuilder().append(person.getFirstName()).append(" ").append(person.getLastName()).toString());
        genderView.setText(person.getGender());
        birthView.setText(person.getBirthDate().toString());
        addressView.setText(person.getAddress());
        phoneView.setText(new StringBuilder().append('0').append(person.getPhoneNumber()).toString());
        maritalStatus.setText(person.getMaritalStatus());
        bloodType.setText(person.getBloodType());

    }
}
