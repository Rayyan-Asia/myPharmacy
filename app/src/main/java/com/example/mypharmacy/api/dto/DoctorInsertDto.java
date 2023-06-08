package com.example.mypharmacy.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DoctorInsertDto {
    private String name;
    private String specialty;
    private int phone;
    private String email;
    private String clinicalAddress;
}
