package com.example.mypharmacy.api;

import com.example.mypharmacy.api.dto.*;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

public interface ApiService {
    @POST("api/user")
    Call<UserDto> insertUser(@Body UserDto userDto);
    @GET("api/person/{id}")
    Call<PersonDto> getPerson(@Path("id") long id);
    @POST("api/person")
    Call<PersonDto> insertPerson(@Body PersonDto personDto);
    @POST("api/user/email")
    Call<UserDto> getUser(@Body UserDto userDto);

    @POST("api/doctor/all")
    Call<List<DoctorDto>> insertDoctors(@Body List<DoctorDto> doctorDtoList);

    @POST("api/appointment/all")
    Call<List<AppointmentDto>> insertAppointments(@Body List<AppointmentDto> doctorDtoList);

}

