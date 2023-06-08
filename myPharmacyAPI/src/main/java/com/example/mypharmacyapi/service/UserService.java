package com.example.mypharmacyapi.service;


import com.example.mypharmacyapi.dto.UserDto;
import com.example.mypharmacyapi.dto.UserInsertDto;

public interface UserService {
    UserDto insertUser(UserInsertDto user);

    UserDto getUser(String id);
}
