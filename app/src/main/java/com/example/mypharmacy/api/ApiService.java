package com.example.mypharmacy.api;

import com.example.mypharmacy.api.dto.PersonDto;
import com.example.mypharmacy.api.dto.UserDto;
import com.example.mypharmacy.api.dto.UserInsertDto;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.*;

public interface ApiService {
    @POST("api/user")
    Call<UserDto> insertUser(@Body UserDto userDto);
    @GET("api/person/{id}")
    Call<PersonDto> getPerson(@Path("id") long id);
    @GET("api/user/{id}")
    Call<UserDto> getUser(@Path("id") String email);
}

