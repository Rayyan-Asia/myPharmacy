package com.example.mypharmacy.data.local.repositories;

import com.example.mypharmacy.data.local.entities.Family;
import com.example.mypharmacy.data.local.entities.FamilyMember;

import java.util.List;

public interface FamilyRepository {
    // Family related methods
    void insertFamily(Family family);
    void updateFamily(Family family);
    void deleteFamily(Family family);
    List<Family> getAllFamilies();
    Family getFamilyById(int familyId);

    // FamilyMember related methods
    void insertFamilyMember(FamilyMember familyMember);
    void updateFamilyMember(FamilyMember familyMember);
    void deleteFamilyMember(FamilyMember familyMember);
    List<FamilyMember> getAllFamilyMembersByFamilyId(int familyId);
    FamilyMember getFamilyMemberById(int familyMemberId);
}
