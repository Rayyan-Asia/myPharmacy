package com.example.mypharmacyapi.dto;

import com.example.mypharmacyapi.entity.Person;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    public String id;
    public String email;
    private Person person;
}
