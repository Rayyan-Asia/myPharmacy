package com.example.mypharmacyapi.service.impl;

import com.example.mypharmacyapi.dto.FamilyDto;
import com.example.mypharmacyapi.dto.FamilyDto;
import com.example.mypharmacyapi.dto.FamilyInsertDto;
import com.example.mypharmacyapi.entity.Family;
import com.example.mypharmacyapi.repository.FamilyRepository;
import com.example.mypharmacyapi.service.FamilyService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Service
public class FamilyServiceImpl implements FamilyService {
    private final FamilyRepository familyRepository;
    private final ModelMapper modelMapper;

    @Override
    public FamilyDto insertFamily(FamilyInsertDto familyInsertDto) {
        Family family = modelMapper.map(familyInsertDto, Family.class);
        Family newFamily = familyRepository.save(family);
        return modelMapper.map(newFamily, FamilyDto.class);
    }

    @Override
    public FamilyDto getFamily(long id) {
        Optional<Family> family = familyRepository.findById(id);
        if(family.isPresent()){
            return modelMapper.map(family.get(), FamilyDto.class);
        } else {
            return null;
        }
    }
}
