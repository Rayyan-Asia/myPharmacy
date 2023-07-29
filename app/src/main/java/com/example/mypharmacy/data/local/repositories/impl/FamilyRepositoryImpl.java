package com.example.mypharmacy.data.local.repositories.impl;

import android.content.Context;
import com.example.mypharmacy.data.local.daos.FamilyDao;
import com.example.mypharmacy.data.local.daos.FamilyMemberDao;
import com.example.mypharmacy.data.local.entities.Family;
import com.example.mypharmacy.data.local.entities.FamilyMember;
import com.example.mypharmacy.data.local.myPharmacyDatabase;
import com.example.mypharmacy.data.local.repositories.FamilyRepository;
import java.util.List;

public class FamilyRepositoryImpl implements FamilyRepository {
    private FamilyDao familyDao;
    private FamilyMemberDao familyMemberDao;
    private Context context;

    myPharmacyDatabase database;

    public FamilyRepositoryImpl(Context context) {
        this.context = context;
        database = myPharmacyDatabase.getInstance(context);
        this.familyDao = database.getFamilyDao();
        this.familyMemberDao = database.getFamilyMemberDao();
    }

    // Family related methods
    @Override
    public void insertFamily(Family family) {
        familyDao.insert(family);
    }

    @Override
    public void updateFamily(Family family) {
        familyDao.update(family);
    }

    @Override
    public void deleteFamily(Family family) {
        familyDao.delete(family);
    }

    @Override
    public List<Family> getAllFamilies() {
        return familyDao.getAllFamilies();
    }

    @Override
    public Family getFamilyById(int familyId) {
        return familyDao.getFamilyById(familyId);
    }

    // FamilyMember related methods
    @Override
    public void insertFamilyMember(FamilyMember familyMember) {
        familyMemberDao.insert(familyMember);
    }

    @Override
    public void updateFamilyMember(FamilyMember familyMember) {
        familyMemberDao.update(familyMember);
    }

    @Override
    public void deleteFamilyMember(FamilyMember familyMember) {
        familyMemberDao.delete(familyMember);
    }

    @Override
    public List<FamilyMember> getAllFamilyMembersByFamilyId(int familyId) {
        return familyMemberDao.getAllFamilyMembersByFamilyId(familyId);
    }

    @Override
    public FamilyMember getFamilyMemberById(int familyMemberId) {
        return familyMemberDao.getFamilyMemberById(familyMemberId);
    }
}
