package com.example.mypharmacy.ui.family;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.mypharmacy.Configuration;
import com.example.mypharmacy.R;
import com.example.mypharmacy.data.local.entities.User;
import com.example.mypharmacy.data.local.repositories.AppointmentRepository;
import com.example.mypharmacy.data.local.repositories.DoctorRepository;
import com.example.mypharmacy.data.local.repositories.PersonRepository;
import com.example.mypharmacy.data.local.repositories.UserRepository;
import com.example.mypharmacy.data.local.repositories.impl.AppointmentRepositoryImpl;
import com.example.mypharmacy.data.local.repositories.impl.DoctorRepositoryImpl;
import com.example.mypharmacy.data.local.repositories.impl.PersonRepositoryImpl;
import com.example.mypharmacy.data.local.repositories.impl.UserRepositoryImpl;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract;
import com.firebase.ui.auth.IdpResponse;
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static android.app.Activity.RESULT_OK;

public class FamilyFragment extends Fragment {
    // See: https://developer.android.com/training/basics/intents/result
    Button login;
    UserRepository userRepository;
    PersonRepository personRepository;
    AppointmentRepository appointmentRepository;
    Configuration configuration = new Configuration();
    DoctorRepository doctorRepository;
    Intent successIntent;
    private final ActivityResultLauncher<Intent> signInLauncher = registerForActivityResult(
            new FirebaseAuthUIActivityResultContract(),
            new ActivityResultCallback<FirebaseAuthUIAuthenticationResult>() {
                @Override
                public void onActivityResult(FirebaseAuthUIAuthenticationResult result) {
                    onSignInResult(result);
                }
            }
    );

    private void onSignInResult(FirebaseAuthUIAuthenticationResult result) {
        IdpResponse response = result.getIdpResponse();
        if (result.getResultCode() == RESULT_OK) {
            // Successfully signed in
            FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    User user = new User();
                    user.setEmail(firebaseUser.getEmail());
                    user.setPersonId(personRepository.getPerson().getId());
                    userRepository.insertUser(user);
                }
            }).start();

            // ...
        } else {
            // Sign in failed. If response is null the user canceled the
            // sign-in flow using the back button. Otherwise check
            // response.getError().getErrorCode() and handle the error.
            // ...
        }
    }

    public FamilyFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Choose authentication providers
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_family, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable Bundle savedInstanceState) {
        login = view.findViewById(R.id.loginButton);
        userRepository = new UserRepositoryImpl(view.getContext());
        personRepository = new PersonRepositoryImpl(view.getContext());
        doctorRepository = new DoctorRepositoryImpl(view.getContext());
        appointmentRepository = new AppointmentRepositoryImpl(view.getContext());
        successIntent = new Intent(view.getContext(), FamilyList.class);
        login.setOnClickListener(e -> {
            List<AuthUI.IdpConfig> providers = Arrays.asList(
                    new AuthUI.IdpConfig.EmailBuilder().build(),
                    new AuthUI.IdpConfig.GoogleBuilder().build());

            // Create and launch sign-in intent
            Intent signInIntent = AuthUI.getInstance()
                    .createSignInIntentBuilder()
                    .setAvailableProviders(providers)
                    .setIsSmartLockEnabled(false)
                    .build();
            signInLauncher.launch(signInIntent);
        });

    }

}