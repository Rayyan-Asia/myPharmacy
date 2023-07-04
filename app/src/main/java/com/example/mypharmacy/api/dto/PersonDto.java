package com.example.mypharmacy.api.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonDto {
    private int id;
    private String firstName;
    private String lastName;
    private String gender;
    private String address;
    private float weight;
    private float height;
    private int phoneNumber;
    private LocalDate birthDate;
    private String maritalStatus;
    private String bloodType;
}
