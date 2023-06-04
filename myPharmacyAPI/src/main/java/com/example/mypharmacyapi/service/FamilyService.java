package com.example.mypharmacyapi.service;

import com.example.mypharmacyapi.dto.FamilyDto;
import com.example.mypharmacyapi.dto.FamilyInsertDto;

public interface FamilyService {
    FamilyDto insertFamily(FamilyInsertDto doctor);
    FamilyDto getFamily(long id);
}
