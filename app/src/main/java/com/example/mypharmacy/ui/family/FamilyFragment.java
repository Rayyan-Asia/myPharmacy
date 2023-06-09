package com.example.mypharmacy.ui.family;

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
import com.example.mypharmacy.Configuration;
import com.example.mypharmacy.R;
import com.example.mypharmacy.api.ApiService;
import com.example.mypharmacy.api.dto.PersonDto;
import com.example.mypharmacy.api.dto.UserDto;
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
import org.jetbrains.annotations.NotNull;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

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
    ApiService apiService;
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
            if(response.isNewUser()) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        User user = new User();
                        user.setEmail(firebaseUser.getEmail());
                        user.setPersonId(personRepository.getPerson().getId());
                        userRepository.insertUser(user);
                        UserDto userDto = new UserDto();
                        PersonDto personDto = new PersonDto();
                        personDto.setId(user.personId);
                        userDto.setId(user.getId());
                        userDto.setEmail(user.getEmail());
                        userDto.setPerson(personDto);
                        Call<UserDto> call = apiService.insertUser(userDto);
                        call.enqueue(new Callback<UserDto>() {
                            @Override
                            public void onResponse(Call<UserDto> call, Response<UserDto> response) {
                                if (response.isSuccessful()) {
                                    // Handle successful response
                                    UserDto data = response.body();
                                    Log.println(Log.INFO, "User Response", data.toString());

                                } else {
                                    Log.println(Log.ERROR, "User Response","BIG DOO DOO");
                                }
                            }

                            @Override
                            public void onFailure(Call<UserDto> call, Throwable t) {
                                // Handle network or other errors
                                // ...
                            }
                        });


                    }
                }).start();
            } else {

            }

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
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(configuration.getApiUrl())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(ApiService.class);
        userRepository = new UserRepositoryImpl(view.getContext());
        personRepository = new PersonRepositoryImpl(view.getContext());
        doctorRepository = new DoctorRepositoryImpl(view.getContext());
        appointmentRepository = new AppointmentRepositoryImpl(view.getContext());
        successIntent = new Intent(view.getContext(), FamilyList.class);
        login.setOnClickListener(e -> {
            List<AuthUI.IdpConfig> providers = Arrays.asList(
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