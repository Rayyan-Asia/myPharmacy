package com.example.mypharmacy.api;

import com.example.mypharmacy.api.dto.UserDto;
import com.example.mypharmacy.api.dto.UserInsertDto;
import retrofit2.Call;
import retrofit2.http.*;

public interface ApiService {
    @POST("api/user")
    Call<UserDto> insertUser(@Body UserDto userDto);
}

