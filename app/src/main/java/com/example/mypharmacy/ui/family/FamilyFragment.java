package com.example.mypharmacy.ui.family;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mypharmacy.Configuration;
import com.example.mypharmacy.R;
import com.example.mypharmacy.api.ApiService;
import com.example.mypharmacy.api.JacksonConverterFactory;
import com.example.mypharmacy.api.RetroClient;
import com.example.mypharmacy.api.dto.AppointmentDto;
import com.example.mypharmacy.api.dto.DoctorDto;
import com.example.mypharmacy.api.dto.PersonDto;
import com.example.mypharmacy.api.dto.UserDto;
import com.example.mypharmacy.data.local.entities.Appointment;
import com.example.mypharmacy.data.local.entities.Doctor;
import com.example.mypharmacy.data.local.entities.Family;
import com.example.mypharmacy.data.local.entities.Person;
import com.example.mypharmacy.data.local.entities.User;
import com.example.mypharmacy.data.local.repositories.AppointmentRepository;
import com.example.mypharmacy.data.local.repositories.DoctorRepository;
import com.example.mypharmacy.data.local.repositories.PersonRepository;
import com.example.mypharmacy.data.local.repositories.UserRepository;
import com.example.mypharmacy.data.local.repositories.impl.AppointmentRepositoryImpl;
import com.example.mypharmacy.data.local.repositories.impl.DoctorRepositoryImpl;
import com.example.mypharmacy.data.local.repositories.impl.PersonRepositoryImpl;
import com.example.mypharmacy.data.local.repositories.impl.UserRepositoryImpl;
import com.example.mypharmacy.ui.medRecord.labTest.SpacingItemDecoration;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract;
import com.firebase.ui.auth.IdpResponse;
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.signin.SignInOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthCredential;
import okhttp3.ResponseBody;
import org.jetbrains.annotations.NotNull;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static android.app.Activity.RESULT_OK;

public class FamilyFragment extends Fragment {
    FloatingActionButton addFamilyButton;
    RecyclerView familyRecyclerView;

    private List<Family> families;

    private Context context;


    public FamilyFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_family, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        new LoadFamilyListAsyncTask(context, familyRecyclerView).execute();
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable Bundle savedInstanceState) {
        familyRecyclerView = view.findViewById(R.id.family_recycler_view);
        addFamilyButton = view.findViewById(R.id.add_family_button);
        addFamilyButton.setOnClickListener(e->{
            Intent intent = new Intent(context, CreateFamilyActivity.class);
            context.startActivity(intent);
        });
        context = view.getContext();
        int spacingInPixels = 20;
        SpacingItemDecoration itemDecoration = new SpacingItemDecoration(spacingInPixels);
        familyRecyclerView.addItemDecoration(itemDecoration);
        new LoadFamilyListAsyncTask(context, familyRecyclerView).execute();
    }



}