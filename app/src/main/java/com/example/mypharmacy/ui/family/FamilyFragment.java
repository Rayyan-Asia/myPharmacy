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
import com.example.mypharmacy.api.JacksonConverterFactory;
import com.example.mypharmacy.api.RetroClient;
import com.example.mypharmacy.api.dto.AppointmentDto;
import com.example.mypharmacy.api.dto.DoctorDto;
import com.example.mypharmacy.api.dto.PersonDto;
import com.example.mypharmacy.api.dto.UserDto;
import com.example.mypharmacy.data.local.entities.Appointment;
import com.example.mypharmacy.data.local.entities.Doctor;
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
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract;
import com.firebase.ui.auth.IdpResponse;
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.signin.SignInOptions;
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
        if (result.getResultCode() == RESULT_OK) {
            // Successfully signed in
            Intent intent = new Intent(this.getContext(), FamilyJoinCreate.class);
            startActivity(intent);
            /*new Thread(new Runnable() {
                @Override
                public void run() {
                    FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                    UserDto userDtoEmail = new UserDto();
                    userDtoEmail.setEmail(firebaseUser.getEmail());
                    Call<UserDto> call = apiService.getUser(userDtoEmail);
                    Response<UserDto> userDtoResponse = null;
                    try {
                        userDtoResponse = call.execute();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                    if (userDtoResponse.body() == null) {
                        Person person = personRepository.getPerson();
                        UserDto userDto = new UserDto();
                        PersonDto personDto = new PersonDto();
                        personDto.setGender(person.getGender());
                        personDto.setAddress(person.getAddress());
                        personDto.setHeight(person.getHeight());
                        personDto.setWeight(person.getWeight());
                        personDto.setBirthDate(person.getBirthDate());
                        personDto.setBloodType(person.getBloodType());
                        personDto.setMaritalStatus(person.getMaritalStatus());
                        personDto.setFirstName(person.getFirstName());
                        personDto.setLastName(person.getLastName());
                        personDto.setPhoneNumber(person.getPhoneNumber());
                        Call<PersonDto> callInsertPerson = apiService.insertPerson(personDto);
                        try {
                            Response<PersonDto> personResponse = callInsertPerson.execute();
                            userDto.setId(UUID.randomUUID().toString());
                            userDto.setEmail(firebaseUser.getEmail());
                            userDto.setPerson(personResponse.body());
                            User user = new User();
                            user.setId(userDto.getId());
                            user.setEmail(userDto.getEmail());
                            userRepository.insertUser(user);
                            Call<UserDto> callInsertUser = apiService.insertUser(userDto);
                            if (personResponse.body() != null) {
                                callInsertUser.execute();
                                List<Doctor> doctors = doctorRepository.getAllDoctors();
                                List<DoctorDto> doctorDtos = new ArrayList<>();
                                for (Doctor doctor : doctors) {
                                    DoctorDto doctorDto = new DoctorDto();
                                    doctorDto.setEmail(doctor.getEmail());
                                    doctorDto.setName(doctor.getName());
                                    doctorDto.setPhone(doctor.getPhone());
                                    doctorDto.setSpecialty(doctor.getSpecialty());
                                    doctorDto.setClinicalAddress(doctor.getClinicalAddress());
                                    doctorDtos.add(doctorDto);
                                }
                                Call<List<DoctorDto>> callInsertDoctors = apiService.insertDoctors(doctorDtos);
                                Response<List<DoctorDto>> doctorsResponse = callInsertDoctors.execute();
                                // Create a map to store the doctor IDs from the original list mapped to the doctorDto IDs
                                Map<Integer, Integer> doctorDoctorDtoMap = doctors.stream()
                                        .collect(Collectors.toMap(
                                                Doctor::getId,
                                                doctor -> doctorsResponse.body().stream()
                                                        .filter(doctorDto -> doctorDto.getEmail().equals(doctor.getEmail()))
                                                        .findFirst()
                                                        .map(DoctorDto::getId)
                                                        .orElse(null)));

                                List<Appointment> appointmentList = appointmentRepository.getAllAppointments();
                                List<AppointmentDto> appointmentDtos = new ArrayList<>();
                                for (Appointment appointment : appointmentList) {
                                    AppointmentDto appointmentDto = new AppointmentDto();
                                    appointmentDto.setDateOfAppointment(appointment.getDateOfAppointment());
                                    appointmentDto.setDiagnosis(appointment.getDiagnosis());
                                    appointmentDto.setSymptoms(appointment.getSymptoms());
                                    appointmentDto.setTitle(appointment.getTitle());
                                    Doctor doctor = doctorRepository.getDoctor(appointment.getDoctorId());
                                    int dtoId = doctorDoctorDtoMap.get(doctor.getId());
                                    DoctorDto doctorDto = new DoctorDto();
                                    doctorDto.setId(dtoId);
                                    appointmentDto.setDoctor(doctorDto);
                                    appointmentDto.setPerson(personResponse.body());
                                    appointmentDtos.add(appointmentDto);
                                }
                                Call<List<AppointmentDto>> callInsertAppointments = apiService.insertAppointments(appointmentDtos);
                                callInsertAppointments.execute();
                            }
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }).start();*/
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
        Retrofit retrofit = RetroClient.getClient();

        apiService = retrofit.create(ApiService.class);
        userRepository = new UserRepositoryImpl(view.getContext());
        personRepository = new PersonRepositoryImpl(view.getContext());
        doctorRepository = new DoctorRepositoryImpl(view.getContext());
        appointmentRepository = new AppointmentRepositoryImpl(view.getContext());
        successIntent = new Intent(view.getContext(), FamilyList.class);
        login.setOnClickListener(e -> {
            if (FirebaseAuth.getInstance().getCurrentUser() != null) {
                FirebaseAuth.getInstance().signOut();
            }
            List<AuthUI.IdpConfig> providers = Arrays.asList(
                    new AuthUI.IdpConfig.GoogleBuilder().build());

            Intent signInIntent = AuthUI.getInstance()
                    .createSignInIntentBuilder()
                    .setAvailableProviders(providers)
                    .setIsSmartLockEnabled(false)
                    .build();
            signInLauncher.launch(signInIntent);


        });
    }
}